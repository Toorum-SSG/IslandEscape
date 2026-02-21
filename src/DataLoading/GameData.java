package DataLoading;
import gameEntities.GameCharacter;
import gameEntities.Item;
import gameEntities.Location;
import gameEntities.Quest;
import java.util.Map;

/**
 * Datový kontejner obsahující veškerá načtená herní data.
 * Slouží jako přenosový objekt mezi {@link GameDataLoader} a třídou {@link Game}.
 */
public class GameData {
    /** Mapa všech herních lokací indexovaná jejich ID. */
    Map<String, Location> locations;
    /** Mapa všech herních předmětů indexovaná jejich ID. */
    Map<String, Item> items;
    /** Mapa všech herních postav indexovaná jejich ID. */
    Map<String, GameCharacter> characters;
    /** Mapa všech herních questů indexovaná jejich ID. */
    Map<String, Quest> quests;
    /** ID výchozí lokace, ve které hráč začíná hru. */
    String startLocation;
    /** Maximální kapacita inventáře hráče (počet políček). */
    int inventorySize;
}
