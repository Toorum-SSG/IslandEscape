package Dialogue;
import java.util.ArrayList;
import java.util.List;

/**
 * Reprezentuje větev dialogu – alternativní průběh rozhovoru s vlastním uvítáním
 * a sadou možností. Větve jsou aktivovány za splnění určitých podmínek (např. po questu).
 */
public class DialogueBranch {
    private String greeting;
    private List<DialogueOption> options;

    public DialogueBranch(String greeting) {
        this.greeting = greeting;
        this.options = new ArrayList<>();
    }

    /**
     * Přidá možnost odpovědi do této větve dialogu.
     *
     * @param option možnost, která má být přidána
     */
    public void addOption(DialogueOption option) {
        options.add(option);
    }

    public String getGreeting() {
        return greeting;
    }

    /**
     * Vrátí seznam možností odpovědí dostupných v této větvi dialogu.
     *
     * @return seznam možností
     */
    public List<DialogueOption> getOptions() {
        return options;
    }
}
