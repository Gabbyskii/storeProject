package util;

import domain.Item;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class FileIO {

    public ArrayList<Item> itemReader(String file) {
        ArrayList<Item> items = new ArrayList<>();

        try (BufferedReader bReader = new BufferedReader(new FileReader(file))) {
            String line = bReader.readLine();
            line = bReader.readLine();

            while ((line != null)) {
                if (!line.isBlank()) {
                    String[] parts = line.split(",");
                    String itemName = parts[0].trim();
                    double price = Double.parseDouble(parts[1].trim());
                    items.add(new Item(itemName, price));
                }
                line = bReader.readLine();
            }
            System.out.println("Successfully loaded items!");
        } catch (IOException e) {
            System.out.println("Could not load items: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Bad price format in CSV: " + e.getMessage());
        }

        return items;
    }

}