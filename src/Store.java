import java.util.ArrayList;

public class Store {

    private String storeName;
    private ArrayList<Item>itemsInventory = new ArrayList<>();
    private ArrayList<Item> buyersCart = new ArrayList<>();
    private TextUI ui;

    public Store(String storeName) {
        this.storeName = storeName;
        ui = new TextUI();

    }

    public void addToCart(int index) {
        if (index >= 0 && index < itemsInventory.size()) {
            buyersCart.add(itemsInventory.get(index));
            System.out.println("Added to cart: " + itemsInventory.get(index));
        } else {
            System.out.println("Invalid choice.");
        }
    }

    public void addToItemInventory(Item item){
        itemsInventory.add(item);

    }

    public Item findItemByName(String name){
        for (Item it: itemsInventory){
        if (it.getItemName().equalsIgnoreCase(name)){
            System.out.println("Item found: "+ name);
           return it;
          }
        }
        System.out.println("Item invalid!!");
        return null;
    }

    public double getTotalPrice(){
        double total = 0;
        for (Item it: buyersCart){
            total += it.getPrice();
        }
        return total;
    }

    public void printReceipt(){
        int count = 1;
        System.out.println("\n===Receipt for your Shopping today===");
        for (Item it: buyersCart){
            System.out.println(count + ". "+ it.getItemName() + " ("+ it.getPrice()+ "kr)");
            count++;
        }
        System.out.println("==Total price of "+ buyersCart.size()
                +" items: "+ getTotalPrice() + "kr==");
    }

    public void storeMenu(){
        boolean runs = true;

        while (runs) {

            System.out.println("\n===MENU===");
            System.out.println("1. Add Item to cart.");
            System.out.println("2. Find item by name.");
            System.out.println("3. Pay & Print receipt.");
            System.out.println("4. Exit Online Store.");

            String choice = ui.promptText("\nChoose:");

            switch (choice){
                case "1" -> {
                    System.out.println("===Items for sale===\n");
                    for (int i = 0; i < itemsInventory.size(); i++){
                        System.out.println((i+1)+ ". "+ itemsInventory.get(i));
                    }
                    int index = Integer.parseInt(ui.promptText("Choose item number:")) - 1;
                    addToCart(index);
                }
                case "2" ->{
                    String findItem = ui.promptText("Find item by name: ");
                    findItemByName(findItem);
                }
                case "3" -> printReceipt();
                case "4" -> runs = false;
                default  -> System.out.println("Invalid choice, try again.");
            }

        }

    }


    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public ArrayList<Item> getItems() {
        return itemsInventory;
    }

    public void setItems(ArrayList<Item> items) {
        this.itemsInventory = items;
    }

    @Override
    public String toString() {
        return  storeName + ": "+ itemsInventory;
    }

}
