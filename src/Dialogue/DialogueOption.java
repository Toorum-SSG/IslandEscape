package Dialogue;
import gameEntities.Item;
import gameEntities.Quest;

public class DialogueOption {
    private String text;
    private String response;
    private boolean endsGame;
    private boolean isVictory;
    private Quest questToGive;
    private Item itemToGive;
    private String branchTo;

    public DialogueOption(String text, String response){
        this.text = text;
        this.response = response;
    }

    public String getText() {
        return text;
    }

    public String getResponse() {
        return response;
    }

    public boolean endsGame() {
        return endsGame;
    }

    public boolean isVictory() {
        return isVictory;
    }

    public Quest getQuestToGive() {
        return questToGive;
    }

    public Item getItemToGive() {
        return itemToGive;
    }

    public void setQuestToGive(Quest quest){
        this.questToGive = quest;
    }

    public String getBranchTo() {
        return branchTo;
    }

    public void setBranchTo(String branchTo) {
        this.branchTo = branchTo;
    }

    public void setItemToGive(Item item) {
        this.itemToGive = item;
    }

    public void setEndsGame(boolean endsGame, boolean isVictory) {
        this.endsGame = endsGame;
        this.isVictory = isVictory;
    }
}
