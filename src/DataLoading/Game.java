package DataLoading;
import commands.CommandParser;
import gameEntities.*;
import java.io.IOException;
import java.util.*;

public class Game {
    private Map<String, Location> locations;
    private Player player;
    private Scanner scanner;
    private boolean running;
    private String dataFilePath;
    private CommandParser commandParser;

    public Game(String dataFilePath) {
        this.dataFilePath = dataFilePath;
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
                System.out.print("\n>>");
                String input = scanner.nextLine().trim();

                if (!input.isEmpty()) {
                    commandParser.parse(input);
                }
            }

            if (player.hasGameEnded()){
                if (player.hasWon()){
                    System.out.println("\n" + "=".repeat(50));
                    System.out.println("Gratuluji! Hra je vyhrána!");
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

    public void initializeGame() throws IOException {
        try {
            GameData gameData = GameDataLoader.loadAllData(dataFilePath);
            this.locations = gameData.locations;
            player = new Player("Tom", gameData.inventorySize);
            player.setCurrentLocation(locations.get(gameData.startLocation));

        } catch (Exception e) {
            System.err.println("CHYBA: " + e.getMessage());
            e.printStackTrace();
            throw new IOException("Nelze načíst herní data", e);
        }
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
            if (!player.hasCompletedQuest("khar_diamant")) {
                System.out.println("\n╔════════════════════════════════════════════════════╗");
                System.out.println("║  Stojíš před masivní ocelovou branou základny.   ║");
                System.out.println("║  Je zamčená a vypadá neproniknutelně.             ║");
                System.out.println("╚════════════════════════════════════════════════════╝");
                System.out.println();
                System.out.println("Potřebuješ zjistit, jak se sem dostat. Možná někdo");
                System.out.println("na ostrově ví, jak otevřít tuto bránu...");
                return;
            }

            if (!player.getInventory().hasItem("kod")) {
                System.out.println("\n╔════════════════════════════════════════════════════╗");
                System.out.println("║  Našel jsi vchod do základny!                     ║");
                System.out.println("║  Před tebou je trezor s číselným zámkem.          ║");
                System.out.println("╚════════════════════════════════════════════════════╝");
                System.out.println();
                System.out.println("Potřebuješ číselný kód k otevření trezoru.");
                System.out.println("Khar ti řekl, že kód majímrtví vojáci v moři...");
                System.out.println();
                System.out.println("[Vrať se a hledej kód v moři u pláže!]");
                return;
            }

            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║  ZÁKLADNA PÁNA OSTROVA                             ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
            System.out.println();
            System.out.println("Vstupuješ do základny. Před tebou je trezor s číselným");
            System.out.println("zámkem, ve kterém leží nejspíše klíčky od helikoptéry!");
            System.out.println();
            System.out.print("Zadej číselný kód (4 číslice): ");
            String code = scanner.nextLine();

            if (code.equals("4782")) {
                System.out.println();
                System.out.println("╔════════════════════════════════════════════════════╗");
                System.out.println("║              *KLIKNUTÍ*                            ║");
                System.out.println("║          Trezor se otevírá!                        ║");
                System.out.println("╚════════════════════════════════════════════════════╝");
                System.out.println();
                System.out.println("Bereš klíčky a běžíš k helikoptéře na střeše!");
                System.out.println("Motor startuje... VRRRRRR!");
                System.out.println("Vzlétnul jsi! Ostrov mizí v dálce.");
                System.out.println();
                System.out.println("═══════════════════════════════════════════════════");
                System.out.println("          🎉 GRATULACE! VÝHRÁL JSI! 🎉");
                System.out.println("═══════════════════════════════════════════════════");
                player.endGame(true);
                return;
            } else {
                System.out.println();
                System.out.println("╔════════════════════════════════════════════════════╗");
                System.out.println("║            ⚠️  ŠPATNÝ KÓD! ⚠️                      ║");
                System.out.println("║          ALARM! POPLACH!                           ║");
                System.out.println("╚════════════════════════════════════════════════════╝");
                System.out.println();
                System.out.println("Ozývájí se sirény! Musíš rychle pryč!");
                System.out.println("Vrátil ses zpět do džungle.");
                System.out.println();
                System.out.println("[Zkontroluj kód, který máš v inventáři - použij příkaz 'prohlednout']");
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
        System.out.println("mapa             - Zobraz mapu");
        System.out.println("prohledej        - Prozkoumej současnou lokaci");
        System.out.println("seber <předmět>  - Seber předmět");
        System.out.println("vyhod <předmět>  - Vyhod předmět");
        System.out.println("prohlednout <předmět> - Zobrazí parametry předmětu");
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

    public void examineItem(String itemName) {
        Player player = getPlayer();
        Item itemInInventory = player.getInventory().getItem(itemName);

        if (itemInInventory != null) {
            displayItemDetails(itemInInventory, "v inventáři");
            return;
        }

        Item itemInLocation = player.getCurrentLocation().getItem(itemName);

        if (itemInLocation != null) {
            displayItemDetails(itemInLocation, "v místnosti");
            return;
        }

        System.out.println("Předmět '" + itemName + "' nebyl nalezen.");
        System.out.println("Není ani v inventáři, ani v této místnosti.");
    }

    private void displayItemDetails(Item item, String location) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║      DETAILY PŘEDMĚTU                  ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();
        System.out.println("  Název:       " + item.getName());
        System.out.println("  Popis:       " + item.getDescription());
        System.out.println("  Velikost:    " + item.getSize() + " políček");
        System.out.println("  Umístění:    " + location);
        System.out.println();
        System.out.println("────────────────────────────────────────");
    }

    public void showMap() {
        String currentLoc = player.getCurrentLocation().getId();

        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║              🗺️  MAPA OSTROVA   🗺️             ║");
        System.out.println("╚═════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("                      [ ZAKLADNA ]");
        System.out.println("                           |");
        System.out.println("                           |");
        System.out.println("        [ CHRAM ]     [ DZUNGLE ]     [ JESKYNE ]");
        System.out.println("             |            / \\            /");
        System.out.println("             |           /   \\          /");
        System.out.println("             |          /     \\        /");
        System.out.println("        [ OKRAJ ]------+       +------+");
        System.out.println("           /   \\                \\");
        System.out.println("          /     \\                \\");
        System.out.println("         /       \\                \\");
        System.out.println("    [ MORE ]   [ PLAZ ]         [ TABOR ]");
        System.out.println();

        System.out.println("─────────────────────────────────────────────────────────────");
        System.out.println("  📍 AKTUÁLNÍ POLOHA: " + getLocationDisplayName(currentLoc));
        System.out.println("─────────────────────────────────────────────────────────────");
        System.out.println();
    }

    private String getLocationDisplayName(String locationId) {
        Location loc = locations.get(locationId);
        return loc != null ? loc.getName() : locationId.toUpperCase();
    }
}
