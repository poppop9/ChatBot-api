package app.xlog.ggbond.chatbotapiapplication;

import okhttp3.*;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@SpringBootTest
class ChatBotApiApplicationTests {
    // 查看问题
    private final OkHttpClient client = new OkHttpClient();

    @Test
    public void QueryUnanswered() throws IOException {
        Request request = new Request.Builder()
                .url("https://api.zsxq.com/v2/groups/15555541222422/topics?scope=all&count=20")
                .addHeader("cookie", "sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%2218e505d7f5710c-0fa58a2610ff17-26001b51-1327104-18e505d7f5811bd%22%2C%22first_id%22%3A%22%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMThlNTA1ZDdmNTcxMGMtMGZhNThhMjYxMGZmMTctMjYwMDFiNTEtMTMyNzEwNC0xOGU1MDVkN2Y1ODExYmQifQ%3D%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%22%2C%22value%22%3A%22%22%7D%2C%22%24device_id%22%3A%2218e505d7f5710c-0fa58a2610ff17-26001b51-1327104-18e505d7f5811bd%22%7D; zsxq_access_token=DFECBBDC-5EA5-103F-B7BB-E894CF8EEDFE_F230941593CDF2AE; abtest_env=product; zsxqsessionid=3b0c87cab6ed974d21fdd5913765435a")
                .addHeader("Content-type", "application/json; charset=UTF-8")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            System.out.println(response.body().string());
        }
    }

    // 回答问题
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json; charset=UTF-8");

    @Test
    public void Answered() throws IOException {
        String postBody = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"你自己试试\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"mentioned_user_ids\": []\n" +
                "  }\n" +
                "}";

        Request request = new Request.Builder()
                .url("https://api.zsxq.com/v2/topics/2855848858441111/comments")
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody))
                .addHeader("cookie", "sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%2218e505d7f5710c-0fa58a2610ff17-26001b51-1327104-18e505d7f5811bd%22%2C%22first_id%22%3A%22%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMThlNTA1ZDdmNTcxMGMtMGZhNThhMjYxMGZmMTctMjYwMDFiNTEtMTMyNzEwNC0xOGU1MDVkN2Y1ODExYmQifQ%3D%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%22%2C%22value%22%3A%22%22%7D%2C%22%24device_id%22%3A%2218e505d7f5710c-0fa58a2610ff17-26001b51-1327104-18e505d7f5811bd%22%7D; zsxq_access_token=DFECBBDC-5EA5-103F-B7BB-E894CF8EEDFE_F230941593CDF2AE; zsxqsessionid=6974e59cd4a0540b769911dc84171e7a; abtest_env=product")
                .addHeader("Content-type", "application/json; charset=UTF-8")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            System.out.println(response.body().string());
        }
    }

//    @Test
//    public void QueryUnanswered() throws IOException, ParseException {
//        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//
//        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/15555541222422/topics?scope=all&count=20");
//
//        get.addHeader("cookie", "sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%2218e505d7f5710c-0fa58a2610ff17-26001b51-1327104-18e505d7f5811bd%22%2C%22first_id%22%3A%22%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMThlNTA1ZDdmNTcxMGMtMGZhNThhMjYxMGZmMTctMjYwMDFiNTEtMTMyNzEwNC0xOGU1MDVkN2Y1ODExYmQifQ%3D%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%22%2C%22value%22%3A%22%22%7D%2C%22%24device_id%22%3A%2218e505d7f5710c-0fa58a2610ff17-26001b51-1327104-18e505d7f5811bd%22%7D; zsxq_access_token=DFECBBDC-5EA5-103F-B7BB-E894CF8EEDFE_F230941593CDF2AE; abtest_env=product; zsxqsessionid=3b0c87cab6ed974d21fdd5913765435a");
//        get.addHeader("Content-type", "application/json; charset=UTF-8");
//
//        CloseableHttpResponse response = httpClient.execute(get);
//        String res = EntityUtils.toString(response.getEntity());
//        System.out.println(res);
//    }


//    public static void main(String[] args) {
//        RestTemplate restTemplate = new RestTemplate();
//        String url = "https://api.zsxq.com/v2/groups/15555541222422/topics?scope=all&count=20";
//
//        String response = restTemplate.getForObject(url, String.class);
//        System.out.println(response);
//    }
}
