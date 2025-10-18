package dev.challenge.product;

public class InventoryItem  {

    private Product product;
    private int qtyTotal;
    private int qtyReserved;
    private int qtyReorder;
    private int qtyLow;
    private int salesPrice;


    public InventoryItem(int salesPrice, int qtyLow, int qtyTotal, Product product) {
        this.salesPrice = salesPrice;
        this.qtyLow = qtyLow;
        this.qtyTotal = qtyTotal;
        this.product = product;
    }

    /**
     *  change qtyReserved and qtyTotal
     * @param qty
     * @return true or false and a message
     */
    public boolean reserveItem(int qty){
        if(qtyTotal < qty) {
            System.out.println("Not enough items, only " + qtyTotal + " available in stock"); // remove the message and let other class handle it?
            return false;
        }
        qtyTotal -= qty;
        qtyReserved += qty;
        System.out.println(qty + " " + product.getSku() + " reserved "); // TODO insert name of buyer and data
        return true;
    }
    public void releaseItem(){

    }
    public void sellItem(){

    }
    public void placeInventoryOrder(){

    }

    //getters for test


    public Product getProduct() {
        return product;
    }

    public int getQtyTotal() {
        return qtyTotal;
    }

    public int getQtyReserved() {
        return qtyReserved;
    }

    public int getQtyReorder() {
        return qtyReorder;
    }

    public int getQtyLow() {
        return qtyLow;
    }

    public int getSalesPrice() {
        return salesPrice;
    }
}
