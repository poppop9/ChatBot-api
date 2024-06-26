package app.xlog.ggbond.zsxq;

import app.xlog.ggbond.zsxq.model.vo.Topic;

import java.io.IOException;
import java.util.List;

// 知识星球的 api

public interface ZsxqApi {
    // 查询没有评论的话题，返回数据
    List<Topic> getWithoutCommentsTopics(String groupId, String cookie) throws IOException;

    // 回答没有评论的话题
    void answerTopics(String cookie, Topic t, String answer) throws IOException;

}
