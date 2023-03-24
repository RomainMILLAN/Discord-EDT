package fr.romainmillan.discordedt;

import fr.romainmillan.discordedt.databases.ConfigurationDatabase;
import fr.romainmillan.discordedt.embedCrafter.EDTCrafter;
import fr.romainmillan.discordedt.manager.EDTService;
import fr.romainmillan.discordedt.manager.NotificationService;
import fr.romainmillan.discordedt.object.Configuration;
import fr.romainmillan.discordedt.object.Cour;
import net.dv8tion.jda.api.entities.Role;

import java.util.TimerTask;

public class NotificationTask extends TimerTask {

    private Cour cour;

    public NotificationTask(Cour cour) {
        this.cour = cour;
    }

    /**
     * Permet de lancer la notification d'un cour
     */
    @Override
    public void run() {

        Configuration conf = ConfigurationDatabase.getConfigurationByGroupe(cour.getGroupe());
        App.getJda().getTextChannelById(conf.getChannelId()).sendMessageEmbeds(EDTCrafter.craftNotification(cour)).queue();
        NotificationService.nextTimer(cour.getGroupe());
       
        if(conf.getRolenotifid() != null){
            Role idrNotification = App.getJda().getRoleById(conf.getRolenotifid());
            Cour next = EDTService.getNextCour(cour.getDate(), cour.getHeureFin(), cour.getGroupe());

            if(next != null && next.getName() != null && !next.getName().equals(""))
                App.getJda().getTextChannelById(conf.getChannelId()).sendMessage(idrNotification.getAsMention() + " > Prochain cour **" + next.getName() + "**").queue();
            else
                App.getJda().getTextChannelById(conf.getChannelId()).sendMessage(idrNotification.getAsMention()).queue();
        }
    }
}
