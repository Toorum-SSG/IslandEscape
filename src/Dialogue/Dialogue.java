package Dialogue;
import gameEntities.GameCharacter;
import gameEntities.Player;

import java.util.*;

/**
 * Reprezentuje dialogový systém pro interakci hráče s herní postavou.
 * Dialog se skládá z uvítacího textu, seznamu možností odpovědí a volitelných větví dialogu.
 * Větve umožňují rozdílné dialogy v závislosti na stavu hráče (např. po splnění questu).
 */
 public class Dialogue {
    private String greeting;
    private List<DialogueOption> options;
    private Map<String, DialogueBranch> branches;
    private DialogueBranch currentBranch;

    public Dialogue(String greeting) {
        this.greeting = greeting;
        this.options = new ArrayList<>();
        this.branches = new HashMap<>();
        this.currentBranch = null;
    }

    public void addOption(DialogueOption option) {
        options.add(option);
    }

    /**
      * Spustí interaktivní dialog s hráčem.
      * Nejprve zkontroluje, zda má být aktivována speciální větev dialogu
      * (např. po získání klíčového předmětu). Poté zobrazí uvítání a možnosti odpovědi
      * a zpracuje výběr hráče.
      *
      * @param scanner skener pro čtení vstupu od hráče
      * @param player  hráč, který vede rozhovor
      * @param speaker herní postava, se kterou hráč mluví
      */
    public void start(Scanner scanner, Player player, GameCharacter speaker) {
        if (currentBranch == null && branches.containsKey("after_quest")) {
            if (speaker.getName().equals("marinak") && player.getInventory().hasItem("poklad")) {
                currentBranch = branches.get("after_quest");
                System.out.println("\n[Mariňák vidí, že máš poklad!]");
            }
            else if (speaker.getName().equals("khar") && player.getInventory().hasItem("diamant")) {
                currentBranch = branches.get("after_quest");
                System.out.println("\n[Khar cítí přítomnost diamantu!]");
            }
        }

        String greetingText = (currentBranch != null) ? currentBranch.getGreeting() : greeting;
        List<DialogueOption> currentOptions = (currentBranch != null) ? currentBranch.getOptions() : options;

        System.out.println("\n" + greetingText);

        if (currentOptions.isEmpty()){
            return;
        }

        System.out.println("\nMožnosti odpovědi:");
        for (int i = 0; i < currentOptions.size(); i++){
            System.out.println((i + 1) + ". " + currentOptions.get(i).getText());
        }

        System.out.print("\nZvol odpověď (1-" + currentOptions.size() + "): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice >= 0 && choice < currentOptions.size()) {
                processChoice(choice, player, currentOptions, speaker);
            } else {
                System.out.println("Neplatná volba!");
            }
        }catch (NumberFormatException e){
            System.out.println("Zadej číslo odpovědi.");
        }
    }

    /**
      * Zpracuje hráčovu volbu v dialogu – zobrazí odpověď postavy, přidělí quest nebo předmět,
      * přepne do jiné větve dialogu nebo ukončí hru.
      *
      * @param choice         index zvolené možnosti (0-based)
      * @param player         hráč vedoucí rozhovor
      * @param currentOptions seznam aktuálně dostupných možností
      * @param speaker        postava, se kterou hráč mluví
      */
    private void processChoice(int choice, Player player, List<DialogueOption> currentOptions, GameCharacter speaker) {
        DialogueOption option = currentOptions.get(choice);
        System.out.println("\n" + option.getResponse());

        if (option.getQuestToGive() != null) {
            player.addQuest(option.getQuestToGive());
            System.out.println("\n[Nový úkol: " + option.getQuestToGive().getName() + "]");
        }

        if (option.getItemToGive() != null) {
            System.out.println("DEBUG: Attempting to give item: " + option.getItemToGive().getName());

            if (speaker.getName().equals("marinak")) {
                if (player.getInventory().hasItem("poklad")) {
                    player.getInventory().removeItem("poklad");
                    System.out.println("[Předal jsi poklad mariňákovi]");
                }
            }

            if (speaker.getName().equals("khar")) {
                if (player.getInventory().hasItem("diamant")) {
                    player.getInventory().removeItem("diamant");
                    System.out.println("[Předal jsi diamant Kharovi]");
                }
            }

            if (player.getInventory().addItem(option.getItemToGive())) {
                System.out.println("\n[Získán předmět: " + option.getItemToGive().getName() + "]");

                if (speaker.getName().equals("marinak")) {
                    player.completeQuest("marinak_poklad");
                    currentBranch = null;
                }
                if (speaker.getName().equals("khar")) {
                    player.completeQuest("khar_diamant");
                    currentBranch = null;
                }
            } else {
                System.out.println("\n[Nemáš místo v batohu!]");
            }
        }

        if (option.getBranchTo() != null && branches.containsKey(option.getBranchTo())) {
            currentBranch = branches.get(option.getBranchTo());
            System.out.println("\n[Pokračovat v konverzaci? Promluv s postavou znovu]");
        } else if (currentBranch != null && option.getItemToGive() == null) {
            currentBranch = null;
        }

        if (option.endsGame()) {
            player.endGame(option.isVictory());
        }
    }

    public void addBranch(String id, DialogueBranch branch) {
         branches.put(id, branch);
     }
 }
