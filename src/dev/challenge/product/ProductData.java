package dev.challenge.product;

import java.util.HashMap;
import java.util.Scanner;

public class ProductData {
    private static final String product = """
            R1 1000, Yamaha, SPORT, 20000, 10, 4
            Fat Boy 1746, Harley, CUSTOM, 25000, 6, 2
            CB500 500, Honda, NAKED, 7000, 20, 6
            Adventure 790, KTM, TRAIL, 15000, 8, 2
            Bike 250, Generic, DEFAULT, 3000, 16, 4
            """;
    public static HashMap<String, InventoryItem> getData() {
        Scanner scanner = new Scanner(product);
        HashMap<String, InventoryItem> dataList = new HashMap<>();

        while(scanner.hasNext()) {
            String[] data = scanner.nextLine().split(",");
            Product product1 = new Product(data[0].trim().toUpperCase(),
                    data[1].trim().toUpperCase(), data[2].trim().toUpperCase());

            InventoryItem item = new InventoryItem(Integer.parseInt(data[3].trim()),Integer.parseInt(data[5].trim()),
                    Integer.parseInt(data[4].trim()), product1);

            dataList.put(item.getProduct().getSku(), item);
        }
        return dataList;
    }

}
