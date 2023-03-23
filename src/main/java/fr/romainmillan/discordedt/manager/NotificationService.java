package fr.romainmillan.discordedt.manager;

import fr.romainmillan.discordedt.NotificationTask;
import fr.romainmillan.discordedt.object.Cour;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class NotificationService {
    public static void nextTimer(String groupe){

        //Cour current = EDTService.getNextCourToday(groupe);
        Cour current = new Cour(1, "infoq5", "Test de cour", "INFO A2", "NADAL CYRILLE", "07:11", "16:30", "23/03/2023", "Test");

        if(current != null){
            setNotificationToCour(current);
        }
    }

    public static void setNotificationToCour(Cour cour){
        int[] hourTab = getHourAndMinByString(cour.getHeureDebut());

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourTab[0]);
        calendar.set(Calendar.MINUTE, hourTab[1]);
        System.out.println(hourTab[0] + " " + hourTab[1]);
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
