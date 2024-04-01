package app.xlog.ggbond.zsxq;

import app.xlog.ggbond.zsxq.model.vo.Topic;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

// 知识星球的 api
@Service
public interface ZsxqApi {
    // 通过这个方法，查询没有评论的话题，返回数据
    List<Topic> getWithoutCommentsTopics(String groupId, String cookie) throws IOException;


}
