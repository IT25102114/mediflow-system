package com.mediflow.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * FILE HANDLING UTILITY
 * 
 * Central service for all file read/write operations.
 * This is the core of the file-based data storage system.
 * All CRUD operations ultimately use this class.
 * 
 * Data format: pipe-delimited (|) records, one per line.
 * First field is always the ID.
 */
@Service
public class FileHandler {

    @Value("${app.data.dir:data}")
    private String dataDir;

    /**
     * Initialize data directory on application startup
     */
    @PostConstruct
    public void init() {
        try {
            Path dirPath = Paths.get(dataDir);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
                System.out.println("✅ Data directory created: " + dirPath.toAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("❌ Failed to create data directory: " + e.getMessage());
        }
    }

    /**
     * Get the full file path for a data file
     */
    private Path getFilePath(String filename) {
        return Paths.get(dataDir, filename);
    }

    /**
     * READ — Read all lines from a file
     * @param filename The name of the data file (e.g., "users.txt")
     * @return List of all non-empty lines
     */
    public List<String> readAllLines(String filename) {
        Path path = getFilePath(filename);
        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
                return new ArrayList<>();
            }
            return Files.readAllLines(path).stream()
                    .filter(line -> !line.trim().isEmpty() && !line.startsWith("#"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error reading file " + filename + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * CREATE — Append a new line to a file
     * @param filename The data file name
     * @param line The pipe-delimited data line to append
     */
    public void writeLine(String filename, String line) {
        Path path = getFilePath(filename);
        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            Files.write(path, (line + System.lineSeparator()).getBytes(),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error writing to file " + filename + ": " + e.getMessage());
        }
    }

    /**
     * UPDATE — Update a specific line by ID (first field)
     * @param filename The data file name
     * @param id The ID to find and update
     * @param newLine The new pipe-delimited data line
     * @return true if the line was found and updated
     */
    public boolean updateLine(String filename, String id, String newLine) {
        Path path = getFilePath(filename);
        try {
            List<String> lines = readAllLines(filename);
            boolean found = false;
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                String lineId = line.split("\\|")[0];
                if (lineId.equals(id)) {
                    updatedLines.add(newLine);
                    found = true;
                } else {
                    updatedLines.add(line);
                }
            }

            if (found) {
                writeAllLines(filename, updatedLines);
            }
            return found;
        } catch (Exception e) {
            System.err.println("Error updating file " + filename + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * DELETE — Remove a line by ID (first field)
     * @param filename The data file name
     * @param id The ID of the record to delete
     * @return true if the line was found and deleted
     */
    public boolean deleteLine(String filename, String id) {
        try {
            List<String> lines = readAllLines(filename);
            List<String> filteredLines = lines.stream()
                    .filter(line -> !line.split("\\|")[0].equals(id))
                    .collect(Collectors.toList());

            if (filteredLines.size() < lines.size()) {
                writeAllLines(filename, filteredLines);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error deleting from file " + filename + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * SEARCH — Find lines containing a keyword (case-insensitive)
     * @param filename The data file name
     * @param keyword The search keyword
     * @return List of matching lines
     */
    public List<String> searchLines(String filename, String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return readAllLines(filename).stream()
                .filter(line -> line.toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    /**
     * Find a single line by ID
     * @param filename The data file name
     * @param id The ID to search for
     * @return The matching line, or null if not found
     */
    public String findById(String filename, String id) {
        return readAllLines(filename).stream()
                .filter(line -> line.split("\\|")[0].equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Get the next available ID for a file
     * @param filename The data file name
     * @return The next sequential ID
     */
    public int getNextId(String filename) {
        List<String> lines = readAllLines(filename);
        int maxId = 0;
        for (String line : lines) {
            try {
                int id = Integer.parseInt(line.split("\\|")[0]);
                if (id > maxId) maxId = id;
            } catch (NumberFormatException ignored) {}
        }
        return maxId + 1;
    }

    /**
     * Write all lines to a file (overwrites existing content)
     * Used internally by update and delete operations
     */
    private void writeAllLines(String filename, List<String> lines) {
        Path path = getFilePath(filename);
        try {
            Files.write(path, lines, StandardOpenOption.WRITE,
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("Error writing all lines to file " + filename + ": " + e.getMessage());
        }
    }

    /**
     * Get total record count in a file
     */
    public int getRecordCount(String filename) {
        return readAllLines(filename).size();
    }
}
