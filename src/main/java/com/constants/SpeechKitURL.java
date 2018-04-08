package com.constants;

import java.util.UUID;

/**
 * Формирование URL для отравки на SpeechKit.
 */
public class SpeechKitURL {
    private String url; // URL запроса к Yandex.SpeechKit-API
    private String uuid;// UUID пользователя
    private String topic; // Языковая модель
    private String language;// Язык говорящего

    public SpeechKitURL(String topic, String language) {
        uuid = UUID.randomUUID().toString().replace("-", "");
        this.url = "https://asr.yandex.net/asr_xml?uuid=" + uuid + "&key=" + SpeechKitAPIKey.KEY + "&topic=" + topic + "&lang=" + language + "";

    }

    /**
     * Готовый URL
     */
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
