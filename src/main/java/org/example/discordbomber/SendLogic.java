package org.example.discordbomber;

import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class SendLogic {


    public void choseFunction(String userMessage, String filePath , String myId, String channelId, boolean whatFunction, int setTime){
        HelloController.timer = new Timer();
        int totalTime = setTime*60000;
        System.out.println(whatFunction);
        if (whatFunction){
           HelloController.timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    System.out.println(totalTime);
                    clientSendImage(userMessage,filePath,myId,channelId);
                }
            }, 0, totalTime);
        }
        if (!whatFunction){
           HelloController.timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    System.out.println(totalTime);
                    clientSend(userMessage,myId,channelId);
                }
            }, 0, totalTime);
        }
    }


    public void clientSend(String userMessage, String myId, String channelId) {
            try {
                URL apiURL = new URL("https://discord.com/api/v10/channels/" + channelId + "/messages");
                HttpURLConnection urlConnection = (HttpURLConnection) apiURL.openConnection();

                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Authorization", myId);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setDoOutput(true);

                Gson gson = new Gson();
                Map<String, Object> messageMap = new HashMap<>();
                messageMap.put("content", userMessage);
                String jsonMessage = gson.toJson(messageMap);


                try {
                    OutputStream trying = urlConnection.getOutputStream();
                    trying.write(jsonMessage.getBytes());
                } finally {}

                int debugCode = urlConnection.getResponseCode();
                System.out.println(debugCode);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


    }

    public void clientSendImage(String userMessage, String filePath , String myId, String channelId){
        try {
            String boundary = "----JavaDiscordBoundary" + System.currentTimeMillis();
            URL apiURL = new URL("https://discord.com/api/v10/channels/" + channelId + "/messages");
            HttpURLConnection urlConnection = (HttpURLConnection) apiURL.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Authorization", myId);
            urlConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            urlConnection.setDoOutput(true);

            File file = new File(filePath);
            String fileName = file.getName();
            System.out.println(fileName);

            try(
                OutputStream trying = urlConnection.getOutputStream();
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(trying, StandardCharsets.UTF_8), true);
            ){
                Gson gson = new Gson();
                Map<String, Object> messageMap = new HashMap<>();
                messageMap.put("content", userMessage);

                // тут повідомлення
                writer.append("--").append(boundary).append("\r\n");
                writer.append("Content-Disposition: form-data; name=\"payload_json\"\r\n\r\n");
                writer.append(gson.toJson(messageMap)).append("\r\n");

                // тут передається файл
                writer.append("--").append(boundary).append("\r\n");
                writer.append("Content-Disposition: form-data; name=\"files[0]\"; filename=\"")
                        .append(fileName).append("\"\r\n");
                writer.append("Content-Type: image/png\r\n\r\n");
                writer.flush();

                Files.copy(file.toPath(), trying);
                trying.flush();


                writer.append("\r\n");
                writer.append("--").append(boundary).append("--").append("\r\n");
                writer.flush();


            }




            int debugCode = urlConnection.getResponseCode();
            System.out.println(debugCode);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
