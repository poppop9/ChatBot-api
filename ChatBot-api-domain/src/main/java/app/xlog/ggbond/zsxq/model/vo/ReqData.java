package app.xlog.ggbond.zsxq.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ReqData {

    private String text;
    @JsonProperty("image_ids")
    private List imageIds;
    @JsonProperty("mentioned_user_ids")
    private List mentionedUserIds;
    public void setText(String text) {
         this.text = text;
     }
     public String getText() {
         return text;
     }

    public void setImageIds(List imageIds) {
         this.imageIds = imageIds;
     }
     public List getImageIds() {
         return imageIds;
     }

    public void setMentionedUserIds(List mentionedUserIds) {
         this.mentionedUserIds = mentionedUserIds;
     }
     public List getMentionedUserIds() {
         return mentionedUserIds;
     }

}