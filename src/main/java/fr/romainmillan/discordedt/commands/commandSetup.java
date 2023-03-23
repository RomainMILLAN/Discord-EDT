package fr.romainmillan.discordedt.commands;

import fr.romainmillan.discordedt.App;
import fr.romainmillan.discordedt.databases.ConfigurationDatabase;
import fr.romainmillan.discordedt.embedCrafter.EmbedCrafter;
import fr.romainmillan.discordedt.embedCrafter.ErrorCrafter;
import fr.romainmillan.discordedt.manager.Logger;
import fr.romainmillan.discordedt.messages.LoggerMessages;
import fr.romainmillan.discordedt.object.Configuration;
import fr.romainmillan.discordedt.states.PluginName;
import fr.romainmillan.discordedt.states.QueueAfterTimes;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class commandSetup extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if(e.getName().equals("setup")){
            Role sudo = e.getGuild().getRoleById(App.idSudo);

            if(e.getMember().getRoles().contains(sudo)){
                String groupe = e.getOption("groupe").getAsString();

                Configuration configuration = new Configuration(groupe);
                ConfigurationDatabase.setupGroupe(configuration);

                e.replyEmbeds(EmbedCrafter.embedCraftWithDescriptionAndColor(":white_check_mark: Initialisation du groupe `"+groupe+"` réussi", Color.GREEN)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                e.getChannel().sendMessage("> Merci de redémarrer le bot.").queue((m) -> m.delete().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.CONF.getMessage(), "Initialisation du groupe `"+groupe+"`", e.getGuild(), e.getMember(), true);
            }else {
                e.replyEmbeds(ErrorCrafter.errorNotPermissionToCommand(e.getMember())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.CONF.getMessage(), LoggerMessages.USER_NO_PERMISSION.getMessage(), e.getGuild(), e.getMember(), false);
            }
        }
    }
}
