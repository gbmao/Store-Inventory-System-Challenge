package dev.challenge.store;

import dev.challenge.product.InventoryItem;
import dev.challenge.product.ProductData;

import java.time.LocalDate;
import java.util.*;

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
            // TODO retirar if-else
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
                if (!cartProducts.containsKey(sku)) {
                    if (inventory.get(sku).reserveItem(1)) {
                        cartProducts.put(sku,1);
                    }
                } else {
                    if (inventory.get(sku).reserveItem(1)) {
                        cartProducts.replace(sku, cartProducts.get(sku) + 1);
                    }
                }
            }
        }

        public void removeItem(String sku, int qty){
            if(!cartProducts.containsKey(sku)) {
                System.out.println("Not such Item on cart");
            } else {
                aisleInventory.get(sku).releaseItem(qty);
                int newValue = cartProducts.get(sku) - qty;

                if(newValue > 0) {
                cartProducts.replace(sku,cartProducts.get(sku),newValue );

                } else {

                    cartProducts.remove(sku);
                }
            }

        }

        public void removeAllItems() {
            cartProducts.forEach((k,v) -> {
                aisleInventory.get(k).releaseItem(v);
            });
            cartProducts.clear();

        }


        public void printSaleSlip(){
            int totalValue = 0;
            System.out.println("Cart ID: " + id  + " Data: " + getDate());
//            cartProducts.forEach((k, v) -> {
//                int totalValue = v * aisleInventory.get(k).getSalesPrice();
//                System.out.printf("%s: %-10s Unit price:$%-10s total: $%-10s%n",v, aisleInventory.get(k).getName(),
//                        aisleInventory.get(k).getSalesPrice(), totalValue);
//
//
//            });

            for(Map.Entry<String, Integer> entry : cartProducts.entrySet()) {
                String productId = entry.getKey();
                int quantity = entry.getValue();
                int subTotal = quantity * aisleInventory.get(productId).getSalesPrice();

                System.out.printf("%3d: %-15s Unit price: $%,10d   Total: $%,12d%n",
                        quantity, aisleInventory.get(productId).getName(),
                        aisleInventory.get(productId).getSalesPrice(), subTotal);
                totalValue += subTotal;
            }

            System.out.printf("          %44s$%,12d%n", "Total cart value: ", totalValue);



        }

        //sell everything
        public void sell(){
            cartProducts.forEach((k,v) ->{
                aisleInventory.get(k).releaseItem(v);
                aisleInventory.get(k).sellItem(v);
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
                new InventoryItem(25000, 0,3,
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


    //TODO criar menu interativo para comprar
    // nao faz parte do exercício
    public String manageStoreCart(){
        Cart cart = new Cart("PHYSICAL");
        carts.put(cart.getId(),cart);
        cart.addItem("FAT-HAR-CUS1746");
        cart.addItem("FAT-HAR-CUS1746");
        cart.addItem("CB5-HON-NAK500");
        printAisleList();
        cart.printSaleSlip();
//        cart.removeItem("FAT-HAR-CUS1746", 1);
        cart.printSaleSlip();
//        cart.removeItem("FAT-HAR-CUS1746", 1);
//        cart.removeAllItems();
        cart.printSaleSlip();
        return  cart.getId();
    }

    public void checkoutCart(String id){
        carts.get(id).sell();
    }

    public void abandonCart(){
        // abandonCart if date is different from actual date
        carts.forEach((k,v) -> {
            if (!Objects.equals(v.getDate(), LocalDate.now())) {
                v.removeAllItems();
            }
        });
    }

    public void listProductByCategory(){
        Map<String, Collection<InventoryItem>> categoryMap = new HashMap<>();
        aisleInventory.forEach((k,v) -> {
            String category = v.getProduct().getCategory();

            categoryMap.computeIfAbsent(category, c-> new ArrayList<>()).add(v);
        });

        categoryMap.forEach((k,v) -> {
            System.out.println("----------------------------------");
            System.out.println("Category: " + k);
            v.forEach(System.out::println);
//            System.out.println("----------------------------------");
        });
    }


    // test
    public void getCartsId() {
        carts.forEach((k,v) -> System.out.println("ID: " + k + " cart:" + v));
    }


}
