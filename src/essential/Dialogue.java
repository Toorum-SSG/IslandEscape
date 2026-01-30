package essential;
import java.util.*;

 public class Dialogue {
    private String greeting;
    private List<DialogueOption> options;

    public Dialogue(String greeting) {
        this.greeting = greeting;
        this.options = new ArrayList<>();
    }

    public void addOption(DialogueOption option) {
        options.add(option);
    }

    public void start(Scanner scanner, Player player) {
        System.out.println("\n" + greeting);

        if (options.isEmpty()){
            return;
        }

        System.out.println("\nMožnosti odpovědi:");
        for (int i = 0; i < options.size(); i++){
            System.out.println((i + 1) + ". " + options.get(i).getText());
        }

        System.out.print("\nZvol odpověď (1-" + options.size() + "): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice >= 0 && choice < options.size()) {
                processChoice(choice, player);
            } else {
                System.out.println("Neplatná volba!");
            }
        }catch (NumberFormatException e){
            System.out.println("Zadej číslo odpovědi.");
        }
    }

    private void processChoice(int choice, Player player) {
        DialogueOption option = options.get(choice);
        System.out.println("\n" + option.getResponse());

        if (option.getQuestToGive() != null){
            player.addQuest(option.getQuestToGive());
            System.out.println("\n[Nový úkol: " + option.getQuestToGive().getName() + "]");
        }

        if (option.getItemToGive() != null){
            if (player.getInventory().addItem(option.getItemToGive())){
                System.out.println("\n[Získán předmět: " + option.getItemToGive().getName() + "]");
            }
        }

        if (option.endsGame()){
            player.endGame(option.isVictory());
        }
    }
}
