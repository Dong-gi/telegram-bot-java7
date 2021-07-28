package link4.joy.telegram.bot;

import com.fasterxml.jackson.databind.ObjectMapper;

import link4.joy.telegram.bot.req.*;
import link4.joy.telegram.bot.res.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class TelegramBot {
    private final String token;
    private final ThreadLocal<ObjectMapper> mapperHolder = new ThreadLocal<ObjectMapper>() {
        @Override
        protected ObjectMapper initialValue() {
            return new ObjectMapper();
        }
    };

    public TelegramBot(String token) {
        this.token = token;
    }

    private String getRequestUrl(String method) {
        return "https://api.telegram.org/bot" + token + '/' + method;
    }

    private <T> T doGet(String method, Map<String, Object> query, Class<T> returnType) throws IOException {
        String url = getRequestUrl(method);
        if (query != null && query.isEmpty() == false) {
            StringBuilder builder = new StringBuilder(url);
            builder.append('?');
            boolean isFirst = true;
            for (Entry<String, Object> entry : query.entrySet()) {
                builder.append(entry.getKey()).append('=').append(entry.getValue());
                if (isFirst)
                    isFirst = false;
                else
                    builder.append('&');
            }
            url = builder.toString();
        }

        URLConnection connection = new URL(url).openConnection();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                builder.append(line);

            String result = builder.toString();
            Logger.getGlobal().log(Level.INFO, method + " >>> " + result);
            return mapperHolder.get().readValue(result, returnType);
        }
    }

    private <T> T doPostJson(String method, String json, Class<T> returnType) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(getRequestUrl(method)).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        connection.connect();

        try (PrintWriter writer = new PrintWriter(connection.getOutputStream())) {
            writer.append(json);
            writer.flush();
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                builder.append(line);

            String result = builder.toString();
            Logger.getGlobal().log(Level.INFO, method + " >>> " + result);
            return mapperHolder.get().readValue(result, returnType);
        }
    }

    private <T> T doPostForm(String method, Map<String, Object> fields, String fileFieldName, File file,
            Class<T> returnType) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(getRequestUrl(method)).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type",
                "multipart/form-data; boundary=----WebKitFormBoundaryT483rRseexWNKfiN");
        connection.setDoOutput(true);
        connection.connect();

        try (OutputStream out = connection.getOutputStream()) {
            out.write("------WebKitFormBoundaryT483rRseexWNKfiN\r\n".getBytes());
            if (fields != null && fields.isEmpty() == false) {
                for (Entry<String, Object> entry : fields.entrySet()) {
                    out.write("Content-Disposition: form-data; name=\"".getBytes());
                    out.write(entry.getKey().getBytes());
                    out.write("\"\r\n\r\n".getBytes());
                    if (CharSequence.class.isAssignableFrom(entry.getValue().getClass()))
                        out.write(((CharSequence) entry.getValue()).toString().getBytes());
                    else if (entry.getValue().getClass().isEnum())
                        out.write(((Enum<?>) entry.getValue()).name().getBytes());
                    else
                        out.write(mapperHolder.get().writeValueAsBytes(entry.getValue()));
                    out.write("\r\n------WebKitFormBoundaryT483rRseexWNKfiN\r\n".getBytes());
                }
            }
            out.write("Content-Disposition: form-data; name=\"".getBytes());
            out.write(fileFieldName.getBytes());
            out.write("\"; filename=\"".getBytes());
            out.write(file.getName().getBytes());
            out.write("\"\r\n\r\n".getBytes());
            try (FileInputStream fin = new FileInputStream(file)) {
                int size = fin.available();
                while (size > 0) {
                    byte[] tmp = new byte[size];
                    fin.read(tmp);
                    out.write(tmp);
                    size = fin.available();
                }
            }
            out.write("\r\n------WebKitFormBoundaryT483rRseexWNKfiN--\r\n".getBytes());
            out.flush();
        }
        Logger.getGlobal().log(Level.INFO, method + " >>> SENT");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                builder.append(line);

            String result = builder.toString();
            Logger.getGlobal().log(Level.INFO, method + " >>> " + result);
            return mapperHolder.get().readValue(result, returnType);
        }
    }

    public GetUpdatesResponse getUpdates() throws IOException {
        return doGet("getUpdates", null, GetUpdatesResponse.class);
    }

    public GetUpdatesResponse getUpdates(int offset) throws IOException {
        Map<String, Object> query = new HashMap<>();
        query.put("offset", offset);
        return doGet("getUpdates", query, GetUpdatesResponse.class);
    }

    public SendMessageResponse sendMessage(SendMessageRequest req) throws IOException {
        return doPostJson("sendMessage", mapperHolder.get().writeValueAsString(req), SendMessageResponse.class);
    }

    public void sendPhoto(SendPhotoRequest req) throws IOException {
        Map<String, Object> fields = new HashMap<String, Object>();
        fields.put("chat_id", req.chat_id);
        if (req.caption != null)
            fields.put("caption", req.caption);
        if (req.parse_mode != null)
            fields.put("parse_mode", req.parse_mode);
        if (req.replay_to_message_id != null)
            fields.put("replay_to_message_id", req.replay_to_message_id);
        if (req.allow_sending_without_reply != null)
            fields.put("allow_sending_without_reply", req.allow_sending_without_reply);
        if (req.reply_markup != null)
            fields.put("reply_markup", req.reply_markup);
        doPostForm("sendPhoto", fields, "photo", req.photo, BaseResponse.class);
    }
}
