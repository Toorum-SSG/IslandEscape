package commands;
import essential.Game;

public class PickUpCommand implements Command{
    private Game game;
    private String itemName;

    public PickUpCommand(Game game, String itemName){
        this.game = game;
        this.itemName = itemName;
    }

    public void execute(){
        game.pickUpItem(itemName);
    }
}
