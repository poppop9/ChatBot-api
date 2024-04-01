package app.xlog.ggbond.zsxq.service;

import app.xlog.ggbond.zsxq.ZsxqApi;
import app.xlog.ggbond.zsxq.model.vo.Topic;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ZsxqApiImpl implements ZsxqApi {
    @Override
    public List<Topic> getWithoutCommentsTopics(String groupId, String cookie) throws IOException {
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.zsxq.com/v2/groups/" + groupId + "/topics?scope=all&count=20")
                .addHeader("cookie", cookie)
                .addHeader("Content-type", "application/json; charset=UTF-8")
                .build();

        try (Response response = client.newCall(request).execute()) {
            // 如果响应不成功，则抛出异常
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // 使用 Jackson 反序列化
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            // 获取响应体
            String Json = response.body().string();

            // 创建话题集合
            ArrayList<Topic> topics = new ArrayList<Topic>();

            try {
                JsonNode jsonNode = objectMapper.readValue(Json, JsonNode.class);
                JsonNode topicsNode = jsonNode.at("/resp_data/topics");

                for (JsonNode topicNode : topicsNode) {
                    String topicId = topicNode.get("topic_id").asText();
                    String text = topicNode.at("/talk/text").asText();
                    int commentsCount = topicNode.get("comments_count").asInt();

                    Topic topic = new Topic(topicId, text, commentsCount);
                    topics.add(topic);
                }

                return topics;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
