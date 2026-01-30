package commands;
import essential.Game;

public class ShowStatusCommand implements Command{
    private Game game;

    public ShowStatusCommand(Game game){
        this.game = game;
    }

    public void execute(){
        game.getPlayer().getInventory().showStatus();
    }
}
