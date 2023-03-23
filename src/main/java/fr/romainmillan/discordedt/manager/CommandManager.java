package fr.romainmillan.discordedt.manager;

import fr.romainmillan.discordedt.databases.ConfigurationDatabase;
import fr.romainmillan.discordedt.messages.*;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    public static List<CommandData> updateSlashCommands(){
        List<CommandData> commandData = new ArrayList<>();

        OptionData edtAction = new OptionData(OptionType.STRING, "action", EDTMessages.DESCRIPTION_EDT_ACTION_ARGUMENT.getMessage()).setRequired(false).addChoice("Réediter l'emploi du temp", "refresh").addChoice("Edition de l'information d'un cour", "edit-info");
        OptionData edtGroupe = new OptionData(OptionType.STRING, "groupe", EDTMessages.DESCRIPTION_EDT_GROUPE_ARGUMENT.getMessage()).setRequired(false).addChoices(ConfigurationDatabase.getAllGroupeChoices());
        OptionData edtId = new OptionData(OptionType.STRING, "id", EDTMessages.DESCRIPTION_EDT_ID_ARGUMENT.getMessage()).setRequired(false);
        OptionData edtDate = new OptionData(OptionType.STRING, "date", EDTMessages.DESCRIPTION_EDT_DATE_ARGUMENT.getMessage()).setRequired(false);
        OptionData edtInfo = new OptionData(OptionType.STRING, "information", EDTMessages.DESCRIPTION_EDT_INFO_ARGUMENT.getMessage()).setRequired(false);
        commandData.add(Commands.slash("edt", EDTMessages.DESCRIPTION_EDT_COMMAND.getMessage()).addOptions(edtInfo).addOptions(edtAction).addOptions(edtId).addOptions(edtGroupe).addOptions(edtDate));

        OptionData edtGroupeRequired = new OptionData(OptionType.STRING, "groupe", EDTMessages.DESCRIPTION_EDT_GROUPE_ARGUMENT.getMessage()).setRequired(true).addChoices(ConfigurationDatabase.getAllGroupeChoices());
        commandData.add(Commands.slash("notification", EDTMessages.DESCRIPTION_NOTIFICATION_COMMAND.getMessage()).addOptions(edtGroupeRequired));

        commandData.add(Commands.slash("next", EDTMessages.DESCRIPTION_NEXT_COMMAND.getMessage()).addOptions(edtGroupeRequired));

        OptionData groupeConf = new OptionData(OptionType.STRING, "groupe", ConfigurationMessages.DESCRIPTION_ARG_GROUPE.getMessages()).setRequired(true);
        commandData.add(Commands.slash("setup", ConfigurationMessages.DESCRIPTION_SETUP_COMMAND.getMessages()).addOptions(groupeConf));

        OptionData confAction = new OptionData(OptionType.STRING, "action", ConfigurationMessages.DESCRIPTION_ARG_ACTION.getMessages()).setRequired(true).addChoice("Nickname", "nick").addChoice("URL du groupe", "url").addChoice("ID du role", "idRole").addChoice("ID du role de notification", "idNotif").addChoice("ID du channel", "idChannel");
        OptionData confGroupe = new OptionData(OptionType.STRING, "groupe", ConfigurationMessages.DESCRIPTION_ARG_GRP.getMessages()).setRequired(true).addChoices(ConfigurationDatabase.getAllGroupeChoices());
        OptionData confText = new OptionData(OptionType.STRING, "texte", "texte pour l'action").setRequired(true);
        commandData.add(Commands.slash("configuration", ConfigurationMessages.DESCRIPTION_COMMAND.getMessages()).addOptions(confAction).addOptions(confGroupe).addOptions().addOptions(confText));


        return commandData;
    }

}
