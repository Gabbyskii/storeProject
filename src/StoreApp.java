import domain.Item;
import domain.Store;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import util.FileIO;
import java.util.ArrayList;


public class StoreApp extends Application {

    private Store store;
    private BorderPane root;
    private Label cartCountLabel;

    @Override
    public void start(Stage stage) {
        store = new Store("Gabski's Deli");

        FileIO file = new FileIO();
        ArrayList<Item> loadedItems = file.itemReader("src/csv/items.csv");
        for (Item it : loadedItems) {
            store.addToItemInventory(it);
        }

        root = new BorderPane();
        root.setTop(buildHeader());
        root.setCenter(buildForside());

        Scene scene = new Scene(root, 950, 650);
        stage.setScene(scene);
        stage.setTitle(store.getStoreName());
        stage.show();
    }

    // ---------- Header (top bar with title + Home/Cart buttons) ----------

    private HBox buildHeader() {
        Label title = new Label(store.getStoreName());
        title.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        title.setStyle("-fx-text-fill: rgba(234,200,255,0.83);");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button homeBtn = new Button("Home");
        homeBtn.setOnAction(e -> root.setCenter(buildForside()));

        cartCountLabel = new Label("Cart (0)");
        Button cartBtn = new Button();
        cartBtn.setGraphic(cartCountLabel);
        cartBtn.setOnAction(e -> showCart());

        HBox header = new HBox(15, title, spacer, homeBtn, cartBtn);
        header.setPadding(new Insets(15));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: #7904f4;");
        return header;
    }

    // ---------- Forside: grid of item cards ----------

    private ScrollPane buildForside() {
        FlowPane itemGrid = new FlowPane();
        itemGrid.setHgap(15);
        itemGrid.setVgap(15);
        itemGrid.setPadding(new Insets(20));

        for (Item it : store.getItems()) {
            itemGrid.getChildren().add(buildItemCard(it));
        }

        ScrollPane scroll = new ScrollPane(itemGrid);
        scroll.setFitToWidth(true);
        return scroll;
    }

    private VBox buildItemCard(Item item) {
        Label icon = new Label(emojiFor(item.getItemName()));
        icon.setFont(Font.font(40));

        Label name = new Label(item.getItemName());
        name.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        name.setWrapText(true);
        name.setAlignment(Pos.CENTER);
        name.setMaxWidth(140);

        Label price = new Label(String.format("%.2f kr", item.getPrice()));
        price.setStyle("-fx-text-fill: #7904f4; -fx-font-weight: bold;");

        Button addBtn = new Button("Add to cart");
        addBtn.setOnAction(e -> {
            int index = store.getItems().indexOf(item);
            store.addToCart(index);
            updateCartCount();
        });

        VBox card = new VBox(8, icon, name, price, addBtn);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(15));
        card.setPrefWidth(150);
        card.setStyle("-fx-background-color: rgba(238,206,253,0.83); -fx-border-color: #7904f4; "
                + "-fx-border-radius: 8; -fx-background-radius: 8;");
        return card;
    }

    // ---------- Cart view ----------

    private void showCart() {
        VBox cartLayout = new VBox(10);
        cartLayout.setPadding(new Insets(20));

        Label header = new Label("Your Cart");
        header.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        cartLayout.getChildren().add(header);

        if (store.getCart().isEmpty()) {
            cartLayout.getChildren().add(new Label("Your cart is empty."));
        } else {
            // copy the list so removing an item while looping doesn't blow up
            for (Item it : new ArrayList<>(store.getCart())) {
                HBox row = new HBox(10);
                row.setAlignment(Pos.CENTER_LEFT);

                Label name = new Label(it.getItemName() + " - " + String.format("%.2f kr", it.getPrice()));
                HBox.setHgrow(name, Priority.ALWAYS);
                name.setMaxWidth(Double.MAX_VALUE);

                Button removeBtn = new Button("Remove");
                removeBtn.setOnAction(e -> {
                    store.removeFromCart(it);
                    updateCartCount();
                    showCart(); // refresh the cart view
                });

                row.getChildren().addAll(name, removeBtn);
                cartLayout.getChildren().add(row);
            }

            Label total = new Label(String.format("Total: %.2f kr", store.getTotalPrice()));
            total.setFont(Font.font("Arial", FontWeight.BOLD, 16));

            Button checkoutBtn = new Button("Checkout");
            checkoutBtn.setStyle("-fx-background-color: #d6adff; -fx-text-fill: white;");
            checkoutBtn.setOnAction(e -> checkout());

            cartLayout.getChildren().addAll(new Separator(), total, checkoutBtn);
        }

        Button backBtn = new Button("Back to store");
        backBtn.setOnAction(e -> root.setCenter(buildForside()));
        cartLayout.getChildren().add(backBtn);

        root.setCenter(new ScrollPane(cartLayout));
    }

    private void checkout() {
        if (store.getCart().isEmpty()) {
            return;
        }

        Alert receipt = new Alert(Alert.AlertType.INFORMATION);
        receipt.setTitle("Receipt");
        receipt.setHeaderText("Thanks for shopping at " + store.getStoreName() + "!");
        receipt.setContentText(store.getReceiptText());
        receipt.showAndWait();

        store.getCart().clear();
        updateCartCount();
        root.setCenter(buildForside());
    }

    private void updateCartCount() {
        cartCountLabel.setText("Cart (" + store.getCart().size() + ")");
    }

    // ---------- Helpers ----------

    // Picks a simple emoji "icon" for an item based on keywords in its name.
    // Swap this out later for real product images if you want.
    private String emojiFor(String name) {
        String n = name.toLowerCase();
        if (n.contains("bread")) return "\uD83C\uDF5E";
        if (n.contains("milk")) return "\uD83E\uDD5B";
        if (n.contains("cheese")) return "\uD83E\uDDC0";
        if (n.contains("coffee")) return "\u2615";
        if (n.contains("tea")) return "\uD83C\uDF75";
        if (n.contains("egg")) return "\uD83E\uDD5A";
        if (n.contains("choc")) return "\uD83C\uDF6B";
        if (n.contains("soda")) return "\uD83E\uDD64";
        if (n.contains("juice")) return "\uD83E\uDDC3";
        if (n.contains("soap") || n.contains("shampoo") || n.contains("toothpaste") || n.contains("deodorant"))
            return "\uD83E\uDDF4";
        if (n.contains("rice")) return "\uD83C\uDF5A";
        if (n.contains("pasta")) return "\uD83C\uDF5D";
        if (n.contains("oil")) return "\uD83E\uDED2";
        if (n.contains("pepper")) return "\uD83C\uDF36";
        if (n.contains("fish")) return "\uD83D\uDC1F";
        if (n.contains("sugar") || n.contains("flour")) return "\uD83C\uDF3E";
        return "\uD83D\uDED2"; // generic shopping cart
    }

    public static void main(String[] args) {
        launch(args);
    }
}