package app.xlog.ggbond.zsxq.service;

import app.xlog.ggbond.zsxq.ZsxqApi;
import app.xlog.ggbond.zsxq.model.vo.Topic;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            List<Topic> topics = new ArrayList<Topic>();

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

                // 进行话题集合的筛选(如果话题有评论，则删除该元素)
                topics = topics.stream()
                        .filter(t -> t.getCommentsCount() == 0)
                        .collect(Collectors.toList());

                return topics;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public void answerTopics(String cookie, Topic t, String answer) throws IOException {
        final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json; charset=UTF-8");
        final OkHttpClient client = new OkHttpClient();

        answer = answer.replace("\n", "\\n");

        String reqData = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"" +
                answer +
                "\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"mentioned_user_ids\": []\n" +
                "  }\n" +
                "}";

        // https://api.zsxq.com/v2/topics/2855848858441111/comments
        Request request = new Request.Builder()
                .url("https://api.zsxq.com/v2/topics/" + t.getTopicId() + "/comments")
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, reqData))
                .addHeader("cookie", cookie)
                .addHeader("Content-type", "application/json; charset=UTF-8")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            System.out.println(response.body().string());
        }

    }
}
