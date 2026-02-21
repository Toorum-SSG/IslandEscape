package DataLoading;
import Dialogue.Dialogue;
import Dialogue.DialogueOption;
import Dialogue.DialogueBranch;
import gameEntities.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Načítá veškerá herní data z JSON souboru a sestavuje objekty hry.
 * Zajišťuje načtení předmětů, questů, postav, dialogů a lokací včetně jejich propojení.
 */
public class GameDataLoader {

    /**
     * Načte veškerá herní data ze zadaného JSON souboru a vrátí je jako {@link GameData}.
     *
     * @param jsonFilePath cesta k JSON souboru s herními daty
     * @return objekt {@link GameData} obsahující načtené lokace, předměty, postavy, questy a nastavení
     * @throws IOException pokud soubor nelze přečíst nebo JSON nelze zparsovat
     */
    public static GameData loadAllData(String jsonFilePath) throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
        Map<String, Object> data = JSONParser.parse(jsonContent);

        if (data == null) {
            throw new IOException("Chyba při parsování JSON");
        }

        GameData gameData = new GameData();
        gameData.items = loadItems(data);
        gameData.quests = loadQuests(data);
        gameData.characters = loadCharacters(data, gameData.quests, gameData.items);
        gameData.locations = loadLocations(data, gameData.items, gameData.characters);
        gameData.startLocation = (String) data.get("startLocation");
        Object invSize = data.get("playerInventorySize");
        gameData.inventorySize = (invSize != null) ? Integer.parseInt(invSize.toString()) : 10;
        return gameData;
    }

    /**
     * Načte předměty ze surových dat JSON a vrátí je jako mapu indexovanou ID.
     *
     * @param data zparsovaná data JSON
     * @return mapa předmětů (klíč: ID předmětu, hodnota: instance {@link Item})
     */
    private static Map<String, Item> loadItems(Map<String, Object> data) {
        Map<String, Item> items = new HashMap<>();

        @SuppressWarnings("unchecked")
        List<Object> itemsList = (List<Object>) data.get("items");

        if (itemsList == null) return items;

        for (Object itemObj : itemsList) {
            @SuppressWarnings("unchecked")
            Map<String, Object> itemData = (Map<String, Object>) itemObj;

            String id = (String) itemData.get("id");
            String name = (String) itemData.get("name");
            String description = (String) itemData.get("description");
            int size = Integer.parseInt(itemData.get("size").toString());

            items.put(id, new Item(name, description, size));
        }

        return items;
    }

    /**
     * Načte questy ze surových dat JSON a vrátí je jako mapu indexovanou ID.
     *
     * @param data zparsovaná data JSON
     * @return mapa questů (klíč: ID questu, hodnota: instance {@link Quest})
     */
    private static Map<String, Quest> loadQuests(Map<String, Object> data) {
        Map<String, Quest> quests = new HashMap<>();

        @SuppressWarnings("unchecked")
        List<Object> questsList = (List<Object>) data.get("quests");

        if (questsList == null) return quests;

        for (Object questObj : questsList) {
            @SuppressWarnings("unchecked")
            Map<String, Object> questData = (Map<String, Object>) questObj;

            String id = (String) questData.get("id");
            String name = (String) questData.get("name");
            String description = (String) questData.get("description");

            Quest quest = new Quest(id, name, description);
            quests.put(id, quest);
        }

        return quests;
    }

    /**
     * Načte herní postavy ze surových dat JSON, včetně jejich dialogů.
     *
     * @param data   zparsovaná data JSON
     * @param quests mapa dostupných questů pro přiřazení k dialogovým možnostem
     * @param items  mapa dostupných předmětů pro přiřazení k dialogovým možnostem
     * @return mapa postav (klíč: ID postavy, hodnota: instance {@link GameCharacter})
     */
    private static Map<String, GameCharacter> loadCharacters(Map<String, Object> data, Map<String, Quest> quests, Map<String, Item> items) {

        Map<String, GameCharacter> characters = new HashMap<>();

        @SuppressWarnings("unchecked")
        List<Object> charsList = (List<Object>) data.get("characters");

        if (charsList == null) return characters;

        for (Object charObj : charsList) {
            @SuppressWarnings("unchecked")
            Map<String, Object> charData = (Map<String, Object>) charObj;

            String id = (String) charData.get("id");
            String name = (String) charData.get("name");
            String description = (String) charData.get("description");
            boolean hostile = (Boolean) charData.get("hostile");

            GameCharacter character = new GameCharacter(name, description, hostile);

            @SuppressWarnings("unchecked")
            Map<String, Object> dialogueData = (Map<String, Object>) charData.get("dialogue");

            if (dialogueData != null) {
                Dialogue dialogue = loadDialogue(dialogueData, quests, items);
                character.setDialogue(dialogue);
            }

            characters.put(id, character);
        }

        return characters;
    }

    /**
     * Sestaví objekt {@link Dialogue} ze surových dat JSON, včetně možností a větví.
     *
     * @param dialogueData data dialogu z JSON
     * @param quests       mapa dostupných questů
     * @param items        mapa dostupných předmětů
     * @return sestavený objekt {@link Dialogue}
     */
    private static Dialogue loadDialogue(Map<String, Object> dialogueData, Map<String, Quest> quests, Map<String, Item> items){
        String greeting = (String) dialogueData.get("greeting");
        Dialogue dialogue = new Dialogue(greeting);

        @SuppressWarnings("unchecked")
        List<Object> optionsList = (List<Object>) dialogueData.get("options");

        if (optionsList != null) {
            for (Object optObj : optionsList) {
                @SuppressWarnings("unchecked")
                Map<String, Object> optData = (Map<String, Object>) optObj;

                DialogueOption option = createDialogueOption(optData, quests, items);
                dialogue.addOption(option);
            }
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> branchesData = (Map<String, Object>) dialogueData.get("branches");

        if (branchesData != null) {
            for (Map.Entry<String, Object> branchEntry : branchesData.entrySet()) {
                @SuppressWarnings("unchecked")
                Map<String, Object> branchData = (Map<String, Object>) branchEntry.getValue();

                String branchGreeting = (String) branchData.get("greeting");
                DialogueBranch branch = new DialogueBranch(branchGreeting);

                @SuppressWarnings("unchecked")
                List<Object> branchOptions = (List<Object>) branchData.get("options");

                if (branchOptions != null) {
                    for (Object optObj : branchOptions) {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> optData = (Map<String, Object>) optObj;

                        DialogueOption option = createDialogueOption(optData, quests, items);
                        branch.addOption(option);
                    }
                }

                dialogue.addBranch(branchEntry.getKey(), branch);
            }
        }

        return dialogue;
    }

    /**
     * Vytvoří objekt {@link DialogueOption} ze surových dat JSON a nastaví jeho volitelné atributy
     * (quest, předmět, větev dialogu, ukončení hry).
     *
     * @param optData data možnosti z JSON
     * @param quests  mapa dostupných questů
     * @param items   mapa dostupných předmětů
     * @return sestavená instance {@link DialogueOption}
     */
    private static DialogueOption createDialogueOption(Map<String, Object> optData, Map<String, Quest> quests, Map<String, Item> items) {

        String text = (String) optData.get("text");
        String response = (String) optData.get("response");

        DialogueOption option = new DialogueOption(text, response);

        String questId = (String) optData.get("questToGive");
        if (questId != null && quests.containsKey(questId)) {
            option.setQuestToGive(quests.get(questId));
        }

        String itemId = (String) optData.get("itemToGive");
        if (itemId != null && items.containsKey(itemId)) {
            option.setItemToGive(items.get(itemId));
        }

        String branchTo = (String) optData.get("branchTo");
        if (branchTo != null) {
            option.setBranchTo(branchTo);
        }

        Object endsGameObj = optData.get("endsGame");
        if (endsGameObj != null && (Boolean) endsGameObj) {
            Object isVictoryObj = optData.get("isVictory");
            boolean isVictory = isVictoryObj != null && (Boolean) isVictoryObj;
            option.setEndsGame(true, isVictory);
        }

        return option;
    }

    /**
     * Načte lokace ze surových dat JSON, přiřadí k nim předměty, postavy a podmínky přístupu,
     * a poté nastaví propojení mezi lokacemi.
     *
     * @param data          zparsovaná data JSON
     * @param allItems      mapa všech dostupných předmětů
     * @param allCharacters mapa všech dostupných postav
     * @return mapa lokací (klíč: ID lokace, hodnota: instance {@link Location})
     */
    private static Map<String, Location> loadLocations(Map<String, Object> data, Map<String, Item> allItems, Map<String, GameCharacter> allCharacters) {

        Map<String, Location> locations = new HashMap<>();

        @SuppressWarnings("unchecked")
        List<Object> locationsList = (List<Object>) data.get("locations");

        for (Object locObj : locationsList) {
            @SuppressWarnings("unchecked")
            Map<String, Object> locData = (Map<String, Object>) locObj;

            String id = (String) locData.get("id");
            String name = (String) locData.get("name");
            String description = (String) locData.get("description");

            Location location = new Location(name, description);
            location.setId(id);

            @SuppressWarnings("unchecked")
            List<Object> itemIds = (List<Object>) locData.get("items");
            if (itemIds != null) {
                for (Object itemIdObj : itemIds) {
                    String itemId = (String) itemIdObj;
                    if (allItems.containsKey(itemId)) {
                        location.addItem(allItems.get(itemId));
                    }
                }
            }

            @SuppressWarnings("unchecked")
            List<Object> charIds = (List<Object>) locData.get("characters");
            if (charIds != null) {
                for (Object charIdObj : charIds) {
                    String charId = (String) charIdObj;
                    if (allCharacters.containsKey(charId)) {
                        location.addCharacter(allCharacters.get(charId));
                    }
                }
            }

            Object requiresQuestObj = locData.get("requiresQuest");
            boolean requiresQuest = requiresQuestObj != null && (Boolean) requiresQuestObj;
            if (requiresQuest) {
                String questId = (String) locData.get("requiredQuestId");
                if (questId != null) {
                    location.setRequiresQuest(questId);
                }
            }

            Object requiresItemObj = locData.get("requiresItem");
            boolean requiresItem = requiresItemObj != null && (Boolean) requiresItemObj;
            if (requiresItem) {
                String itemId = (String) locData.get("requiredItemId");
                if (itemId != null) {
                    location.setRequiresItem(itemId);
                }
            }

            locations.put(id, location);
        }

        for (Object locObj : locationsList) {
            @SuppressWarnings("unchecked")
            Map<String, Object> locData = (Map<String, Object>) locObj;

            String id = (String) locData.get("id");
            Location location = locations.get(id);

            @SuppressWarnings("unchecked")
            Map<String, Object> connections = (Map<String, Object>) locData.get("connections");

            if (connections != null) {
                for (Map.Entry<String, Object> entry : connections.entrySet()) {
                    location.addConnection(entry.getKey(), (String) entry.getValue());
                }
            }
        }

        return locations;
    }
}

