package com.mediflow.service;

import com.mediflow.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Category Service — CRUD operations for medicine categories.
 */
@Service
public class CategoryService {

    private static final String CATEGORIES_FILE = "categories.txt";
    private final FileHandler fileHandler;

    @Autowired
    public CategoryService(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public Category addCategory(String name, String description, String icon) {
        int id = fileHandler.getNextId(CATEGORIES_FILE);
        Category cat = new Category(id, name, description, icon, true, id);
        fileHandler.writeLine(CATEGORIES_FILE, cat.toFileString());
        return cat;
    }

    public List<Category> getAllCategories() {
        return fileHandler.readAllLines(CATEGORIES_FILE).stream()
                .map(Category::fromFileString)
                .filter(Objects::nonNull)
                .filter(Category::isActive)
                .sorted(Comparator.comparingInt(Category::getSortOrder))
                .collect(Collectors.toList());
    }

    public Category findById(int id) {
        String line = fileHandler.findById(CATEGORIES_FILE, String.valueOf(id));
        return line != null ? Category.fromFileString(line) : null;
    }

    public boolean updateCategory(Category category) {
        return fileHandler.updateLine(CATEGORIES_FILE, String.valueOf(category.getId()), category.toFileString());
    }

    public boolean deleteCategory(int id) {
        return fileHandler.deleteLine(CATEGORIES_FILE, String.valueOf(id));
    }
}
