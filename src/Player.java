import java.util.*;

 public class Player {

    public Player(String name, int inventorySize) {}

    public String getName() {}

    public Inventory getInventory() {}

    public Notebook getNotebook() {}

    public Location getCurrentLocation() {}

    public boolean hasGameEnded() {}

    public boolean hasWon() {}

    public void setCurrentLocation(Location location) {}

    public void addQuest(Quest quest) {}

    public void completeQuest(String questId) {}

    public boolean hasCompletedQuest(String questId) {}

    public boolean hasQuest(String questId) {}

    public void endGame(boolean victory) {}

    public void showQuests() {}
}
