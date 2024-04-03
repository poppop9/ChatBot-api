package app.xlog.ggbond.ChatGPT.service;

import app.xlog.ggbond.ChatGPT.ChatGPTapi;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ChatGPTapiImpl implements ChatGPTapi {
    @Value("${ChatBot-api.apiKey}")
    private String apiKey;

    @Override
    public String getAnswer(String question) throws IOException {
        final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json; charset=UTF-8");
        final OkHttpClient client = new OkHttpClient();

        String postBody = "{\n" +
                "    \"model\": \"gpt-3.5-turbo\",\n" +
                "    \"messages\": [\n" +
                "        {\n" +
                "            \"role\": \"user\",\n" +
                "            \"content\": \"" +
                question +
                "\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"temperature\": 0.7\n" +
                "}";

        Request request = new Request.Builder()
                .url("https://api.chatanywhere.com.cn/v1/chat/completions")
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody))
                .addHeader("Content-type", "application/json")
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readValue(response.body().string(), JsonNode.class);
            String text = jsonNode.at("/choices/0/message/content").asText();
            return text;
        }
    }
}
