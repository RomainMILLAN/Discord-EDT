package fr.romainmillan.discordedt.commands;

import fr.romainmillan.discordedt.databases.ConfigurationDatabase;
import fr.romainmillan.discordedt.databases.EDTDatabase;
import fr.romainmillan.discordedt.embedCrafter.EDTCrafter;
import fr.romainmillan.discordedt.embedCrafter.EmbedCrafter;
import fr.romainmillan.discordedt.embedCrafter.ErrorCrafter;
import fr.romainmillan.discordedt.manager.Downloader;
import fr.romainmillan.discordedt.manager.Logger;
import fr.romainmillan.discordedt.messages.EDTMessages;
import fr.romainmillan.discordedt.object.Cour;
import fr.romainmillan.discordedt.states.PluginName;
import fr.romainmillan.discordedt.states.QueueAfterTimes;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VEvent;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class commandEDT extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if(e.getName().equals("edt")){
            String action = null;
            if(e.getOption("action") != null)
                action = e.getOption("action").getAsString();

            String groupe = ConfigurationDatabase.getListGroupe().get(0);
            if(e.getOption("groupe") != null)
                groupe = e.getOption("groupe").getAsString();

            String id = null;
            if(e.getOption("id") != null)
                id = Objects.requireNonNull(e.getOption("id")).getAsString();

            if(action != null){
                if(action.equals("refresh")){
                    if(groupe != null){
                        try {
                            e.replyEmbeds(EmbedCrafter.embedCraftWithDescriptionAndColor(":white_check_mark: " + EDTMessages.EDT_DOING_REFRSH.getMessage() + " (`"+groupe+"`)", Color.GREEN)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                            this.refreshEdtByGroupe(groupe);

                            e.getChannel().sendMessageEmbeds(EmbedCrafter.embedCraftWithDescriptionAndColor(":white_check_mark: " + EDTMessages.EDT_REFRESH.getMessage() + " (`"+groupe+"`)", Color.GREEN)).queue((m) -> m.delete().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                            Logger.getInstance().toLog(PluginName.EDT.getMessage(),  EDTMessages.EDT_REFRESH.getMessage() + " (`"+groupe+"`)", e.getGuild(), e.getMember(), true);
                        } catch (IOException | ParserException ex) {
                            throw new RuntimeException(ex);
                        }
                    }else {
                        e.replyEmbeds(ErrorCrafter.errorEmbedCrafterWithDescription(":x: " + EDTMessages.NO_GROUP_SELECT.getMessage())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                        Logger.getInstance().toLog(PluginName.EDT.getMessage(), EDTMessages.NO_GROUP_SELECT.getMessage(), e.getGuild(), e.getMember(), false);
                    }
                }

                if(action.equals("edit-info")){
                    if(e.getOption("information") != null){
                        if(id != null){
                            String information = e.getOption("information").getAsString();
                            Cour cour = EDTDatabase.getCourById(Integer.parseInt(id));

                            information = information.replace("'", " ");
                            cour.setInformation(information);
                            EDTDatabase.updateInformationOfCourse(cour);
                            e.replyEmbeds(EmbedCrafter.embedCraftWithDescriptionAndColor(":white_check_mark: " + EDTMessages.INFORMATION_UPDATE.getMessage(), Color.GREEN)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                            Logger.getInstance().toLog(PluginName.EDT.getMessage(), EDTMessages.INFORMATION_UPDATE + " (`"+id+"` -> `"+information+"`)", e.getGuild(), e.getMember(), true);
                        }else {
                            e.replyEmbeds(ErrorCrafter.errorEmbedCrafterWithDescription(":x: " + EDTMessages.NO_ID_ARGUMENT.getMessage())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                            Logger.getInstance().toLog(PluginName.EDT.getMessage(), EDTMessages.NO_ID_ARGUMENT.getMessage(), e.getGuild(), e.getMember(), false);
                        }
                    }else {
                        e.replyEmbeds(ErrorCrafter.errorEmbedCrafterWithDescription(":x: " + EDTMessages.NO_INFORMATION_ARGUMENT.getMessage())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                        Logger.getInstance().toLog(PluginName.EDT.getMessage(),  EDTMessages.NO_INFORMATION_ARGUMENT.getMessage(), e.getGuild(), e.getMember(), false);
                    }
                }
            }else {
                //SEE EDT
                String date = null;
                if(e.getOption("date") != null)
                    date = Objects.requireNonNull(e.getOption("date")).getAsString();

                if(id == null){
                    if(date == null){
                        //Date du jour
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        date = String.valueOf(dtf.format(LocalDateTime.now()));;
                    }

                    //Liste des cours
                    ArrayList<Cour> listCour = EDTDatabase.getListCourByDateAndGroupe(date, groupe);

                    if(listCour.size() != 0){
                        e.replyEmbeds(EDTCrafter.craftEDTListCour(listCour)).queue();
                        Logger.getInstance().toLog(PluginName.EDT.getMessage(), EDTMessages.SHOW_COUR_LIST.getMessage() + " (`"+date+"`), ", e.getGuild(), e.getMember(), true);
                    }else {
                        e.replyEmbeds(ErrorCrafter.errorEmbedCrafterWithDescription(":x: " + EDTMessages.NO_COUR_TO_DATE.getMessage())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                        Logger.getInstance().toLog(PluginName.EDT.getMessage(), EDTMessages.NO_COUR_TO_DATE.getMessage() +" (`"+date+"`)", e.getGuild(), e.getMember(), false);
                    }
                }else {
                    Cour cour = EDTDatabase.getCourById(Integer.parseInt(id));

                    if(cour != null){
                        e.replyEmbeds(EDTCrafter.craftEDTCourEmbed(cour)).queue();
                        Logger.getInstance().toLog(PluginName.EDT.getMessage(), EDTMessages.SHOW_COUR.getMessage() +" (`"+cour.getId()+"`)", e.getGuild(), e.getMember(), true);
                    }else {
                        e.replyEmbeds(ErrorCrafter.errorEmbedCrafterWithDescription(":x: " + EDTMessages.NONE_COUR_WITH_ID.getMessage())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                        Logger.getInstance().toLog(PluginName.EDT.getMessage(), EDTMessages.NONE_COUR_WITH_ID.getMessage(), e.getGuild(), e.getMember(), false);
                    }
                }

            }
        }
    }

    public void refreshEdtByGroupe(String groupe) throws IOException, ParserException {
        String url = "";
        String fileName = groupe + ".ics";

        url = ConfigurationDatabase.getConfigurationByGroupe(groupe).getUrl();

        Downloader d = new Downloader(url, new File(fileName));
        d.run();

        FileInputStream fin = new FileInputStream(fileName);
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(fin);

        List<CalendarComponent> cs = calendar.getComponents();
        EDTDatabase.deleteAllCourByGroupeId(groupe);
        for (CalendarComponent c: cs) {
            if (c instanceof VEvent) {
                String date = String.valueOf(c.getProperties().get(1));
                date = date.substring(8);
                date = date.substring(0, 8);
                String anne = date.substring(0, 4);
                String mois = date.substring(4, 6);
                String jour = date.substring(6, 8);
                date = jour + "/" + mois + "/" + anne;

                String hstart = String.valueOf(c.getProperties().get(1));
                hstart = hstart.substring(17);
                hstart = hstart.substring(0, 4);
                String hstarth = hstart.substring(0, 2);
                int hstarthint = Integer.parseInt(hstarth);
                hstarthint += 1;
                if(hstarthint < 10){
                    hstarth = "0" + String.valueOf(hstarthint);
                }else{
                    hstarth = String.valueOf(hstarthint);
                }
                String hstartm = hstart.substring(2, 4);
                hstart = hstarth + ":" + hstartm;

                String hend = String.valueOf(c.getProperties().get(2));
                hend = hend.substring(15);
                hend = hend.substring(0, 4);
                String hendh = hend.substring(0, 2);
                int hendhint = Integer.parseInt(hendh);
                hendhint += 1;
                hendh = String.valueOf(hendhint);
                String hendm = hend.substring(2, 4);
                hend = hendh + ":" + hendm;

                String location = " ";
                if(String.valueOf(c.getProperties().get(4)).length() > 2 && !String.valueOf(c.getProperties().get(4)).contains("\r\n")){
                    location = String.valueOf(c.getProperties().get(4));
                    location = location.substring(9);
                }
                if(location.contains("'"))
                    location = location.replace("'", " ");

                String description = String.valueOf(c.getProperties().get(5));
                description = description.substring(16);
                description = description.replaceAll("[\n\t ]", " ");
                description = description.replace("\\", " ");
                description = description.replace("n", "§");
                String descArgs[] = description.split("§");
                if(description.contains("'"))
                    description = description.replace("'", " ");

                String professeur = "";
                for(String sr : descArgs){
                    if(sr.equals(sr.toUpperCase()) && !sr.startsWith("ERIFIE") && !sr.contains("\n") && !sr.contains("\r")){
                        professeur += sr + " ";
                    }
                }
                if(professeur.length() < 4)
                    professeur = "AUCUN";
                for (String str : descArgs){
                    boolean allMAJ = true;
                    int i=0;
                    while(i<str.length() && allMAJ == true){
                        char ch = str.charAt(i);
                        if(Character.isLowerCase(ch)){
                            allMAJ = false;
                        }

                        i++;
                    }
                }

                int id = EDTDatabase.getMaxId()+1;

                String name = String.valueOf(c.getProperties().get(3));
                name = name.substring(8);
                name = name.substring(0, name.length()-2);
                if(name.contains("'"))
                    name = name.replace("'", " ");

                EDTDatabase.ajoutCour(new Cour(id, groupe, name, location, professeur, hstart, hend, date, " "));
            }
        }
    }
}
