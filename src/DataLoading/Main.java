package DataLoading;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String dataFile = "gamedata.json";
        if (args.length > 0) {
            dataFile = args[0];
        }
        Game game = new Game(dataFile);
        game.start();
    }
}