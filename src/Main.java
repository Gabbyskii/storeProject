import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        Store fotex = new Store("Litto Store");

        FileIO file = new FileIO();
        ArrayList<Item>loadedItems = file.itemReader("src/items.csv");
        for (Item it: loadedItems){
            fotex.addToItemInventory(it);
        }

        fotex.storeMenu();


    }

}
