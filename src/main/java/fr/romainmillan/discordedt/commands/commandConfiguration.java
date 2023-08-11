package fr.romainmillan.discordedt.commands;

import fr.romainmillan.discordedt.App;
import fr.romainmillan.discordedt.databases.ConfigurationDatabase;
import fr.romainmillan.discordedt.embedCrafter.EmbedCrafter;
import fr.romainmillan.discordedt.embedCrafter.ErrorCrafter;
import fr.romainmillan.discordedt.manager.Sentry;
import fr.romainmillan.discordedt.messages.ConfigurationMessages;
import fr.romainmillan.discordedt.messages.LoggerMessages;
import fr.romainmillan.discordedt.object.Configuration;
import fr.romainmillan.discordedt.states.LogState;
import fr.romainmillan.discordedt.states.PluginName;
import fr.romainmillan.discordedt.states.QueueAfterTimes;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class commandConfiguration extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if(e.getName().equals("configuration")){
            Role sudo = e.getGuild().getRoleById(App.idSudo);

            if(e.getMember().getRoles().contains(sudo)){
                String action = e.getOption("action").getAsString();
                String groupe = e.getOption("groupe").getAsString();
                String texte = e.getOption("texte").getAsString();
                Configuration configuration = ConfigurationDatabase.getConfigurationByGroupe(groupe);

                if(configuration == null){
                    e.replyEmbeds(ErrorCrafter.errorEmbedCrafterWithDescription(":x: " + ConfigurationMessages.NO_GROUP_FIND.getMessages())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                    Sentry.getInstance().toLog(PluginName.CONF.getMessage(),  ConfigurationMessages.NO_GROUP_FIND.getMessages(), LogState.ERROR, e.getMember(), e.getGuild());
                    return;
                }

                if(action.equals("nick")){
                    configuration.setNickgroupe(texte);
                }else if(action.equals("url")){
                    configuration.setUrl(texte);
                }else if(action.equals("idRole")){
                    configuration.setRoleid(texte);
                }else if(action.equals("idNotif")){
                    configuration.setRolenotifid(texte);
                }else if(action.equals("idChannel")){
                    configuration.setChannelId(texte);
                }else {
                    e.replyEmbeds(ErrorCrafter.errorCommand()).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                    Sentry.getInstance().toLog(PluginName.CONF.getMessage(), LoggerMessages.COMMAND_NOT_EXIST.getMessage(), LogState.ERROR, e.getMember(), e.getGuild());
                    return;
                }

                ConfigurationDatabase.updateConfiguration(configuration);
                e.replyEmbeds(EmbedCrafter.embedCraftWithDescriptionAndColor(":white_check_mark: Configuration du groupe `"+groupe+"` mise à jour", Color.GREEN)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Sentry.getInstance().toLog(PluginName.CONF.getMessage(), "Configuration `"+groupe+"` mise à jour", LogState.SUCCESSFUL, e.getMember(), e.getGuild());
                return;
            }else {
                e.replyEmbeds(ErrorCrafter.errorNotPermissionToCommand(e.getMember())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Sentry.getInstance().toLog(PluginName.CONF.getMessage(), LoggerMessages.USER_NO_PERMISSION.getMessage(), LogState.ERROR, e.getMember(), e.getGuild());
            }
        }
    }
}
