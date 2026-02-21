package commands;

/**
 * Spouštěč příkazů implementující vzor Command.
 * Uchovává aktuálně nastavený příkaz a na vyžádání ho provede.
 */
public class CommandInvoker {
    private Command command;


    /**
     * Nastaví příkaz, který má být proveden.
     *
     * @param command příkaz k nastavení
     */
    public void setCommand(Command command){
        this.command = command;
    }

    /**
     * Provede aktuálně nastavený příkaz.
     * Pokud žádný příkaz nastaven není, vypíše informativní zprávu.
     */
    public void executeCommand(){
        if(command != null){
            command.execute();
        }else {
            System.out.println("Není příkaz k provedení");
        }
    }
}
