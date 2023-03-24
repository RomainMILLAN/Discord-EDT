package fr.romainmillan.discordedt.commands;

import fr.romainmillan.discordedt.embedCrafter.EDTCrafter;
import fr.romainmillan.discordedt.embedCrafter.ErrorCrafter;
import fr.romainmillan.discordedt.manager.EDTService;
import fr.romainmillan.discordedt.manager.Logger;
import fr.romainmillan.discordedt.messages.EDTMessages;
import fr.romainmillan.discordedt.object.Cour;
import fr.romainmillan.discordedt.states.PluginName;
import fr.romainmillan.discordedt.states.QueueAfterTimes;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.concurrent.TimeUnit;

public class commandNext extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if(e.getName().equals("next")){
            String groupe = e.getOption("groupe").getAsString();

            Cour nextCour = EDTService.getNextCourToday(groupe);

            if(nextCour != null){
                e.replyEmbeds(EDTCrafter.craftNextCourEmbed(nextCour)).queue();
                Logger.getInstance().toLog(PluginName.EDT.getMessage(), "Affichage du prochain cour (`"+nextCour.getId()+"`)", e.getGuild(), e.getMember(), true);
            }else {
                e.replyEmbeds(ErrorCrafter.errorEmbedCrafterWithDescription(":x: " + EDTMessages.NO_COUR_FOUND.getMessage())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.EDT.getMessage(), EDTMessages.NO_COUR_FOUND.getMessage(), e.getGuild(), e.getMember(), false);
            }
        }
    }
}
