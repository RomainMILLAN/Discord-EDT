package fr.romainmillan.discordedt.manager;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import fr.romainmillan.discordedt.App;
import fr.romainmillan.discordedt.states.ConsoleState;
public class SignalApi {

    public static void sendSignalPersonnalMessage(String message) {
        if(App.getConfiguration("SIGNAL_API_URL").equals("")) {
            return;
        }

        if(App.getConfiguration("SIGNAL_NUMBER").equals("") && App.getConfiguration("SIGNAL_GROUP_ID").equals("")){
            return;
        }

        StringBuilder body = new StringBuilder();
        if(!App.getConfiguration("SIGNAL_NUMBER").equals("") && !App.getConfiguration("SIGNAL_GROUP_ID").equals("")){
            body
                    .append("{\"message\": \"")
                    .append(message)
                    .append("\",\"number\": \"")
                    .append(App.getConfiguration("SIGNAL_NUMBER"))
                    .append("\",\"recipients\": [\"")
                    .append(App.getConfiguration("SIGNAL_GROUP_ID"))
                    .append("\"]}");

        }else {
            body
                    .append("{\"message\": \"")
                    .append(message)
                    .append("\",\"number\": \"")
                    .append(App.getConfiguration("SIGNAL_NUMBER"))
                    .append("\",\"recipients\": [\"")
                    .append(App.getConfiguration("SIGNAL_NUMBER"))
                    .append("\"]}");
        }

        ConsoleManager.getInstance().toConsole(
                body.toString(),
                ConsoleState.DEBUG
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(App.getConfiguration("SIGNAL_API_URL").toString()))
                .method("POST", HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();

        try {
            HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
