package app.xlog.ggbond.zsxq.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Topic {

    @JsonProperty("topic_id")
    private String topicId;
    private String text;

    @JsonProperty("comments_count")
    private int commentsCount;

    public Topic(String topicId, String text, int commentsCount) {
        this.topicId = topicId;
        this.text = text;
        this.commentsCount = commentsCount;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }
}