package essential;
import commands.CommandParser;

import java.io.IOException;
import java.util.*;

public class Game {
    private Map<String, Location> locations;
    private Player player;
    private Scanner scanner;
    private boolean running;
    private String worldFilePath;
    private CommandParser commandParser;

    public Game(String worldFilePath) {
        this.worldFilePath = worldFilePath;
        this.scanner = new Scanner(System.in);
        this.running = true;
        this.commandParser = new CommandParser(this);
    }

    public void setRunning(boolean running){
        this.running = running;
    }

    public void start() throws IOException {
        try {
            initializeGame();
            showIntro();

            player.getCurrentLocation().examine();

            while (running && !player.hasGameEnded()){
                System.out.println("\n>>");
                String input = scanner.nextLine().trim();

                if (!input.isEmpty()) {
                    commandParser.parse(input);
                }
            }

            if (player.hasGameEnded()){
                if (player.hasWon()){
                    System.out.println("\n" + "=".repeat(50));
                    System.out.println("Gratujuli! Unikl/a jsi!");
                    System.out.println("=".repeat(50));
                } else {
                    System.out.println("\n" + "=".repeat(50));
                    System.out.println("Konec hry ;(");
                    System.out.println("=".repeat(50));
                }
            }
        } finally {
            scanner.close();
        }
    }

    public void showIntro(){
        System.out.println("\n" + "=".repeat(40));
        System.out.println("            ÚTĚK Z OSTROVA");
        System.out.println("=".repeat(40));
        System.out.println("\nMladý kluk jménem Tom pochází z bohaté a vlivné rodiny.");
        System.out.println("Ke svým 14. narozeninám dostává poukaz na vyhlídkovou plavbu");
        System.out.println("luxusní jachtou po Tichém oceánu...");
        System.out.println("\nCelý výlet byla pouze zástěrka. Tom byl prodán záhadnému,");
        System.out.println("bohatému člověku jako cenný kousek do jeho sbírky.");
        System.out.println("\nLoď zasáhla náhlá řada přírodních katastrof.");
        System.out.println("Tom samým zázrakem přežil a probouzí se na nádherné");
        System.out.println("písečné pláži plné života...");
        System.out.println("\n" + "=".repeat(40));
        System.out.println("Napiš 'pomoc' pro zobrazení dostupných příkazů.\n");
    }

    public void initializeGame(){
        System.out.println("Načítám svět ze souboru: " + worldFilePath);
        locations = WorldLoader.loadWorld(worldFilePath);
        String startLocationID = WorldLoader.getStartLocation(worldFilePath);

        System.out.println("Načteno " + locations.size() + " lokací");

        Item kokos = new Item("kokos", "Čerstvý kokos", 2);
        Item kod = new Item("kod", "Kód: 4782", 1);
        Item plamenomet = new Item("plamenomet", "Mocná zbraň", 5);
        Item poklad = new Item("poklad", "Vzácný poklad", 4);
        Item diamant = new Item("diamant", "Obří diamant",3);

        locations.get("plaz").addItem(kokos);
        locations.get("more").addItem(kod);
        locations.get("chram").addItem(poklad);
        locations.get("jeskyne").addItem(diamant);

        Quest marinak_quest = new Quest("marinak_poklad", "Poklad pro mariňáka", "Mariák chce vzácný poklad z chrámu.");
        Quest khar_quest = new Quest("khar_diamant","Diamant pro Khara","Khar si brousí tesáky po jeskynním diamantu");

        GameCharacter trosecnik = new GameCharacter("trosecnik","Starý muž se šedivými vlasy",false);
        Dialogue trosecnikDialogue = new Dialogue("Trosečník: Och, další ubožák na ostrově!");
        DialogueOption t1 = new DialogueOption("","Trosečník: Musíš najít cestu před džungli");
        trosecnikDialogue.addOption(t1);
        trosecnik.setDialogue(trosecnikDialogue);

        GameCharacter marinak = new GameCharacter("marinak", "Voják", false);
        Dialogue marinakDialog = new Dialogue("Mariňák: Kdo jsi?");
        DialogueOption m1 = new DialogueOption("Můžeš mi pomoct?", "Mariňák: Přines mi poklad z chrámu a dám ti to, co potřebuješ.");
        m1.setQuestToGive(marinak_quest);
        marinakDialog.addOption(m1);
        marinak.setDialogue(marinakDialog);

        GameCharacter khar = new GameCharacter("khar", "Obrovský had", false);
        Dialogue kharDialog = new Dialogue("Khar: Ssss...");
        DialogueOption k1 = new DialogueOption("Potřebuji pomoct.", "Khar: Přinessss mi diamant!");
        k1.setQuestToGive(khar_quest);
        kharDialog.addOption(k1);
        khar.setDialogue(kharDialog);

        GameCharacter pavouk = new GameCharacter("pavouk", "Obrovský pavouk", true);

        locations.get("plaz").addCharacter(trosecnik);
        locations.get("tabor").addCharacter(marinak);
        locations.get("dzungle").addCharacter(khar);
        locations.get("jeskyne").addCharacter(pavouk);

        player = new Player("Tom", 10);
        player.setCurrentLocation(locations.get(startLocationId));
    }

    public void moveToLocation(String locationName) {
        String connectedLoc = player.getCurrentLocation().getConnectedLocation(locationName);

        if (connectedLoc == null) {
            System.out.println("Nemůžeš jít do '" + locationName + "' odsud.");
            return;
        }

        Location targetLocation = locations.get(connectedLoc);

        if (targetLocation == null) {
            System.out.println("Tato lokace neexistuje.");
            return;
        }

        if (connectedLoc.equals("okraj") && !player.getInventory().hasItem("kokos")) {
            System.out.println("\nTermiti tě napadají! Potřebuješ kokos!");
            return;
        }

        if (connectedLoc.equals("jeskyne") && !player.getInventory().hasItem("plamenomet")) {
            System.out.println("\nPavouk tě napadne! Potřebuješ plamenomet!");
            return;
        }

        if (connectedLoc.equals("zakladna")) {
            if (!player.getInventory().hasItem("kod")) {
                System.out.println("\nPotřebuješ kód!");
                return;
            }

            System.out.println("\nZadej kód (4 číslice): ");
            String code = scanner.nextLine();

            if (code.equals("4782")) {
                System.out.println("\nVYHRÁL JSI! Uprchl jsi helikoptérou!");
                player.endGame(true);
                return;
            } else {
                System.out.println("\nŠpatný kód!");
                return;
            }
        }

        if (!targetLocation.isAccessible(player)) {
            return;
        }

        player.setCurrentLocation(targetLocation);
        System.out.println("\nPřesunul ses do: " + targetLocation.getName());
        targetLocation.examine();
    }

    public void pickUpItem(String itemName) {
        Item item = player.getCurrentLocation().getItem(itemName);

        if (item == null) {
            System.out.println("Předmět '" + itemName + "' se zde nenachází.");
            return;
        }

        if (player.getInventory().addItem(item)) {
            player.getCurrentLocation().removeItem(itemName);
            System.out.println("Sebral jsi: " + item.getName());

            if (itemName.equals("poklad") && player.hasQuest("marinak_poklad")) {
                player.completeQuest("marinak_poklad");
            }

            if (itemName.equals("diamant") && player.hasQuest("khar_diamant")) {
                player.completeQuest("khar_diamant");
            }
        } else {
            System.out.println("Nemáš dost místa v batohu!");
        }
    }

    public void dropItem(String itemName) {
        Item item = player.getInventory().getItem(itemName);

        if (item == null) {
            System.out.println("Předmět '" + itemName + "' nemáš v inventáři.");
            return;
        }

        player.getInventory().removeItem(itemName);
        player.getCurrentLocation().addItem(item);
        System.out.println("Vyhodil jsi: " + itemName);
    }

    public void talkToCharacter(String characterName) {
        GameCharacter character = player.getCurrentLocation().getCharacter(characterName);

        if (character == null) {
            System.out.println("Postava '" + characterName + "' se zde nenachází.");
            return;
        }

        if (character.isHostile()) {
            System.out.println(character.getName() + " je nepřátelský!");
            return;
        }

        character.interact(scanner, player);
    }

    public void showHelp() {
        System.out.println("\n=== NÁPOVĚDA ===");
        System.out.println("\nPříkazy:");
        System.out.println("jdi <lokace>     - Přesun do jiné lokace");
        System.out.println("prohledej        - Prozkoumej současnou lokaci");
        System.out.println("seber <předmět>  - Seber předmět");
        System.out.println("vyhod <předmět>  - Vyhod předmět");
        System.out.println("mluv <postava>   - Promluv s postavou");
        System.out.println("stav             - Zobraz inventář");
        System.out.println("zapis            - Zapiš poznámku");
        System.out.println("cti              - Přečti poznámky");
        System.out.println("pomoc            - Zobraz tuto nápovědu");
        System.out.println("konec            - Ukonči hru");
    }

    public Player getPlayer(){
        return player;
    }

    public Scanner getScanner(){
        return scanner;
    }
}
