package app.xlog.ggbond;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
    public static void main(String[] args) throws IOException {
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.zsxq.com/v2/groups/15555541222422/topics?scope=all&count=20")
                .addHeader("cookie", "zsxq_access_token=DFECBBDC-5EA5-103F-B7BB-E894CF8EEDFE_F230941593CDF2AE; abtest_env=product; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22812554158425482%22%2C%22first_id%22%3A%2218e505d7f5710c-0fa58a2610ff17-26001b51-1327104-18e505d7f5811bd%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMThlNTA1ZDdmNTcxMGMtMGZhNThhMjYxMGZmMTctMjYwMDFiNTEtMTMyNzEwNC0xOGU1MDVkN2Y1ODExYmQiLCIkaWRlbnRpdHlfbG9naW5faWQiOiI4MTI1NTQxNTg0MjU0ODIifQ%3D%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22812554158425482%22%7D%2C%22%24device_id%22%3A%2218e505d7f5710c-0fa58a2610ff17-26001b51-1327104-18e505d7f5811bd%22%7D; zsxqsessionid=e1934fa04e413d06f0921d8ffd7f5148")
                .addHeader("Content-type", "application/json; charset=UTF-8")
                .build();

        try (Response response = client.newCall(request).execute()) {
            // 如果响应不成功，则抛出异常
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            ObjectMapper objectMapper = new ObjectMapper();

            String Json = response.body().string();

            try {
                JsonNode jsonNode = objectMapper.readValue(Json, JsonNode.class);
                JsonNode succeeded = jsonNode.get("succeeded");
                System.out.println(succeeded.asText());

                JsonNode topicId = jsonNode.at("/resp_data/topics/0/create_time");
                System.out.println(topicId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
