package commands;
import essential.Game;

public class QuitCommand implements Command{
    private Game game;

    public QuitCommand(Game game){
        this.game = game;
    }

    public void execute(){
        System.out.println("Opravdu chceš ukončit svoji hru? (ano/ne)");
        String confirmation = game.getScanner().nextLine().toLowerCase();
        if (confirmation.equals("ano")){
            game.setRunning(false);
            System.out.println("Hra ukončena. Děkuji za hraní!");
        }
    }

}
