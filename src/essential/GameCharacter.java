package essential;
import java.util.*;

public class GameCharacter {
    private String name;
    private String description;
    private Dialogue dialogue;
    private boolean hostile;

    public GameCharacter(String name, String description, boolean hostile) {
        this.name = name;
        this.description = description;
        this.hostile = hostile;
    }

    public String getName() {
        return name;
    }

    public boolean isHostile() {
        return hostile;
    }

    public void setDialogue(Dialogue dialogue) {
        this.dialogue = dialogue;
    }

    public void interact(Scanner scanner, Player player) {
        if (dialogue != null){
            dialogue.start(scanner, player);
        }else {
            System.out.println(name + " nepromluví.");
        }
    }
}
