package fr.romainmillan.discordedt.manager;

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
        OptionData edtGroupe = new OptionData(OptionType.STRING, "groupe", EDTMessages.DESCRIPTION_EDT_GROUPE_ARGUMENT.getMessage()).setRequired(false).addChoice("BUT INFO - A1", "infos6").addChoice("BUT INFO - Q5", "infoq5");
        OptionData edtId = new OptionData(OptionType.STRING, "id", EDTMessages.DESCRIPTION_EDT_ID_ARGUMENT.getMessage()).setRequired(false);
        OptionData edtDate = new OptionData(OptionType.STRING, "date", EDTMessages.DESCRIPTION_EDT_DATE_ARGUMENT.getMessage()).setRequired(false);
        OptionData edtInfo = new OptionData(OptionType.STRING, "information", EDTMessages.DESCRIPTION_EDT_INFO_ARGUMENT.getMessage()).setRequired(false);
        commandData.add(Commands.slash("edt", EDTMessages.DESCRIPTION_EDT_COMMAND.getMessage()).addOptions(edtInfo).addOptions(edtAction).addOptions(edtId).addOptions(edtGroupe).addOptions(edtDate));

        return commandData;
    }

}
