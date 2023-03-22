package fr.romainmillan.discordedt.commands;

import fr.romainmillan.discordedt.App;
import fr.romainmillan.discordedt.NotificationTask;
import fr.romainmillan.discordedt.databases.EDTDatabase;
import fr.romainmillan.discordedt.embedCrafter.EmbedCrafter;
import fr.romainmillan.discordedt.embedCrafter.ErrorCrafter;
import fr.romainmillan.discordedt.manager.EDTConfiguration;
import fr.romainmillan.discordedt.manager.Logger;
import fr.romainmillan.discordedt.messages.EDTMessages;
import fr.romainmillan.discordedt.messages.ErrorMessages;
import fr.romainmillan.discordedt.messages.LoggerMessages;
import fr.romainmillan.discordedt.object.Cour;
import fr.romainmillan.discordedt.states.PluginName;
import fr.romainmillan.discordedt.states.QueueAfterTimes;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class commandNotification extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if(e.getName().equals("notification")){
            Role sudo = e.getGuild().getRoleById(EDTConfiguration.IDR_SUDO);

            if(e.getMember().getRoles().contains(sudo)){
                String groupe = e.getOption("groupe").getAsString();

                nextTimer(groupe);

                e.replyEmbeds(EmbedCrafter.embedCraftWithDescriptionOnly(":white_check_mark: Notification activée pour le groupe `"+groupe+"`")).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.EDT.getMessage(), "Notification de cour pour le groupe `"+groupe+"`", e.getGuild(), e.getMember(), true);
            }else {
                e.replyEmbeds(ErrorCrafter.errorNotPermissionToCommand(e.getMember())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.EDT.getMessage(), LoggerMessages.USER_NO_PERMISSION.getMessage(), e.getGuild(), e.getMember(), false);
            }
        }
    }

    public static void nextTimer(String groupe){

        DateTimeFormatter days = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        DateTimeFormatter hour = DateTimeFormatter.ofPattern("HH:mm");
        String date = days.format(LocalDate.now());
        LocalTime localTime = LocalTime.now();
        String[] dateSplitIF = hour.format(localTime).split(":");
        String time = dateSplitIF[0] + ":" + dateSplitIF[1];

        Cour current = EDTDatabase.getNextCour(date, time,groupe);
        //Cour current = new Cour(1, "infoq5", "Test de cour", "INFO A2", "NADAL CYRILLE", "16:18", "16:30", "22/03/2023", "Test");

        if(current == null){
            String[] dateSplit = date.split("/");
            int dayInt = Integer.parseInt(dateSplit[0]) + 1;
            date = dayInt + "/" + dateSplit[1] + "/" + dateSplit[2];

            current = EDTDatabase.getNextCour(date, "00:00", groupe);
        }

        if(current != null){
            int hourDate = Integer.parseInt(dateSplitIF[0]);
            int hourMin = Integer.parseInt(dateSplitIF[1]);

            String hourStartIF[] = current.getHeureDebut().split(":");
            int hourStart = Integer.parseInt(hourStartIF[0]);
            int minStart = Integer.parseInt(hourStartIF[1]);

            if(hourStart > hourDate){
                setNotificationToCour(current);
            }else if(hourStart == hourDate&& minStart > hourMin){
                setNotificationToCour(current);
            }else {
                current = EDTDatabase.getNextCour(date, time,groupe);
            }
        }
    }

    public static void setNotificationToCour(Cour cour){
        int[] hourTab = getHourAndMinByString(cour.getHeureDebut());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourTab[0]);
        calendar.set(Calendar.MINUTE, hourTab[1]);
        calendar.set(Calendar.SECOND, 0);
        Date time = calendar.getTime();

        Timer timer = new Timer();
        timer.schedule(new NotificationTask(cour), time);
    }

    public static int[] getHourAndMinByString(String hour){
        String[] hourTab = hour.split(":");
        int[] res = new int[2];
        res[0] = Integer.parseInt(hourTab[0]);
        res[1] = Integer.parseInt(hourTab[1]);

        return res;
    }
}
