package fr.romainmillan.discordedt.manager;

import fr.romainmillan.discordedt.App;
import fr.romainmillan.discordedt.states.ConsoleState;

public class Console {
    private static Console instance = null;

    /**
     * Constructeur de singleton.
     */
    public Console() {}

    /**
     * Retourne une instance et la crée si elle n'existe pas.
     * @return
     */
    public static Console getInstance() {
        if(instance == null){
            instance = new Console();
        }

        return instance;
    }

    /**
     * Envoye un message dans la console formater
     * @param message
     * @param state
     */
    public void toConsole(String message, ConsoleState state){
        if(state == ConsoleState.DEBUG){
            System.out.println(ConsoleColor.BLACK_BACKGROUND_BRIGHT.getConsoleColor() + "[" + DateHourFormatter.getInstance().getDateAndHourTimeFormat() + "]" +  ConsoleColor.RESET.getConsoleColor() + " - " + this.getStateFinalConsole(state) + " - " + message);
        }else {
            System.out.println(ConsoleColor.BLACK_BACKGROUND_BRIGHT.getConsoleColor() + "[" + DateHourFormatter.getInstance().getDateAndHourTimeFormat() + "]" +  ConsoleColor.RESET.getConsoleColor() + " - " + this.getStateFinalConsole(state) + " - " + message);
        }
    }

    /**
     * Retourne le status final avec les couleurs console
     * @param state
     * @return
     */
    private String getStateFinalConsole(ConsoleState state){
        if(state == ConsoleState.INFO){
            return ConsoleColor.BLUE.getConsoleColor() + "INFO" + ConsoleColor.RESET.getConsoleColor();
        }else if(state == ConsoleState.LOG){
            return ConsoleColor.YELLOW.getConsoleColor() + "DISCORD SENTRY" + ConsoleColor.RESET.getConsoleColor();
        }else if(state == ConsoleState.DEBUG){
            return ConsoleColor.PURPLE.getConsoleColor() + "DEBUG" + ConsoleColor.RESET.getConsoleColor();
        }else if(state == ConsoleState.CONSOLE)
            return ConsoleColor.CYAN.getConsoleColor() + "CONSOLE" + ConsoleColor.RESET.getConsoleColor();


        return ConsoleColor.RED.getConsoleColor() + "ERREUR" + ConsoleColor.RESET.getConsoleColor();
    }
}
