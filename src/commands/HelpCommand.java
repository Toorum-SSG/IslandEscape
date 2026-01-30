package commands;
import essential.Game;

public class HelpCommand implements Command{
    private Game game;

    public HelpCommand(Game game){
        this.game = game;
    }

    public void execute(){
        game.showHelp();
    }
}
