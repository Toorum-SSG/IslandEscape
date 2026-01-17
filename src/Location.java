import java.util.*;

 public class Location {

    public Location(String name, String description) {}

    public String getName() {}
    public String getDescription() {}

    public void addItem(Item item) {}

    public boolean removeItem(String itemName) {}

    public Item getItem(String itemName) {}

    public void addCharacter(GameCharacter character) {}

    public GameCharacter getCharacter(String characterName) {}

    public void addConnection(String direction, String locationName) {}

    public String getConnectedLocation(String direction) {}

    public void setRequiresQuest(String questId) {}

    public void setRequiresItem(String itemId) {}

    public boolean isAccessible(Player player) {}

    public void examine() {}

    public List<Item> getItems() {}

    public List<GameCharacter> getCharacters() {}
}
