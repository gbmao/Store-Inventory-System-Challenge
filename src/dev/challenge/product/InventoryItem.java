package dev.challenge.product;

public class InventoryItem {

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
     * change qtyReserved and qtyTotal
     *
     * @param qty
     * @return true or false and a message
     */
    public boolean reserveItem(int qty) {
        int available = qtyTotal - qtyReserved;
        if (available < qty) {
            System.out.println(product.getSku() + " Not enough items, only " + qtyTotal +
                    " available in stock"); // remove the message and let other class handle it?
            return false;
        }
//        qtyTotal -= qty;
        qtyReserved += qty;
        System.out.println(qty + " " + product.getSku() + " reserved "); // TODO insert name of buyer and data
        return true;
    }

    /**
     * Does the reverse of reserveItem() if param> qtyReserved, removes everything
     * and return it to qtyTotal
     */
    public boolean releaseItem(int qty) {
        if (qtyReserved < qty) qty = qtyReserved; // just to ensure no negative value
//        qtyTotal += qty;
        qtyReserved -= qty;
        System.out.println();
        return true;
    }

    /**
     * Sell every reserved item
     */
    public void sellItem() {
        qtyTotal -= qtyReserved;
        qtyReserved = 0;
    }

    /**
     * Sell directly from total
     *
     * @param qty
     */
    public void sellItem(int qty) {
        if ((qtyTotal - qtyReserved) < qty) {
            System.out.println("Not enough unreserved  items");
        } else {
            qtyTotal -= qty;
        }

    }

    //TODO Still not sure how this should work
    public void placeInventoryOrder() {
        if (qtyLow >= qtyTotal) {
            qtyReorder = qtyLow - 10; // 10 = the total number of items I want
        }
    }


    //
    public Product moveProduct(int qty) {
        if(reserveItem(qty)){
            return product;
        } else {
            return null;
        }
    }

    //getters for test

    public String getName() {

        return product.getName();
    }

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

    @Override
    public String toString() {
        return product.getSku() + " {" +
                "product=" + product.getName() +
                ", qtyReserved=" + qtyReserved +
                ", salesPrice=" + salesPrice +
                ", qtyTotal=" + qtyTotal +
                '}';
    }
}
