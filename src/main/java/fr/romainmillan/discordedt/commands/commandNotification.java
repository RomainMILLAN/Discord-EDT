package fr.romainmillan.discordedt.commands;

import fr.romainmillan.discordedt.App;
import fr.romainmillan.discordedt.embedCrafter.EmbedCrafter;
import fr.romainmillan.discordedt.embedCrafter.ErrorCrafter;
import fr.romainmillan.discordedt.manager.Logger;
import fr.romainmillan.discordedt.manager.NotificationService;
import fr.romainmillan.discordedt.messages.LoggerMessages;
import fr.romainmillan.discordedt.states.PluginName;
import fr.romainmillan.discordedt.states.QueueAfterTimes;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

public class commandNotification extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if(e.getName().equals("notification")){
            Role sudo = e.getGuild().getRoleById(App.idSudo);

            if(e.getMember().getRoles().contains(sudo)){
                String groupe = null;
                if(e.getOption("groupe") != null){
                    groupe = e.getOption("groupe").getAsString();
                }

                String action = null;
                if(e.getOption("action") != null){
                    action = e.getOption("action").getAsString();
                }

                if(action != null){
                    NotificationService.deleteAllNotifications();

                    e.replyEmbeds(EmbedCrafter.embedCraftWithDescriptionAndColor(":white_check_mark: Notifications supprimée", Color.GREEN)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                    Logger.getInstance().toLog(PluginName.EDT.getMessage(), "Notifications supprimée", e.getGuild(), e.getMember(), true);
                }else {
                    NotificationService.nextTimer(groupe);

                    e.replyEmbeds(EmbedCrafter.embedCraftWithDescriptionOnly(":white_check_mark: Notification activée pour le groupe `"+groupe+"`")).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                    Logger.getInstance().toLog(PluginName.EDT.getMessage(), "Notification de cour pour le groupe `"+groupe+"`", e.getGuild(), e.getMember(), true);
                }
            }else {
                e.replyEmbeds(ErrorCrafter.errorNotPermissionToCommand(e.getMember())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.EDT.getMessage(), LoggerMessages.USER_NO_PERMISSION.getMessage(), e.getGuild(), e.getMember(), false);
            }
        }
    }
}
