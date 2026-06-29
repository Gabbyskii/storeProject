import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        Store fotex = new Store("Litto Store");

        fotex.addToItemInventory(new Item("butter", 15.00));
        fotex.addToItemInventory(new Item("bread", 20.00));
        fotex.addToItemInventory(new Item("chips", 18.00));
        fotex.addToItemInventory(new Item("deodorant", 13.00));
        fotex.addToItemInventory(new Item("køkkenrulle", 25.49));


        fotex.storeMenu();



    }

}
