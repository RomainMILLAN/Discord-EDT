package fr.romainmillan.discordedt.manager;

import fr.romainmillan.discordedt.databases.EDTDatabase;
import fr.romainmillan.discordedt.object.Cour;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class EDTService {

    /**
     * Retourne le prochain cour
     * <pre/>
     * @param date
     * @param time
     * @param groupe
     * @return
     */
    public static Cour getNextCour(String date, String time, String groupe){
        Cour resultat = EDTDatabase.getNextCour(date, time,groupe);

        if(resultat == null){
            date = getNextDate(date);

            resultat = EDTDatabase.getNextCour(date, "00:00",groupe);
        }

        return resultat;
    }

    /**
     * Retourne le prochain cour de la journée
     * <pre/>
     * @param groupe
     * @return
     */
    public static Cour getNextCourToday(String groupe){
        DateTimeFormatter daysFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        DateTimeFormatter hoursFormatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalTime localTime = LocalTime.now();

        String date = daysFormatter.format(LocalDate.now());
        String[] hoursSplit = hoursFormatter.format(localTime).split(":");
        String hours = hoursSplit[0] + ":" + hoursSplit[1];

        return getNextCour(date, hours, groupe);
    }

    public static String getNextDate(String date){
        String[] dateSplit = date.split("/");
        // [01] [02] [2023]

        int days = Integer.parseInt(dateSplit[0]);
        int months = Integer.parseInt(dateSplit[1])-1;
        int years = Integer.parseInt(dateSplit[2]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, days);
        calendar.set(Calendar.MONTH, months);
        calendar.set(Calendar.YEAR, years);
        calendar.add(Calendar.DATE, 1);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH)+1;
        int year = calendar.get(Calendar.YEAR);

        String dayStr = String.valueOf(day);
        String monthStr = String.valueOf(month);
        String yearStr = String.valueOf(year);

        if(day < 10)
            dayStr = "0" + day;

        if(month < 10)
            monthStr = "0" + month;

        String resultat = dayStr + "/" + monthStr + "/" + yearStr;


        return resultat;
    }
}
