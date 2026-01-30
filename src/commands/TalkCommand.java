package commands;
import essential.Game;

public class TalkCommand implements Command{
    private Game game;
    private String characterName;

    public TalkCommand(Game game, String characterName){
        this.game = game;
        this.characterName = characterName;
    }

    public void execute(){
        game.talkToCharacter(characterName);
    }
}
