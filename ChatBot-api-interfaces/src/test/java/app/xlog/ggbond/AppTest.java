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
import org.springframework.http.MediaType;
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
    public void Test() throws IOException {
        List<Topic> withoutCommentsTopics = zsxqApi.getWithoutCommentsTopics(groupId, cookie);
        for (Topic topic : withoutCommentsTopics) {
            System.out.println(topic.getTopicId() + " " + topic.getText() + " " + topic.getCommentsCount());
        }
    }

    @Test
    public void TestAnswer() throws IOException {
        zsxqApi.answerTopics(cookie, zsxqApi.getWithoutCommentsTopics(groupId, cookie));
    }

    @Autowired
    private ChatGPTapi chatGPTapi;

    @Test
    public void TestChatGPT() throws IOException {
        chatGPTapi.getAnswer("列举一下 JavaWeb 最新技术");
    }
}
