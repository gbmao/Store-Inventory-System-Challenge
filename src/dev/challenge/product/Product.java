package dev.challenge.product;

enum Category {SPORT, CUSTOM, NAKED, TRAIL, DEFAULT}

public  class Product {
    private String sku;
    private String name;
    private String manufacturer;
    private String category;

    protected Product(String name, String manufacturer, String category) {
        this.name = name;
        this.manufacturer = manufacturer;
        switch (category.toUpperCase().trim()) {
            case "SPORT" -> this.category = Category.SPORT.toString();
            case "CUSTOM" -> this.category = Category.CUSTOM.toString();
            case "NAKED" -> this.category = Category.NAKED.toString();
            case "TRAIL" -> this.category = Category.TRAIL.toString();
            default -> this.category = Category.DEFAULT.toString();
        }
        this.sku = this.name.substring(0, 3).trim() + "-" + this.manufacturer.substring(0, 3) + "-" +
                this.category.substring(0,3) + name.substring(name.lastIndexOf(" ") + 1); // TODO criar um metodo melhor
    }

    public String getSku() {
        return sku;
    }
}
