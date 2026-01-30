package essential;
import java.util.*;

 public class Inventory {
    private int maxSlots;
    private int usedSlots;
    private List<Item> items;

    public Inventory(int maxSlots) {
        this.maxSlots = maxSlots;
        this.usedSlots = 0;
        this.items = new ArrayList<>();
    }

    public boolean addItem(Item item) {
        if (hasSpace(item.getSize())){
            items.add(item);
            usedSlots += item.getSize();
            return true;
        }
        return false;
    }

    public boolean removeItem(String itemName) {
        for (Item item : items){
            if (item.getName().equalsIgnoreCase(itemName)){
                items.remove(item);
                usedSlots -= item.getSize();
                return true;
            }
        }
        return false;
    }

    public Item getItem(String itemName) {
        for (Item item : items){
            if (item.getName().equalsIgnoreCase(itemName)){
                return item;
            }
        }
        return null;
    }

    public boolean hasItem(String itemName) {
        return getItem(itemName) != null;
    }

    public boolean hasSpace(int size) {
        return (usedSlots + size) <= maxSlots;
    }

    public void showStatus() {
        System.out.println("\n=== INVENTÁŘ ===");
        if (items.isEmpty()){
            System.out.println("Inventář je prázdný.");
        } else {
            System.out.println("Předměty v batohu:");
            for (Item item : items){
                System.out.println(" -" + item.getName() + " (velikost: " + item.getSize() + ")");
            }
        }
        System.out.println("Využito políček: " + usedSlots + "/" + maxSlots);

    }
}
