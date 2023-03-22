package fr.romainmillan.discordedt.manager;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader {
    private String link;
    private File out;

    public Downloader(String l, File out){
        this.link = l;
        this.out = out;
    }

    public void run(){
        try{
            URL url = new URL(this.link);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            //double fileSize = http.getContentLengthLong();
            BufferedInputStream in = new BufferedInputStream(http.getInputStream());
            FileOutputStream fos = new FileOutputStream(this.out);
            BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
            byte[] buffer = new byte[1024];
            //double downloaded = 0.00;
            int read = 0;
            //double percentDownloaded = 0.00;
            while((read = in.read(buffer, 0, 1024)) >= 0){
                bout.write(buffer, 0, read);
                //downloaded += read;
                //percentDownloaded = (downloaded*100)/fileSize;
                //String percent = String.format("%.4f", percentDownloaded);
            }
            bout.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
