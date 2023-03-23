package fr.romainmillan.discordedt;

import fr.romainmillan.discordedt.commands.commandNotification;
import fr.romainmillan.discordedt.databases.EDTDatabase;
import fr.romainmillan.discordedt.embedCrafter.EDTCrafter;
import fr.romainmillan.discordedt.manager.EDTConfiguration;
import fr.romainmillan.discordedt.manager.EDTService;
import fr.romainmillan.discordedt.manager.NotificationService;
import fr.romainmillan.discordedt.object.Cour;
import net.dv8tion.jda.api.entities.Role;

import java.util.TimerTask;

public class NotificationTask extends TimerTask {

    private Cour cour;

    public NotificationTask(Cour cour) {
        this.cour = cour;
    }

    @Override
    public void run() {
        boolean notificationSend = false;

        NotificationService.nextTimer(cour.getGroupe());

        App.getJda().getTextChannelById(EDTConfiguration.IDC_NOTIFICATION).sendMessageEmbeds(EDTCrafter.craftNotification(cour)).queue();
        Role idrNotification = null;

        if (cour.getGroupe().equals("infoq5")) {
            idrNotification = App.getJda().getRoleById(EDTConfiguration.IDR_NOTIFICATION_Q5);
            notificationSend = true;
        } else if (cour.getGroupe().equals("infos6")){
            idrNotification = App.getJda().getRoleById(EDTConfiguration.IDR_NOTIFICATION_S6);
            notificationSend = true;
        }



        if(notificationSend){
            Cour next = EDTService.getNextCour(cour.getDate(), cour.getHeureFin(), cour.getGroupe());

            if(idrNotification != null)
                App.getJda().getTextChannelById(EDTConfiguration.IDC_NOTIFICATION).sendMessage(idrNotification.getAsMention() + " > Prochain cour **" + next.getName() + "**").queue();
            else
                App.getJda().getTextChannelById(EDTConfiguration.IDC_NOTIFICATION).sendMessage(idrNotification.getAsMention()).queue();
        }else{
            if(idrNotification != null)
                App.getJda().getTextChannelById(EDTConfiguration.IDC_NOTIFICATION).sendMessage(idrNotification.getAsMention()).queue();
        }
    }
}
