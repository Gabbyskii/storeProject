import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        Store store1 = new Store("Litto Store");

        store1.addItem(new Item("butter", 15.00));
        store1.addItem(new Item("bread", 20.00));
        store1.addItem(new Item("chips", 18.00));
        store1.addItem(new Item("deodorant", 13.00));

        System.out.println("===Shopping cart total of ("+ store1.getItems().size() +" items)===");

        store1.printReceipt();



    }

}
