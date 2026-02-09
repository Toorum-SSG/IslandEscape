package Dialogue;
import java.util.ArrayList;
import java.util.List;

public class DialogueBranch {
    private String greeting;
    private List<DialogueOption> options;

    public DialogueBranch(String greeting) {
        this.greeting = greeting;
        this.options = new ArrayList<>();
    }

    public void addOption(DialogueOption option) {
        options.add(option);
    }

    public String getGreeting() {
        return greeting;
    }
    public List<DialogueOption> getOptions() {
        return options;
    }
}
