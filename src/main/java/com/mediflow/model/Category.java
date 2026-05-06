package com.mediflow.model;

/**
 * ENCAPSULATION
 * 
 * Category class for organizing medicines into groups.
 * Uses encapsulation with private fields and public getters/setters.
 */
public class Category {
    private int id;
    private String name;
    private String description;
    private String icon;
    private boolean active;
    private int sortOrder;

    public Category() {
        this.active = true;
        this.sortOrder = 0;
    }

    public Category(int id, String name, String description, String icon, boolean active, int sortOrder) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.active = active;
        this.sortOrder = sortOrder;
    }

    public String toFileString() {
        return String.join("|",
                String.valueOf(id),
                name != null ? name : "",
                description != null ? description : "",
                icon != null ? icon : "",
                String.valueOf(active),
                String.valueOf(sortOrder)
        );
    }

    public static Category fromFileString(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 6) return null;

        Category cat = new Category();
        cat.setId(Integer.parseInt(parts[0]));
        cat.setName(parts[1]);
        cat.setDescription(parts[2]);
        cat.setIcon(parts[3]);
        cat.setActive(Boolean.parseBoolean(parts[4]));
        cat.setSortOrder(Integer.parseInt(parts[5]));
        return cat;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public int getSortOrder() { return sortOrder; }
    public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }
}
