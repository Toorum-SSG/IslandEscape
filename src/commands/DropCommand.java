package commands;
import essential.Game;

public class DropCommand implements Command{
    private Game game;
    private String itemName;

    public DropCommand(Game game, String itemName){
        this.game = game;
        this.itemName = itemName;
    }

    public void execute(){
        game.dropItem(itemName);
    }
}
