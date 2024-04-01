package app.xlog.ggbond.ChatGPT;

import java.io.IOException;

public interface ChatGPTapi {
    void getAnswer(String question) throws IOException;
}
