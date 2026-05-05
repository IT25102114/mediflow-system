package com.mediflow.service;

import com.mediflow.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SupplierService {

    private static final String SUPPLIERS_FILE = "suppliers.txt";
    private final FileHandler fileHandler;

    @Autowired
    public SupplierService(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public Supplier addSupplier(String name, String phone, String email, String address,
                                String type, String extra1, String extra2, String extra3) {
        int id = fileHandler.getNextId(SUPPLIERS_FILE);
        String now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Supplier supplier;
        if ("INTERNATIONAL".equalsIgnoreCase(type)) {
            InternationalSupplier s = new InternationalSupplier();
            s.setId(id); s.setName(name); s.setContactPhone(phone); s.setEmail(email);
            s.setAddress(address); s.setCreatedAt(now);
            s.setCountry(extra1 != null ? extra1 : "");
            s.setImportLicenseNumber(extra2 != null ? extra2 : "");
            s.setCurrency(extra3 != null ? extra3 : "USD");
            supplier = s;
        } else {
            LocalSupplier s = new LocalSupplier();
            s.setId(id); s.setName(name); s.setContactPhone(phone); s.setEmail(email);
            s.setAddress(address); s.setCreatedAt(now);
            s.setDistrict(extra1 != null ? extra1 : "");
            s.setRegistrationNumber(extra2 != null ? extra2 : "");
            supplier = s;
        }
        fileHandler.writeLine(SUPPLIERS_FILE, supplier.toFileString());
        return supplier;
    }

    public List<Supplier> getAllSuppliers() {
        return fileHandler.readAllLines(SUPPLIERS_FILE).stream()
                .map(this::parseSupplier).filter(Objects::nonNull)
                .filter(Supplier::isActive).collect(Collectors.toList());
    }

    public Supplier findById(int id) {
        String line = fileHandler.findById(SUPPLIERS_FILE, String.valueOf(id));
        return line != null ? parseSupplier(line) : null;
    }

    public List<Supplier> searchSuppliers(String keyword) {
        return fileHandler.searchLines(SUPPLIERS_FILE, keyword).stream()
                .map(this::parseSupplier).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public boolean updateSupplier(Supplier supplier) {
        return fileHandler.updateLine(SUPPLIERS_FILE, String.valueOf(supplier.getId()), supplier.toFileString());
    }

    public boolean deleteSupplier(int id) {
        return fileHandler.deleteLine(SUPPLIERS_FILE, String.valueOf(id));
    }

    private Supplier parseSupplier(String line) {
        try {
            String[] parts = line.split("\\|", -1);
            if (parts.length < 8) return null;
            String type = parts[7];
            if ("INTERNATIONAL".equalsIgnoreCase(type)) return InternationalSupplier.fromFileString(line);
            else return LocalSupplier.fromFileString(line);
        } catch (Exception e) { return null; }
    }

    public int getSupplierCount() { return fileHandler.getRecordCount(SUPPLIERS_FILE); }
}
