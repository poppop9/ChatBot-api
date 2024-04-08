package app.xlog.ggbond;

import app.xlog.ggbond.ChatGPT.ChatGPTapi;
import app.xlog.ggbond.zsxq.ZsxqApi;
import app.xlog.ggbond.zsxq.model.vo.Topic;
import okhttp3.*;
import org.springframework.http.MediaType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

/**
 * Unit test for simple App.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {
    @Value("${ChatBot-api.groupId}")
    private String groupId;

    @Value("${ChatBot-api.cookie}")
    private String cookie;

    @Autowired
    private ZsxqApi zsxqApi;

    @Test
    public void TestGetWithoutCommentsTopics() throws IOException {
        List<Topic> withoutCommentsTopics = zsxqApi.getWithoutCommentsTopics(groupId, cookie);
        for (Topic topic : withoutCommentsTopics) {
            System.out.println(topic.getTopicId() + " " + topic.getText() + " " + topic.getCommentsCount());
        }
    }

    @Autowired
    private ChatGPTapi chatGPTapi;

    @Value("${ChatBot-api.apiKey}")
    private String apiKey;

    @Test
    public void TestAnswer() throws IOException {
        zsxqApi.answerTopics(cookie, zsxqApi.getWithoutCommentsTopics(groupId, cookie).get(0), "ddd");
    }

    @Test
    public void TestChatGPT() throws IOException {
        String answer = chatGPTapi.getAnswer("介绍一下嘉兴大学");
        System.out.println(answer);
    }
}
