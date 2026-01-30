package commands;
import essential.Game;

public class ExamineCommand implements Command{
    private Game game;

    public ExamineCommand(Game game){
        this.game = game;
    }

    public void execute(){
        game.getPlayer().getCurrentLocation().examine();
    }
}
