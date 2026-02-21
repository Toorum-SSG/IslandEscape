package test;

import gameEntities.*;
import Dialogue.*;
import DataLoading.JSONParser;
import java.util.Map;

/**
 * Testovací třída pro hru IslandEscape.
 * Obsahuje 7 unit testů pokrývajících klíčové metody programu.
 * Spusť pomocí: java -cp out test.GameTest
 */
public class GameTest {
    private static int passed = 0;
    private static int failed = 0;

    private static void assertTrue(String testName, boolean condition) {
        if (condition) {
            System.out.println("  [PASS] " + testName);
            passed++;
        } else {
            System.out.println("  [FAIL] " + testName);
            failed++;
        }
    }

    private static void assertEquals(String testName, Object expected, Object actual) {
        boolean ok = expected == null ? actual == null : expected.equals(actual);
        if (ok) {
            System.out.println("  [PASS] " + testName);
            passed++;
        } else {
            System.out.println("  [FAIL] " + testName + " | očekáváno: " + expected + ", bylo: " + actual);
            failed++;
        }
    }

    /**
     * Test 1: Ověřuje přidávání a odebírání předmětů z inventáře a správu kapacity.
     */
    static void testInventory() {
        System.out.println("\n=== Test 1: Inventory ===");
        Inventory inv = new Inventory(5);

        Item maly = new Item("maly", "malý předmět", 2);
        Item velky = new Item("velky", "velký předmět", 4);

        assertTrue("addItem - přidání předmětu (size 2, kapacita 5)", inv.addItem(maly));
        assertTrue("hasItem - předmět existuje v inventáři", inv.hasItem("maly"));
        assertTrue("addItem - přidání předmětu přesahuje kapacitu (size 4, zbývá 3)", !inv.addItem(velky));
        assertTrue("removeItem - odebrání existujícího předmětu", inv.removeItem("maly"));
        assertTrue("hasItem - předmět po odebrání neexistuje", !inv.hasItem("maly"));
        assertTrue("addItem - po odebrání se velký předmět vejde (size 4, kapacita 5)", inv.addItem(velky));
    }

    /**
     * Test 2: Ověřuje správu questů hráče – přidání, splnění a kontrolu stavu.
     */
    static void testPlayerQuests() {
        System.out.println("\n=== Test 2: Player Questy ===");
        Player player = new Player("Tom", 10);
        Quest q = new Quest("q1", "Najdi poklad", "Hledej poklad na pláži.");

        assertTrue("hasQuest před přidáním - quest neexistuje", !player.hasQuest("q1"));
        player.addQuest(q);
        assertTrue("hasQuest po addQuest - quest existuje", player.hasQuest("q1"));
        assertTrue("hasCompletedQuest před splněním - quest není dokončen", !player.hasCompletedQuest("q1"));
        player.completeQuest("q1");
        assertTrue("hasCompletedQuest po completeQuest - quest je dokončen", player.hasCompletedQuest("q1"));
        assertTrue("hasQuest po dokončení - stále vrací true", player.hasQuest("q1"));
    }

    /**
     * Test 3: Ověřuje přidávání předmětů a postav do lokace a přístupnost lokace.
     */
    static void testLocation() {
        System.out.println("\n=== Test 3: Location ===");
        Location loc = new Location("Pláž", "Nádherná písečná pláž.");
        loc.setId("plaz");
        Item kokos = new Item("kokos", "kokos ze stromu", 1);
        GameCharacter postava = new GameCharacter("marinak", "starý mariňák", false);

        loc.addItem(kokos);
        loc.addCharacter(postava);

        assertTrue("getItem - předmět nalezen v lokaci", loc.getItem("kokos") != null);
        assertTrue("getCharacter - postava nalezena v lokaci", loc.getCharacter("marinak") != null);
        assertTrue("removeItem - odebrání předmětu z lokace", loc.removeItem("kokos"));
        assertTrue("getItem po odebrání - předmět již není", loc.getItem("kokos") == null);
        assertEquals("getId - ID lokace je správné", "plaz", loc.getId());
    }

    /**
     * Test 4: Ověřuje přístupnost lokace podmíněnou questem.
     */
    static void testLocationAccessibility() {
        System.out.println("\n=== Test 4: Location Accessibility ===");
        Location loc = new Location("Základna", "Tajná základna.");
        loc.setRequiresQuest("khar_diamant");

        Player player = new Player("Tom", 10);
        Quest q = new Quest("khar_diamant", "Kharův diamant", "Dej diamant Kharovi.");

        assertTrue("isAccessible bez questu - přístup zamítnut", !loc.isAccessible(player));
        player.addQuest(q);
        player.completeQuest("khar_diamant");
        assertTrue("isAccessible po splnění questu - přístup povolen", loc.isAccessible(player));
    }

    /**
     * Test 5: Ověřuje parsování jednoduchého JSON objektu.
     */
    static void testJSONParser() {
        System.out.println("\n=== Test 5: JSONParser ===");
        String json = "{\"name\": \"kokos\", \"size\": \"2\", \"valid\": true}";
        Map<String, Object> result = JSONParser.parse(json);

        assertTrue("parse - výsledek není null", result != null);
        assertEquals("parse - name je kokos", "kokos", result.get("name"));
        assertEquals("parse - size je 2", "2", result.get("size"));
        assertEquals("parse - valid je true", true, result.get("valid"));
    }

    /**
     * Test 6: Ověřuje DialogueOption – text, odpověď a nastavení konce hry.
     */
    static void testDialogueOption() {
        System.out.println("\n=== Test 6: DialogueOption ===");
        DialogueOption opt = new DialogueOption("Chci utéct!", "Dobrá volba.");

        assertEquals("getText - správný text možnosti", "Chci utéct!", opt.getText());
        assertEquals("getResponse - správná odpověď", "Dobrá volba.", opt.getResponse());
        assertTrue("endsGame výchozí stav - false", !opt.endsGame());

        opt.setEndsGame(true, true);
        assertTrue("endsGame po setEndsGame - true", opt.endsGame());
        assertTrue("isVictory po setEndsGame(true, true) - true", opt.isVictory());

        DialogueOption opt2 = new DialogueOption("Vzdávám se.", "Prohrál jsi.");
        opt2.setEndsGame(true, false);
        assertTrue("isVictory po setEndsGame(true, false) - false", !opt2.isVictory());
    }

    /**
     * Test 7: Ověřuje metodu endGame a stavy výhry/prohry hráče.
     */
    static void testPlayerGameEnd() {
        System.out.println("\n=== Test 7: Player endGame ===");
        Player winner = new Player("Tom", 10);
        Player loser = new Player("Tom", 10);

        assertTrue("hasGameEnded před koncem - false", !winner.hasGameEnded());
        winner.endGame(true);
        assertTrue("hasGameEnded po endGame - true", winner.hasGameEnded());
        assertTrue("hasWon po výhře - true", winner.hasWon());

        loser.endGame(false);
        assertTrue("hasGameEnded po prohře - true", loser.hasGameEnded());
        assertTrue("hasWon po prohře - false", !loser.hasWon());
    }

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║    IslandEscape - Unit Testy             ║");
        System.out.println("╚══════════════════════════════════════════╝");

        testInventory();
        testPlayerQuests();
        testLocation();
        testLocationAccessibility();
        testJSONParser();
        testDialogueOption();
        testPlayerGameEnd();

        System.out.println("\n══════════════════════════════════════════");
        System.out.println("Výsledek: " + passed + " prošlo / " + (passed + failed) + " celkem");
        if (failed == 0) {
            System.out.println("✓ Všechny testy prošly!");
        } else {
            System.out.println("✗ " + failed + " test(ů) selhalo.");
        }
        System.out.println("══════════════════════════════════════════");
    }
}