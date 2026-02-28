import DataLoading.Game;
import DataLoading.GameData;
import DataLoading.GameDataLoader;
import DataLoading.JSONParser;
import gameEntities.*;
import org.junit.Test;
import java.io.IOException;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit testy pro projekt IslandEscape pomocí JUnit Jupiter (JUnit 5).
 */
public class IslandEscapeTest {
    private static final String GAME_DATA = "gamedata.json";

    /**
     * Test 1 - Inventory.addItem() a hasItem()
     * Ověřuje, že se předmět přidá do inventáře a že předmět přesahující kapacitu přidán není.
     */
    @Test
    public void testInventory_addAndHasItem() {
        Inventory inv = new Inventory(5);
        Item kokos = new Item("kokos", "Cerstvý kokos", 2);
        Item velkyPredmet = new Item("truhla", "Tezka truhla", 10);

        assertTrue(inv.addItem(kokos),
                "Predmet (velikost 2) by mel byt pridan do inventare s kapacitou 5");
        assertTrue(inv.hasItem("kokos"),
                "Predmet 'kokos' by mel byt v inventari");
        assertFalse(inv.addItem(velkyPredmet),
                "Predmet presahujici kapacitu by nemel byt pridan");
        assertFalse(inv.hasItem("truhla"),
                "Predmet 'truhla' by nemel byt v inventari");
    }

    /**
     * Test 2 - Inventory.removeItem()
     * Ověřuje odebrání předmětu a uvolnění kapacity inventáře.
     */
    @Test
    public void testInventory_removeItem() {
        Inventory inv = new Inventory(5);
        Item item = new Item("kokos", "Kokos", 3);
        inv.addItem(item);

        assertTrue(inv.removeItem("kokos"),
                "Odebrani existujiciho predmetu by melo vratit true");
        assertFalse(inv.hasItem("kokos"),
                "Po odebrani by predmet nemel byt v inventari");
        assertFalse(inv.removeItem("kokos"),
                "Odebrani neexistujiciho predmetu by melo vratit false");

        Item velky = new Item("poklad", "Poklad", 5);
        assertTrue(inv.addItem(velky),
                "Po odebrani by se mel velky predmet vejit");
    }

    /**
     * Test 3 - Player: addQuest(), completeQuest(), hasQuest(), hasCompletedQuest()
     * Ověřuje celý životní cyklus questu u hráče.
     */
    @Test
    public void testPlayer_questLifecycle() {
        Player player = new Player("Tom", 10);
        Quest quest = new Quest("marinak_poklad", "Marinakuv poklad",
                "Najdi poklad a dones ho marinakovi.");

        assertFalse(player.hasQuest("marinak_poklad"),
                "Hrac by nemel mit quest pred jeho pridanim");

        player.addQuest(quest);
        assertTrue(player.hasQuest("marinak_poklad"),
                "Hrac by mel mit quest po addQuest()");
        assertFalse(player.hasCompletedQuest("marinak_poklad"),
                "Quest by nemel byt splneny hned po pridani");

        player.completeQuest("marinak_poklad");
        assertTrue(player.hasCompletedQuest("marinak_poklad"),
                "Quest by mel byt splneny po completeQuest()");
        assertTrue(player.hasQuest("marinak_poklad"),
                "hasQuest() by melo vracet true i pro splnene questy");
    }

    /**
     * Test 4 - Location: addItem(), getItem(), isAccessible()
     * Ověřuje správu předmětu v lokaci a podmíněný pristup do lokací.
     */
    @Test
    public void testLocation_itemsAndAccessibility() {
        Location loc = new Location("Jeskyne", "Temna jeskyne.");
        loc.setId("jeskyne");
        Item diamant = new Item("diamant", "Vzacny diamant", 1);

        loc.addItem(diamant);
        assertNotNull(loc.getItem("diamant"),
                "Predmet 'diamant' by mel byt v lokaci");
        assertNull(loc.getItem("zlato"),
                "Predmet 'zlato' by nemel byt v lokaci");

        loc.setRequiresQuest("khar_diamant");
        Player player = new Player("Tom", 10);

        assertFalse(loc.isAccessible(player),
                "Lokace by nemela byt pristupna bez splneneho questu");

        Quest q = new Quest("khar_diamant", "Kharuv diamant", "Dej diamant Kharovi.");
        player.addQuest(q);
        player.completeQuest("khar_diamant");

        assertTrue(loc.isAccessible(player),
                "Lokace by mela byt pristupna po splneni questu");
    }

    /**
     * Test 5 - JSONParser.parse()
     * Ověřuje parsovaní různých typů JSON hodnot.
     */
    @Test
    public void testJSONParser_parse() {
        Map<String, Object> result = JSONParser.parse(
                "{\"name\": \"Tom\", \"size\": 3, \"active\": true}");

        assertNotNull(result,
                "Vysledek parsovani nesmi byt null");
        assertEquals("Tom", result.get("name"),
                "Hodnota 'name' by mela byt 'Tom'");
        assertEquals(true, result.get("active"),
                "Boolean hodnota 'active' by mela byt true");

        assertNull(JSONParser.parse("[1, 2, 3]"),
                "Pole jako vstup by melo vratit null");
        assertNull(JSONParser.parse(""),
                "Prazdny string by mel vratit null");
    }

    /**
     * Test 6 - GameDataLoader.loadAllData()
     * Ověřuje načtení hernich dat ze souboru gamedata.json.
     * @throws IOException
     */
    @Test
    public void testGameDataLoader_loadAllData() throws IOException {
        GameData data = GameDataLoader.loadAllData(GAME_DATA);

        assertNotNull(data,
                "GameData by nemela byt null");
        assertEquals("plaz", data.startLocation,
                "Vychozi lokace by mela byt 'plaz'");
        assertEquals(10, data.inventorySize,
                "Velikost inventare by mela byt 10");
        assertFalse(data.locations.isEmpty(),
                "Mapa lokaci by nemela byt prazdna");
        assertTrue(data.items.containsKey("kokos"),
                "Mapa predmetu by mela obsahovat 'kokos'");
        assertFalse(data.characters.isEmpty(),
                "Mapa postav by nemela byt prazdna");
    }

    /**
     * Test 7 - Game.initializeGame() a moveToLocation()
     * Ověřuje inicializaci hry a pohyb hráče mezi lokacemi.
     * @throws IOException
     */
    @Test
    public void testGame_initAndMove() throws IOException {
        Game game = new Game(GAME_DATA);
        game.initializeGame();

        assertNotNull(game.getPlayer(),
                "Hrac by nemel byt null po inicializaci");
        assertEquals("Pláž", game.getPlayer().getCurrentLocation().getName(),
                "Hrac by mel zacinat na 'Plaz'");
        assertFalse(game.getPlayer().hasGameEnded(),
                "Hra by nemela byt ukoncena hned po startu");

        Location pred = game.getPlayer().getCurrentLocation();
        game.moveToLocation("neexistuje");
        assertSame(pred, game.getPlayer().getCurrentLocation(),
                "Neplatny pohyb by nemel zmenit lokaci");

        game.moveToLocation("more");
        assertEquals("Moře", game.getPlayer().getCurrentLocation().getName(),
                "Hrac by mel byt na 'More' po prikazu 'more'");
    }
}
