package commands;
import essential.Game;

public class MoveCommand implements Command{
    private Game game;
    private String locationName;

    public MoveCommand(Game game, String locationName){
        this.game = game;
        this.locationName = locationName;
    }

    public void execute(){
        game.moveToLocation(locationName);
    }
}
