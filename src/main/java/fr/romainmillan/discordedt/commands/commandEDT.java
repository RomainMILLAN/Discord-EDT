package fr.romainmillan.discordedt.commands;

import fr.romainmillan.discordedt.databases.ConfigurationDatabase;
import fr.romainmillan.discordedt.databases.EDTDatabase;
import fr.romainmillan.discordedt.embedCrafter.EDTCrafter;
import fr.romainmillan.discordedt.embedCrafter.EmbedCrafter;
import fr.romainmillan.discordedt.embedCrafter.ErrorCrafter;
import fr.romainmillan.discordedt.manager.Downloader;
import fr.romainmillan.discordedt.manager.EDTService;
import fr.romainmillan.discordedt.manager.Sentry;
import fr.romainmillan.discordedt.messages.EDTMessages;
import fr.romainmillan.discordedt.object.Cour;
import fr.romainmillan.discordedt.states.LogState;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class commandEDT extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if (e.getName().equals("edt")) {
            String action = null;
            if (e.getOption("action") != null)
                action = e.getOption("action").getAsString();

            String groupe = ConfigurationDatabase.getListGroupe().get(0);
            if (e.getOption("groupe") != null)
                groupe = e.getOption("groupe").getAsString();

            String id = null;
            if (e.getOption("id") != null)
                id = Objects.requireNonNull(e.getOption("id")).getAsString();

            String date = null;
            if (e.getOption("date") != null)
                date = Objects.requireNonNull(e.getOption("date")).getAsString();

            if (action != null) {
                if (action.equals("refresh")) {
                    if (groupe != null) {

                        if (date == null) {
                            DateTimeFormatter daysFormatter = DateTimeFormatter.ofPattern("dd/LL/uuuu");
                            date = daysFormatter.format(LocalDate.now());
                        }

                        try {
                            e.replyEmbeds(EmbedCrafter.embedCraftWithDescriptionAndColor(":white_check_mark: "
                                    + EDTMessages.EDT_DOING_REFRSH.getMessage() + " (`" + groupe + "`)", Color.GREEN))
                                    .setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(
                                            QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));

                            this.refreshEdtByGroupeAndDate(groupe, date);

                            e.getChannel()
                                    .sendMessageEmbeds(EmbedCrafter.embedCraftWithDescriptionAndColor(
                                            ":white_check_mark: " + EDTMessages.EDT_REFRESH.getMessage() + " (`"
                                                    + groupe + "`)",
                                            Color.GREEN))
                                    .queue((m) -> m.delete().queueAfter(
                                            QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                            Sentry.getInstance().toLog(PluginName.EDT.getMessage(),
                                    EDTMessages.EDT_REFRESH.getMessage() + " (`" + groupe + "`)", 
                                    LogState.SUCCESSFUL,
                                    e.getMember(),
                                    e.getGuild());
                        } catch (IOException | ParserException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        e.replyEmbeds(ErrorCrafter
                                .errorEmbedCrafterWithDescription(":x: " + EDTMessages.NO_GROUP_SELECT.getMessage()))
                                .setEphemeral(true).queue((m) -> m.deleteOriginal()
                                        .queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                        Sentry.getInstance().toLog(PluginName.EDT.getMessage(),
                                EDTMessages.NO_GROUP_SELECT.getMessage(), LogState.ERROR, e.getMember(), e.getGuild());
                    }
                }

                if (action.equals("edit-info")) {
                    if (e.getOption("information") != null) {
                        if (id != null) {
                            String information = e.getOption("information").getAsString();
                            Cour cour = EDTDatabase.getCourById(Integer.parseInt(id));

                            information = information.replace("'", " ");
                            cour.setInformation(information);
                            EDTDatabase.updateInformationOfCourse(cour);
                            e.replyEmbeds(EmbedCrafter.embedCraftWithDescriptionAndColor(
                                    ":white_check_mark: " + EDTMessages.INFORMATION_UPDATE.getMessage(), Color.GREEN))
                                    .setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(
                                            QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                            Sentry.getInstance().toLog(PluginName.EDT.getMessage(),
                                    EDTMessages.INFORMATION_UPDATE + " (`" + id + "` -> `" + information + "`)",
                                    LogState.SUCCESSFUL,
                                    e.getMember(),
                                    e.getGuild());
                        } else {
                            e.replyEmbeds(ErrorCrafter
                                    .errorEmbedCrafterWithDescription(":x: " + EDTMessages.NO_ID_ARGUMENT.getMessage()))
                                    .setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(
                                            QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                            Sentry.getInstance().toLog(PluginName.EDT.getMessage(),
                                    EDTMessages.NO_ID_ARGUMENT.getMessage(), 
                                    LogState.ERROR,
                                    e.getMember(),
                                    e.getGuild());
                        }
                    } else {
                        e.replyEmbeds(ErrorCrafter.errorEmbedCrafterWithDescription(
                                ":x: " + EDTMessages.NO_INFORMATION_ARGUMENT.getMessage())).setEphemeral(true)
                                .queue((m) -> m.deleteOriginal()
                                        .queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                        Sentry.getInstance().toLog(PluginName.EDT.getMessage(),
                                EDTMessages.NO_INFORMATION_ARGUMENT.getMessage(), 
                                LogState.ERROR,
                                e.getMember(),
                                e.getGuild());
                    }
                }
            } else {
                // SEE EDT

                if (id == null) {
                    if (date == null) {
                        // EDT par semaine
                        // Date du jour
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        date = String.valueOf(dtf.format(LocalDateTime.now()));
                        
                        HashMap<String, String> listeSemaineCour = EDTService.getListeCourSemaineDateStartAndGroupe(date, groupe);

                        e.replyEmbeds(EDTCrafter.craftEDTSemaine(date, groupe, listeSemaineCour)).queue();
                        Sentry.getInstance().toLog(PluginName.EDT.getMessage(), "Affichage de l'emploi du temp de la semaine (`"+date+"`)", LogState.SUCCESSFUL, e.getMember(), e.getGuild());
                    } else {
                        // Liste des cours
                        ArrayList<Cour> listCour = EDTDatabase.getListCourByDateAndGroupe(date, groupe);

                        if (listCour.size() != 0) {
                            e.replyEmbeds(EDTCrafter.craftEDTListCour(listCour)).queue();
                            Sentry.getInstance().toLog(PluginName.EDT.getMessage(),
                                    EDTMessages.SHOW_COUR_LIST.getMessage() + " (`" + date + "`), ", 
                                    LogState.SUCCESSFUL,
                                    e.getMember(),
                                    e.getGuild());
                        } else {
                            e.replyEmbeds(ErrorCrafter.errorEmbedCrafterWithDescription(
                                    ":x: " + EDTMessages.NO_COUR_TO_DATE.getMessage())).setEphemeral(true)
                                    .queue((m) -> m.deleteOriginal().queueAfter(
                                            QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                            Sentry.getInstance().toLog(PluginName.EDT.getMessage(),
                                    EDTMessages.NO_COUR_TO_DATE.getMessage() + " (`" + date + "`)", 
                                    LogState.ERROR,
                                    e.getMember(),
                                    e.getGuild());
                        }
                    }
                } else {
                    Cour cour = EDTDatabase.getCourById(Integer.parseInt(id));

                    if (cour != null) {
                        e.replyEmbeds(EDTCrafter.craftEDTCourEmbed(cour)).queue();
                        Sentry.getInstance().toLog(PluginName.EDT.getMessage(),
                                EDTMessages.SHOW_COUR.getMessage() + " (`" + cour.getId() + "`)", LogState.SUCCESSFUL, e.getMember(), e.getGuild());
                    } else {
                        e.replyEmbeds(ErrorCrafter
                                .errorEmbedCrafterWithDescription(":x: " + EDTMessages.NONE_COUR_WITH_ID.getMessage()))
                                .setEphemeral(true).queue((m) -> m.deleteOriginal()
                                        .queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                        Sentry.getInstance().toLog(PluginName.EDT.getMessage(),
                                EDTMessages.NONE_COUR_WITH_ID.getMessage(), LogState.ERROR, e.getMember(), e.getGuild());
                    }
                }

            }
        }
    }

    public void refreshEdtByGroupeAndDate(String groupe, String dateToRefresh) throws IOException, ParserException {
        String url = "";
        String fileName = groupe + ".ics";

        url = ConfigurationDatabase.getConfigurationByGroupe(groupe).getUrl();

        Downloader d = new Downloader(url, new File(fileName));
        d.run();

        FileInputStream fin = new FileInputStream(fileName);
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(fin);

        List<CalendarComponent> cs = calendar.getComponents();

        int hourIntDecalage = 2;
        String[] dateToRefreshSplit = dateToRefresh.split("/");
        int dayToRefresh = Integer.parseInt(dateToRefreshSplit[0]);
        int monthToRefresh = Integer.parseInt(dateToRefreshSplit[1]);
        int yearToRefresh = Integer.parseInt(dateToRefreshSplit[2]);

        EDTDatabase.deleteAllCourAfterDateByGroupe(dateToRefresh, groupe);

        for (CalendarComponent c : cs) {
            if (c instanceof VEvent) {
                // DATE
                String date = String.valueOf(c.getProperties().get(1));
                date = date.substring(8);
                date = date.substring(0, 8);
                String anne = date.substring(0, 4);
                String mois = date.substring(4, 6);
                String jour = date.substring(6, 8);
                date = jour + "/" + mois + "/" + anne;
                int year = Integer.parseInt(anne);
                int month = Integer.parseInt(mois);
                int day = Integer.parseInt(jour);

                if ((year > yearToRefresh) || (year == yearToRefresh && month == monthToRefresh && day >= dayToRefresh)
                        || (year == yearToRefresh && month > monthToRefresh)) {
                    // HEURES
                    // Heure de début
                    String hstart = String.valueOf(c.getProperties().get(1));
                    hstart = hstart.substring(17);
                    hstart = hstart.substring(0, 4);
                    String hstarth = hstart.substring(0, 2);
                    int hstarthint = Integer.parseInt(hstarth);
                    hstarthint += hourIntDecalage;
                    if (hstarthint < 10) {
                        hstarth = "0" + String.valueOf(hstarthint);
                    } else {
                        hstarth = String.valueOf(hstarthint);
                    }
                    String hstartm = hstart.substring(2, 4);
                    hstart = hstarth + ":" + hstartm;
                    // Heure de fin
                    String hend = String.valueOf(c.getProperties().get(2));
                    hend = hend.substring(15);
                    hend = hend.substring(0, 4);
                    String hendh = hend.substring(0, 2);
                    int hendhint = Integer.parseInt(hendh);
                    hendhint += hourIntDecalage;
                    hendh = String.valueOf(hendhint);
                    String hendm = hend.substring(2, 4);
                    hend = hendh + ":" + hendm;

                    // SAELLE
                    String location = " ";
                    if (String.valueOf(c.getProperties().get(4)).length() > 2
                            && !String.valueOf(c.getProperties().get(4)).contains("\r\n")) {
                        location = String.valueOf(c.getProperties().get(4));
                        location = location.substring(9);
                    }
                    if (location.contains("'"))
                        location = location.replace("'", " ");

                    // DESCRIPTION
                    String description = String.valueOf(c.getProperties().get(5));
                    description = description.substring(16);
                    description = description.replaceAll("[\n\t ]", " ");
                    description = description.replace("\\", " ");
                    description = description.replace("n", "§");
                    String descArgs[] = description.split("§");
                    if (description.contains("'"))
                        description = description.replace("'", " ");

                    // PROFESSEUR
                    String professeur = "";
                    for (String sr : descArgs) {
                        if (sr.equals(sr.toUpperCase()) && !sr.startsWith("ERIFIE") && !sr.contains("\n")
                                && !sr.contains("\r")) {
                            professeur += sr + " ";
                        }
                    }
                    if (professeur.length() < 4)
                        professeur = "AUCUN";
                    for (String str : descArgs) {
                        boolean allMAJ = true;
                        int i = 0;
                        while (i < str.length() && allMAJ == true) {
                            char ch = str.charAt(i);
                            if (Character.isLowerCase(ch)) {
                                allMAJ = false;
                            }

                            i++;
                        }
                    }

                    // ID
                    int id = EDTDatabase.getMaxId() + 1;

                    // NOM
                    String name = String.valueOf(c.getProperties().get(3));
                    name = name.substring(8);
                    name = name.substring(0, name.length() - 2);
                    if (name.contains("'"))
                        name = name.replace("'", " ");

                    // AJOUT
                    EDTDatabase.ajoutCour(new Cour(id, groupe, name, location, professeur, hstart, hend, date, " "));
                }
            }
        }
    }
}
