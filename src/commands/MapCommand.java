package commands;
import DataLoading.Game;

public class MapCommand implements Command {
    private Game game;

    public MapCommand(Game game) {
        this.game = game;
    }

    public void execute() {
        game.showMap();
    }
}
