import java.util.ArrayList;

public class Store {

    private String storeName;
    private ArrayList<Item>items = new ArrayList<>();

    public Store(String storeName) {
        this.storeName = storeName;
    }


    public void addItem(Item item){
        items.add(item);
        System.out.println("Item added: "+ item);

    }

    public double getTotalPrice(){
        double total = 0;
        for (Item it: items){
            total += it.getPrice();
        }
        return total;
    }

    public void printReceipt(){
        int count = 1;
        System.out.println("\n===Receipt for your Shopping today===");
        for (Item it: items){
            System.out.println(count + ". "+ it.getItemName() + " ("+ it.getPrice()+ "kr)");
            count++;
        }
        System.out.println("==Total price of items: "+ getTotalPrice() + "kr==");
    }


    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return  storeName + ": "+ items;
    }

}
