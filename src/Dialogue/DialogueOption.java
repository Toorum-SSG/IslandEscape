package Dialogue;
import gameEntities.Item;
import gameEntities.Quest;

/**
 * Reprezentuje jednu možnost odpovědi v dialogu.
 * Každá možnost má text zobrazovaný hráči, odpověď postavy a volitelné efekty
 * jako přidělení questu, předmětu, přechod do jiné větve nebo ukončení hry.
 */
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

    /**
     * Vrátí text možnosti zobrazovaný hráči.
     *
     * @return text možnosti
     */
    public String getText() {
        return text;
    }

    /**
     * Vrátí text odpovědi postavy po výběru této možnosti.
     *
     * @return text odpovědi
     */
    public String getResponse() {
        return response;
    }

    /**
     * Zjistí, zda výběr této možnosti ukončí hru.
     *
     * @return {@code true} pokud tato možnost ukončí hru
     */
    public boolean endsGame() {
        return endsGame;
    }

    /**
     * Zjistí, zda ukončení hry způsobené touto možností je výhrou.
     *
     * @return {@code true} pokud jde o výhru, {@code false} pokud o prohru
     */
    public boolean isVictory() {
        return isVictory;
    }

    /**
     * Vrátí quest, který bude hráči přidělen po výběru této možnosti.
     *
     * @return quest k přidělení, nebo {@code null}
     */
    public Quest getQuestToGive() {
        return questToGive;
    }

    /**
     * Vrátí předmět, který bude hráči předán po výběru této možnosti.
     *
     * @return předmět k předání, nebo {@code null}
     */
    public Item getItemToGive() {
        return itemToGive;
    }

    /**
     * Nastaví quest, který bude hráči přidělen po výběru této možnosti.
     *
     * @param quest quest k přidělení
     */
    public void setQuestToGive(Quest quest){
        this.questToGive = quest;
    }

    /**
     * Vrátí identifikátor dialogové větve, do které se přejde po výběru této možnosti.
     *
     * @return identifikátor cílové větve, nebo {@code null}
     */
    public String getBranchTo() {
        return branchTo;
    }

    /**
     * Nastaví identifikátor dialogové větve, do které se přejde po výběru této možnosti.
     *
     * @param branchTo identifikátor cílové větve
     */
    public void setBranchTo(String branchTo) {
        this.branchTo = branchTo;
    }

    /**
     * Nastaví předmět, který bude hráči předán po výběru této možnosti.
     *
     * @param item předmět k předání
     */
    public void setItemToGive(Item item) {
        this.itemToGive = item;
    }

    /**
     * Nastaví, zda výběr této možnosti ukončí hru, a zda jde o výhru nebo prohru.
     *
     * @param endsGame  {@code true} pokud má tato možnost ukončit hru
     * @param isVictory {@code true} pokud jde o výhru, {@code false} pokud o prohru
     */
    public void setEndsGame(boolean endsGame, boolean isVictory) {
        this.endsGame = endsGame;
        this.isVictory = isVictory;
    }
}
