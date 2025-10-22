package dev.challenge.store;

import dev.challenge.product.InventoryItem;
import dev.challenge.product.ProductData;

import java.time.LocalDate;
import java.util.HashMap;

public class StoreLogic {



    private class Cart {
        enum Type {PHYSICAL, VIRTUAL}

        private static int idTotal = 0;
        private HashMap<String, Integer> cartProducts;
        private LocalDate date;
        private Type type; // physical or virtual
        private String id;



        public Cart(String type) {
            this.date = LocalDate.now();
            idTotal += 1;
            this.id = String.valueOf(idTotal);
            switch (type.trim().toUpperCase()) {
                case "VIRTUAL" -> this.type = Type.VIRTUAL;
                case "PHYSICAL" -> this.type = Type.PHYSICAL;
            }

            cartProducts = new HashMap<>();
        }


        public void addItem(String sku){
            if (type == Type.PHYSICAL) {

                if (!cartProducts.containsKey(sku)) {
                    if (aisleInventory.get(sku).reserveItem(1)) {
                        cartProducts.put(sku, 1);
                    }
                } else {
                    if (aisleInventory.get(sku).reserveItem(1)) {
                        cartProducts.replace(sku, cartProducts.get(sku) + 1);
                    }
                }
            } else {
                // virtual cart
            }
        }

        public void removeItem(String sku){
            if(!cartProducts.containsKey(sku)) {
                System.out.println("Not such Item on cart");
            } else {
                aisleInventory.get(sku).releaseItem(cartProducts.get(sku));
                cartProducts.remove(sku);
            }
        }

        public void printSaleSlip(){
            int totalValue = 0;
            System.out.print("Cart ID: " + id );
            cartProducts.forEach((k, v) -> {
                System.out.printf("%-10s %-10s",aisleInventory.get(k).getName(),
                        aisleInventory.get(k).getSalesPrice());

            });

        }

        public String getId() {
            return id;
        }



        public LocalDate getDate() {
            return date;
        }

        public HashMap<String, Integer> getCartProducts() {
            return cartProducts;
        }
    }

    private HashMap<String, InventoryItem> inventory;
    private HashMap<String,  InventoryItem> aisleInventory = new HashMap<>(); // display physical on store shelves
    private HashMap<String, Cart> carts = new HashMap<>();

    //constructor
    public StoreLogic(){
        inventory = new HashMap<>(ProductData.getData());
        aisleInventory = new HashMap<>();
    }


    public void setAisleInventory(){

        aisleInventory.put("FAT-HAR-CUS1746",
                new InventoryItem(25000, 0,1,
                        inventory.get("FAT-HAR-CUS1746").moveProduct(1)));
        aisleInventory.put("CB5-HON-NAK500",
                new InventoryItem(25000, 0,1,
                        inventory.get("CB5-HON-NAK500").moveProduct(1)));
        aisleInventory.put("ADV-KTM-TRA790",
                new InventoryItem(25000, 0,1,
                        inventory.get("ADV-KTM-TRA790").moveProduct(1)));
        aisleInventory.put("R1-YAM-SPO1000",
                new InventoryItem(25000, 0,1,
                        inventory.get("R1-YAM-SPO1000").moveProduct(1)));

//        aisleInventory.put("FAT-HAR-CUS1746", inventory.get("FAT-HAR-CUS1746").moveProduct(1))

    }

    // print lists
    public void printList() {
        inventory.forEach((k,v) -> System.out.println(v));
    }
    public void printAisleList(){
        aisleInventory.forEach((k,v) -> System.out.println(v));
    }


    public String manageStoreCart(){
        Cart cart = new Cart("PHYSICAL");
        carts.put(cart.getId(),cart);
        cart.addItem("FAT-HAR-CUS1746");
        cart.printSaleSlip();
        return  cart.getId();
    }

    public void checkoutCart(){

    }

    public void abandonCart(){
        // abandonCart if date is different from actual date
    }

    public void listProductByCategory(){

    }


    // test
    public void getCartsId() {
        carts.forEach((k,v) -> System.out.println("ID: " + k + " cart:" + v));
    }


}
