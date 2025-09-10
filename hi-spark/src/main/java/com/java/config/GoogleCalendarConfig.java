package com.java.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.ServiceAccountCredentials;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.util.Collections;

@Component
public class GoogleCalendarConfig {

    private static final String APPLICATION_NAME = "hi-spark";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    public Calendar getCalendarService() throws Exception {
        var httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        var credentials = ServiceAccountCredentials.fromStream(
                new FileInputStream("src/main/resources/service-account.json")
        ).createScoped(Collections.singletonList("https://www.googleapis.com/auth/calendar"));

        return new Calendar.Builder(httpTransport, JSON_FACTORY, new HttpCredentialsAdapter(credentials))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
