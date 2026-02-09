package commands;
import DataLoading.Game;

public class ExamineItemCommand implements Command {
    private Game game;
    private String itemName;

    public ExamineItemCommand(Game game, String itemName) {
        this.game = game;
        this.itemName = itemName;
    }

    @Override
    public void execute() {
        game.examineItem(itemName);
    }
}
