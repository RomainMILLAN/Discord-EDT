package fr.romainmillan.discordedt;

import fr.romainmillan.discordedt.commands.*;
import fr.romainmillan.discordedt.manager.CommandManager;
import fr.romainmillan.discordedt.manager.Configuration;
import fr.romainmillan.discordedt.manager.ConsoleManager;
import fr.romainmillan.discordedt.manager.UploadManager;
import fr.romainmillan.discordedt.states.ConsoleState;
import fr.romainmillan.discordedt.states.EnvironementState;
import fr.romainmillan.discordedt.states.messages.application.BotMessages;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.ArrayList;
import java.util.Timer;

public class App {
    final public static String urlLinkToBDD = "jdbc:sqlite:DiscordEDT.db";
    private static JDA jda;
    public static String idSudo = "";
    public static ArrayList<String> notificationCurrent = new ArrayList<>();
    public static ArrayList<Timer> timersNotification = new ArrayList<>();

    private static Configuration configuration;
    private static EnvironementState environementState = EnvironementState.PRODUCTION;
    private static boolean debugingState = false;

    private static String guildId = "";

    /**
     * Run the bot
     * <pre/>
     * 
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        App.configuration = new Configuration();

        if(App.configuration.getConfiguration("APP_ENV").equals("DEVELOP"))
            App.environementState = EnvironementState.DEVELOPMENT;

        if(App.configuration.getConfiguration("APP_DEBUGING").equalsIgnoreCase("true"))
            App.debugingState = true;

        App.guildId = App.configuration.getConfiguration("GUILD_ID");

        App.idSudo = App.configuration.getConfiguration("R_OP");

        App.runJdaBot();
    }

    private static void runJdaBot() throws InterruptedException {
        Runtime.getRuntime().addShutdownHook(new Thread(App::stop));

        App.jda = JDABuilder.createDefault(App.configuration.getConfiguration("BOT_TOKEN"))
                .setIdle(true)
                .enableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE)
                .setActivity(Activity.watching(BotMessages.ACTIVITY_PLAYING_BOT.getMessage()))
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_PRESENCES)
                .setEnableShutdownHook(true)
                .build();

        App.jda.awaitStatus(JDA.Status.INITIALIZING);
        ConsoleManager.getInstance().toConsole(BotMessages.JDA_BOT_INITIALIZING.getMessage(), ConsoleState.INFO);

        App.jda.awaitStatus(JDA.Status.CONNECTED);
        ConsoleManager.getInstance().toConsole(BotMessages.JDA_BOT_CONNECTED.getMessage(), ConsoleState.INFO);

        App.jda.awaitReady();
        ConsoleManager.getInstance().toConsole(BotMessages.JDA_BOT_READY.getMessage(), ConsoleState.INFO);
        UploadManager.sendConnectedMessage();

        App.updateCommands();
    }

    public static void updateCommands() {
        jda.addEventListener(new commandEDT());
        jda.addEventListener(new commandNotification());
        jda.addEventListener(new commandNext());

        jda.addEventListener(new commandSetup());
        jda.addEventListener(new commandConfiguration());

        jda.getGuildById(App.guildId).updateCommands().addCommands(CommandManager.updateSlashCommands()).queue();
    }

    private static void stop() {
        UploadManager.sendDisconnectmessage();
    }

    public static String getConfiguration(String configurationKey) {
        return App.configuration.getConfiguration(configurationKey);
    }

    public static EnvironementState getEnvironementState() {
        return environementState;
    }

    public static boolean isDebuging() {
        return debugingState;
    }

    public static JDA getJda() {
        return jda;
    }

    public static String getGuildId() {
        return guildId;
    }
}
