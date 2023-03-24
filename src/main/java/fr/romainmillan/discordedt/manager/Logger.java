package fr.romainmillan.discordedt.manager;

import fr.romainmillan.discordedt.states.ConsoleState;
import fr.romainmillan.discordedt.states.FileName;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

public class Logger {
    private static Logger instance = null;
    private FileName fileName = FileName.LOGGER_FILENAME;

    /**
     * Contructeur de singleton
     */
    public Logger() {}

    /**
     * Retourne un Logger et le crée s'il est null
     * @return
     */
    public static Logger getInstance() {
        if(instance == null){
            instance = new Logger();
        }

        return instance;
    }

    /**
     * Log les informations demander dans la Console, Fichier + Channel discord
     * @param title
     * @param message
     * @param g
     * @param member
     * @param status
     */
    public void toLog(String title, String message, Guild g, Member member, boolean status) {
        if(status)
            message = ":white_check_mark: " + message + " (*Success*)";
        else
            message = ":x: " + message + " (*Failed*)";

        //g.getTextChannelById("1088375925445496902").sendMessageEmbeds(LoggerCrafter.craftLogEmbedWithTitleAndMessage(title,message, member)).queue();
        Console.getInstance().toConsole("'" + g.getName() + "'" +  ConsoleColor.PURPLE.getConsoleColor()+" | " + ConsoleColor.RESET.getConsoleColor() + title + ConsoleColor.PURPLE.getConsoleColor() + " | " + ConsoleColor.RESET.getConsoleColor() + message, ConsoleState.LOG);
        WriteFile.getInstance().writeOnFile(fileName, "\n[" + DateHourFormatter.getInstance().getDateAndHourTimeFormat() + "] - " + g.getName() + " - DISCORD SENTRY/" + title + message);
    }
}
