package commands;
import DataLoading.Game;

/**
 * Zpracovává textové příkazy zadané hráčem a převádí je na příslušné {@link Command} objekty.
 * Používá vzor Command a deleguje provedení na {@link CommandInvoker}.
 */
public class CommandParser {
    private Game game;
    private CommandInvoker invoker;

    /**
     * Vytvoří nový parser příkazů pro danou instanci hry.
     *
     * @param game instance hry, se kterou příkazy pracují
     */
    public CommandParser(Game game){
        this.game = game;
        this.invoker = new CommandInvoker();
    }

    /**
     * Zparsuje textový vstup hráče, rozpozná příkaz a cíl, vytvoří příslušný
     * {@link Command} objekt a nechá ho provést přes {@link CommandInvoker}.
     * Pokud je příkaz neznámý nebo chybí povinný argument, vypíše nápovědu.
     *@param input řetězec zadaný hráčem na vstupu (např. "jdi plaz", "seber kokos")
     */
    public void parse(String input){
        String[]parts = input.toLowerCase().split(" ", 2);
        String commandWord = parts[0];
        String target = parts.length > 1 ? parts[1] : "";
        Command command = null;

        switch (commandWord){
            case "jdi":
                if(!target.isEmpty()){
                    command = new MoveCommand(game, target);
                }else {
                    System.out.println("Kam chceš jít? (použij: jdi <lokace>)");
                    return;
                }
                break;

            case "prohlednout":
                if (!target.isEmpty()) {
                    command = new ExamineItemCommand(game, target);
                } else {
                    System.out.println("Co chceš prohlédnout? (použij: prohlednout <předmět>)");
                    return;
                }
                break;

            case "prohledej":
                command = new ExamineCommand(game);
                break;

            case "seber":
                if (!target.isEmpty()){
                    command = new PickUpCommand(game, target);
                }else {
                    System.out.println("Co chceš sebrat? (použij: seber <předmět>)");
                    return;
                }
                break;

            case "vyhod":
                if (!target.isEmpty()){
                    command = new DropCommand(game, target);
                }else {
                    System.out.println("Co chceš vyhodit? (použij: vyhod <předmět>)");
                    return;
                }
                break;

            case "mluv":
                if (!target.isEmpty()){
                    command = new TalkCommand(game, target);
                }else {
                    System.out.println("S kým chceš mluvit? (použij: mluv <postava>)");
                    return;
                }
                break;

            case "stav":
                command = new ShowStatusCommand(game);
                break;

            case "zapis":
                command = new WriteNoteCommand(game);
                break;

            case "cti":
                command = new ReadNotesCommand(game);
                break;

            case "pomoc":
                command = new HelpCommand(game);
                break;

            case "konec":
            case "quit":
                command = new QuitCommand(game);
                break;

            case "mapa":
                command = new MapCommand(game);
                break;

            default:
                System.out.println("Neznámý příkaz: '" + commandWord + "'. Napiš 'pomoc' pro seznam příkazů.");
                return;
        }

        invoker.setCommand(command);
        invoker.executeCommand();
    }
}
