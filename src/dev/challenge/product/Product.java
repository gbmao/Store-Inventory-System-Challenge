package dev.challenge.product;

enum Category {SPORT, CUSTOM, NAKED, TRAIL, DEFAULT}

public  class Product {
    private String sku;
    private String name;
    private String manufacturer;
    private Category category;

    protected Product(String name, String manufacturer, String category) {
        this.name = name;
        this.manufacturer = manufacturer;
        switch (category.toUpperCase().trim()) {
            case "SPORT" -> this.category = Category.SPORT;
            case "CUSTOM" -> this.category = Category.CUSTOM;
            case "NAKED" -> this.category = Category.NAKED;
            case "TRAIL" -> this.category = Category.TRAIL;
            default -> this.category = Category.DEFAULT;
        }
        this.sku = this.name.substring(0, 3) + "-" + this.manufacturer.substring(0, 4) + "-" + this.category
        + this.name.substring(this.name.length()- 3); // TODO criar um metodo melhor
    }

    public String getSku() {
        return sku;
    }
}
