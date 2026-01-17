public class DialogueOption {

    public DialogueOption(String text, String response){}

    public String getText() {}

    public String getResponse() {}

    public boolean endsGame() {}

    public boolean isVictory() {}

    public Quest getQuestToGive() {}

    public Item getItemToGive() {}

    public void setEndsGame(boolean endsGame, boolean isVictory){}

    public void setQuestToGive(Quest quest){}

    public void setItemToGive(Item item){}
}
