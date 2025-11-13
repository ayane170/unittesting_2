package test2.opdracht3;

public class InventoryService implements ProductRepository{

    public InventoryService() {
    }

    public int  getCurrentStock(String productId){return 0;}

    public InventoryStatus checkAvailability(String productId){
        int q =getCurrentStock(productId);
        if (q > 20){
            return InventoryStatus.STOCK_OK;
        } else if (q>1 && q < 10) {
            return  InventoryStatus.STOCK_LOW;
        }else
            return InventoryStatus.SOLD_OUT;
    }
//implementeer deze function een maak unittest die gebruik maakt van en gemokte instantie van ProductRepository



}
