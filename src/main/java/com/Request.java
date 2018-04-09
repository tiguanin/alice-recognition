package com;


import com.constants.Languages;
import com.constants.SpeechKitURL;
import com.constants.Topics;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;


// TODO: нужно формировать отдельную папку, в которой будут лежать парами - аудиофайл и распознанный XML

// TODO: Адапитировать код под разные форматы файлов - mp3, ogg, wav

public class Request {


    public static void execute(String audioType, String audioPath) {

        try {
            // формирование запроса
            SpeechKitURL createdUrl = new SpeechKitURL(Topics.QUERIES, Languages.RUSSIAN);
            URL yandexUrl = new URL(createdUrl.getUrl());
            HttpURLConnection con = (HttpURLConnection) yandexUrl.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Host", "asr.yandex.net");
            con.setRequestProperty("Content-Type", audioType);
            con.setDoOutput(true);

            System.out.println(createdUrl.getUrl());

            // представление файла в виде массива байтов
            File voice = new File(audioPath);
            //System.out.println(voice.getTotalSpace());
            byte[] bytes = Files.readAllBytes(voice.toPath());
            System.out.println(voice.toPath());

            // отправка массива байтов в открытое соедниение
            DataOutputStream request = new DataOutputStream(con.getOutputStream());
            request.write(bytes);
            request.flush();
            request.close();

            // обработка ответа из SpeechKit API
            InputStream in = new BufferedInputStream(con.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            line = sb.toString();
            System.out.println(line);

            br.close();
            con.disconnect();

            // обработка ответа и извлечение данных из XML
            XmlProcessing.convertStringToXml(line);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
