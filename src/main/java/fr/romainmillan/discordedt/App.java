package fr.romainmillan.discordedt;

import fr.romainmillan.discordedt.commands.commandConfiguration;
import fr.romainmillan.discordedt.commands.commandEDT;
import fr.romainmillan.discordedt.commands.commandNotification;
import fr.romainmillan.discordedt.commands.commandSetup;
import fr.romainmillan.discordedt.manager.CommandManager;
import fr.romainmillan.discordedt.manager.Console;
import fr.romainmillan.discordedt.messages.AppMessages;
import fr.romainmillan.discordedt.states.ConsoleState;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.ArrayList;

public class App {
    final public static String urlLinkToBDD = "jdbc:sqlite:DiscordEDT.db";
    private static JDA jda;
    public static String idSudo = "";
    public static ArrayList<String> notificationCurrent = new ArrayList<>();

    /**
     * Get the token of the bot and run it.
     * <pre/>
     * 
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        
        StringBuilder TOKEN = new StringBuilder();
        for(String argument : args){
            if(argument.startsWith("--")){
                idSudo = argument.substring(2);
            }else {
                TOKEN.append(argument);
            }
        }

        run(TOKEN.toString());
    }

    /**
     * Run the bot
     * <pre/>
     * 
     * @throws InterruptedException
     */
    public static void run(String botToken) throws InterruptedException {
        jda = JDABuilder.createDefault(botToken)
                .setIdle(true)
                .enableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE)
                .setActivity(Activity.watching(AppMessages.ACTIVITY_PLAYING_BOT.getMessage()))
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_PRESENCES)
                .build();

        jda.awaitStatus(JDA.Status.INITIALIZING);
        Console.getInstance().toConsole(AppMessages.JDA_BOT_INITIALIZING.getMessage(), ConsoleState.INFO);

        jda.awaitStatus(JDA.Status.CONNECTED);
        Console.getInstance().toConsole(AppMessages.JDA_BOT_CONNECTED.getMessage(), ConsoleState.INFO);

        jda.awaitReady();
        Console.getInstance().toConsole(AppMessages.JDA_BOT_READY.getMessage(), ConsoleState.INFO);

        jda.addEventListener(new commandEDT());
        jda.addEventListener(new commandNotification());

        jda.addEventListener(new commandSetup());
        jda.addEventListener(new commandConfiguration());

        /*
        Update  all slash commands
         */
        updateSlashCommands();

        /*
        ConsoleCommander
        */
        //ConsoleCommander.consoleCommander();
    }

    /**
     * Update all the commands slash on all guild of the bot
     */
    public static void updateSlashCommands(){
        for(Guild guild : jda.getGuilds()){
            jda.getGuildById(guild.getId()).updateCommands().addCommands(CommandManager.updateSlashCommands()).queue();
        }
    }

    /**
     * Retourne le JDA en cour
     * @return
     */
    public static JDA getJda() {
        return jda;
    }
}
