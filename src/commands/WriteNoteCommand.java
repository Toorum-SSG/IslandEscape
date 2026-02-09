package commands;
import DataLoading.Game;

public class WriteNoteCommand implements Command{
    private Game game;

    public WriteNoteCommand(Game game){
        this.game = game;
    }

    public void execute(){
        System.out.print("Tvůj zápis: ");
        String note = game.getScanner().nextLine();
        game.getPlayer().getNotebook().addNote(note);
    }
}
