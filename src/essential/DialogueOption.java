package essential;

public class DialogueOption {
    private String text;
    private String response;
    private boolean endsGame;
    private boolean isVictory;
    private Quest questToGive;
    private Item itemToGive;

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
}
