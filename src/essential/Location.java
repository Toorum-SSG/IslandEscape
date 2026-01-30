package essential;
import java.util.*;

 public class Location {
     private String id;
     private String name;
     private String description;
     private List<Item> items;
     private List<GameCharacter> characters;
     private Map<String, String> connections;
     private boolean requiresQuest;
     private String requiredQuestId;
     private boolean requiresItem;
     private String requiredItemId;

    public Location(String name, String description) {
        this.name = name;
        this.description = description;
        this.items = new ArrayList<>();
        this.characters = new ArrayList<>();
        this.connections = new HashMap<>();
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public boolean removeItem(String itemName) {
        for (Item item : items){
            if (item.getName().equalsIgnoreCase(itemName)){
                items.remove(item);
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

    public void addCharacter(GameCharacter character) {
        characters.add(character);
    }

    public GameCharacter getCharacter(String characterName) {
        for (GameCharacter character : characters){
            if (character.getName().equalsIgnoreCase(characterName)){
                return character;
            }
        }
        return null;
    }

    public void addConnection(String direction, String locationName) {
        connections.put(direction.toLowerCase(), locationName);
    }

    public String getConnectedLocation(String direction) {
        return connections.get(direction.toLowerCase());
    }

    public void setRequiresQuest(String questId) {
        this.requiresQuest = true;
        this.requiredQuestId = questId;
    }

    public void setRequiresItem(String itemId) {
        this.requiresItem = true;
        this.requiredItemId = itemId;
    }

    public boolean isAccessible(Player player) {
        if (requiresQuest && !player.hasCompletedQuest(requiredQuestId)) {
            System.out.println("Tato lokace ještě není dostupná. Musíš splnit určitý úkol.");
            return false;
        }
        if (requiresItem && !player.getInventory().hasItem(requiredItemId)) {
            System.out.println("K vstupu do této lokace potřebuješ " + requiredItemId + ".");
            return false;
        }
        return true;
    }

    public void examine() {
        System.out.println("\n=== " + name.toUpperCase() + " ===");
        System.out.println(description);

        if (!items.isEmpty()) {
            System.out.println("\nViditelné předměty:");
            for (Item item : items) {
                System.out.println("  - " + item.getName());
            }
        }

        if (!characters.isEmpty()) {
            System.out.println("\nPostavy na místě:");
            for (GameCharacter character : characters) {
                System.out.println("  - " + character.getName());
            }
        }

        if (!connections.isEmpty()) {
            System.out.println("\nMůžeš jít do:");
            for (String location : connections.values()) {
                System.out.println("  - " + location);
            }
        }
    }
 }



