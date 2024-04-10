package app.xlog.ggbond.application.job;

import app.xlog.ggbond.ChatGPT.ChatGPTapi;
import app.xlog.ggbond.zsxq.ZsxqApi;
import app.xlog.ggbond.zsxq.model.vo.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Random;

@Configuration
@ComponentScan("app.xlog.ggbond")
@EnableScheduling
public class ChatBotSchedule {
    @Autowired
    private ZsxqApi zsxqApi;

    @Autowired
    private ChatGPTapi chatGPTapi;

    @Value("${ChatBot-api.groupId}")
    private String groupId;
    @Value("${ChatBot-api.cookie}")
    private String cookie;

    // 设置定时任务
    @Scheduled(cron = "0/10 * 7-22 * * ?")
    public void RunSchedule() {
        try {
            Logger logger = LoggerFactory.getLogger(ChatBotSchedule.class);

            // 避免风控，把请求随机化
            if (new Random().nextBoolean()) {
                logger.atInfo().log("这次不回答，避免风控");
                return;
            }

            // 查询问题
            List<Topic> withoutCommentsTopics = zsxqApi.getWithoutCommentsTopics(groupId, cookie);

            if (withoutCommentsTopics.isEmpty()) {
                logger.atInfo().log("没有问题");
                return;
            }

            Topic t = withoutCommentsTopics.get(withoutCommentsTopics.size() - 1);
            // 拿到回答
            String answer = chatGPTapi.getAnswer(t.getText());
            // 回答响应
            zsxqApi.answerTopics(cookie, t, answer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
