package essential;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private Inventory inventory;
    private Notebook notebook;
    private List<Quest> activeQuests;
    private List<Quest> completedQuests;
    private Location currentLocation;
    private boolean gameEnded;
    private boolean victory;

    public Player(String name, int inventorySize) {
        this.name = name;
        this.inventory = new Inventory(inventorySize);
        this.notebook = new Notebook();
        this.activeQuests = new ArrayList<>();
        this.completedQuests = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Notebook getNotebook() {
        return notebook;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public boolean hasGameEnded() {
        return gameEnded;
    }

    public boolean hasWon() {
        return victory;
    }

    public void setCurrentLocation(Location location) {
        this.currentLocation = location;
    }

    public void addQuest(Quest quest) {
        if (!hasQuest(quest.getId())){
            activeQuests.add(quest);
        }
    }

    public void completeQuest(String questId) {
        for (Quest quest : activeQuests){
            if (quest.getId().equals(questId)){
                quest.complete();
                completedQuests.add(quest);
                activeQuests.remove(quest);
                System.out.println("\n[Úkol dokončen: " + quest.getName() + "]");
                return;
            }
        }
    }

    public boolean hasCompletedQuest(String questId) {
        for (Quest quest : completedQuests){
            if (quest.getId().equals(questId)){
                return true;
            }
        }
        return false;
    }

    public boolean hasQuest(String questId) {
        for (Quest quest: activeQuests){
            if (quest.getId().equals(questId)){
                return true;
            }
        }
        return hasCompletedQuest(questId);
    }

    public void endGame(boolean victory) {
        this.gameEnded = true;
        this.victory = victory;
    }
}
