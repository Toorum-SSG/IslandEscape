package commands;
import essential.Game;

public class ReadNotesCommand implements Command{
    private Game game;

    public ReadNotesCommand(Game game){
        this.game = game;
    }

    public void execute(){
        game.getPlayer().getNotebook().showNotes();
    }
}
