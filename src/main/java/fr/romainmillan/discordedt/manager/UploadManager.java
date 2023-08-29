package fr.romainmillan.discordedt.manager;

import fr.romainmillan.discordedt.App;
import fr.romainmillan.discordedt.embedCrafter.UploadCrafter;
import fr.romainmillan.discordedt.states.EnvironementState;

public class UploadManager {

    public static void sendConnectedMessage() {
        if(App.getEnvironementState() != EnvironementState.PRODUCTION){
            return;
        }
        
        App.getJda().getGuildById(App.getConfiguration("GUILD_ID"))
                .getTextChannelById(
                        App.getConfiguration("TC_SENTRY")
                )
                .sendMessageEmbeds(
                        UploadCrafter.craftConnectEmbed(App.getJda().getSelfUser())
                                .build()
                )
                .queue();

        DiscordWebhookApi.sendConnectedNotification();;
    }
    public static void sendDisconnectmessage() {
        if(App.getEnvironementState() != EnvironementState.PRODUCTION){
            return;
        }

        DiscordWebhookApi.sendDisconnectedNotification();
    }

}
