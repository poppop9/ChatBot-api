package app.xlog.ggbond.ChatGPT;

import java.io.IOException;

public interface ChatGPTapi {
    String getAnswer(String apiKey,String question) throws IOException;
}
