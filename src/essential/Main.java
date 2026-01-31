package essential;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String worldFile = "world.json";
        if (args.length > 0){
            worldFile = args[0];
        }

        Game game = new Game(worldFile);
        game.start();
    }
}