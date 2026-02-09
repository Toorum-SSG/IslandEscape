package DataLoading;
import gameEntities.GameCharacter;
import gameEntities.Item;
import gameEntities.Location;
import gameEntities.Quest;

import java.util.Map;

public class GameData {
    Map<String, Location> locations;
    Map<String, Item> items;
    Map<String, GameCharacter> characters;
    Map<String, Quest> quests;
    String startLocation;
    int inventorySize;
}
