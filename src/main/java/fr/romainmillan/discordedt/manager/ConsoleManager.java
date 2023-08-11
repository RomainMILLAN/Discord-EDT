package fr.romainmillan.discordedt.manager;

import fr.romainmillan.discordedt.App;
import fr.romainmillan.discordedt.states.ConsoleColors;
import fr.romainmillan.discordedt.states.ConsoleState;
import fr.romainmillan.discordedt.states.EnvironementState;

public class ConsoleManager {
    private static ConsoleManager INSTANCE = null;

    public ConsoleManager() {}

    public static ConsoleManager getInstance() {
        if(ConsoleManager.INSTANCE == null)
            ConsoleManager.INSTANCE = new ConsoleManager();

        return INSTANCE;
    }

    /**
     * Affiche dans la console le message passer en paremetre avec le prefix du status.
     *
     * @param messageToConsole, message à afficher
     * @param consoleState, status de la console
     */
    public void toConsole(String messageToConsole, ConsoleState consoleState){
        if(consoleState == ConsoleState.DEBUG){
            if(App.getEnvironementState() != EnvironementState.DEVELOPMENT || !App.isDebuging())
                return;
        }


        System.out.println(ConsoleColors.BLACK_BACKGROUND_BRIGHT.getConsoleColor() + "[" + DateHourFormatter.getInstance().getDateAndHourTimeFormat() + "]" +  ConsoleColors.RESET.getConsoleColor() + " - " + consoleState.getConsolePrefix() + " - " + messageToConsole);
    }


}
