package com.gooproper.util;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendMessageToFCM {
    private static final String FCM_API = "https://fcm.googleapis.com/fcm/send";
    private static final String SERVER_KEY = "AAAAy4Cyncs:APA91bHOuS-ePeZgVXlaORWsC_5h9H1Z_G4cg2zJVyvxePrp290kkDNrXxhfp0WLdVOmaSwzaGwxLpxFNwVeAVvcupqqOF8FRBstbsK2SLNEuZbTG53Uig5B1FUTATbBeQ6bmmtdwYz0";
    private static final MediaType JSON = MediaType.parse("application/json");

    public static String sendMessage(String token, String title, String message, String notificationType) {
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, createMessageBody(token, title, message, notificationType));
            Request request = new Request.Builder()
                    .url(FCM_API)
                    .post(body)
                    .addHeader("Authorization", "key=" + SERVER_KEY)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private static String createMessageBody(String token, String title, String message, String notificationType) {
        return "{\"to\":\"" + token + "\"," +
                "\"data\":{\"title\":\"" + title + "\",\"message\":\"" + message + "\",\"type\":\"" + notificationType + "\"}}";
    }
}
