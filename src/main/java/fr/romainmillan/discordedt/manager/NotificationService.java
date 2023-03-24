package fr.romainmillan.discordedt.manager;

import fr.romainmillan.discordedt.NotificationTask;
import fr.romainmillan.discordedt.object.Cour;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class NotificationService {
    /**
     * Mets en place la prochaine notifications.
     * <pre/>
     * 
     * @param groupe nom du groupe auquel il faut changer la prochaien notification
     */
    public static void nextTimer(String groupe){

        Cour current = EDTService.getNextCourToday(groupe);
        //Cour current = new Cour(1, "infoq5", "Test de cour", "INFO A2", "NADAL CYRILLE", "12:24", "16:30", "23/03/2023", "");

        if(current != null){
            setNotificationToCour(current);
        }
    }

    /**
     * Permet de mettre en palce la notiofication pour un cour donné
     * <pre/>
     * 
     * @param cour
     */
    public static void setNotificationToCour(Cour cour){
        int[] hourTab = getHourAndMinByString(cour.getHeureDebut());

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourTab[0]);
        calendar.set(Calendar.MINUTE, hourTab[1]);
        calendar.set(Calendar.SECOND, 0);

        String[] dateSplit = cour.getDate().split("/");
        int date = Integer.parseInt(dateSplit[0]);
        int month = Integer.parseInt(dateSplit[1]);
        int year = Integer.parseInt(dateSplit[2]);
        calendar.set(Calendar.DAY_OF_MONTH, date);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.YEAR, year);

        Date time = calendar.getTime();
        Timer timer = new Timer();

        timer.schedule(new NotificationTask(cour), time);
    }

    /**
     * Divise l'heure et les minutes
     * <pre/>
     * 
     * @param hour Heure à diviser
     * @return Tableau contenant en 0 les heures et en 1 les minutes.
     */
    public static int[] getHourAndMinByString(String hour){
        String[] hourTab = hour.split(":");
        int[] res = new int[2];
        res[0] = Integer.parseInt(hourTab[0]);
        res[1] = Integer.parseInt(hourTab[1]);

        return res;
    }
}
