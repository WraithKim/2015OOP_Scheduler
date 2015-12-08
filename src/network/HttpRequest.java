package network;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import util.Constant;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Lumin on 2015-11-29.
 * HttpRequest를 요청할 수 있도록 도와주는 클래스입니다. (정적 클래스입니다.)
 */
public class HttpRequest {

    /**
     * HttpRequest 클래스의 인스턴스를 초기화합니다. HttpRequest는 정적 클래스이기 때문에 외부에서 인스턴스화 할 수 없습니다.
     */
    private HttpRequest() {

    }


    /**
     * Url로부터, POST 방법을 통해 HttpRequest를 Url에 요청합니다.
     * @param urlToSend Request를 요청할 Url 정보입니다.
     * @param requestPayLoad POST에 추가할 RequestPayLoad를 가리킵니다.
     * @param interpretEncoding 문자열 해석에 사용할 인코딩 형식을 가리킵니다.
     * @return Url에 대한 Reqeust 정보를 반환합니다. 빈 문자열이 반환될 경우 문제가 발생한 것입니다.
     */
    public static String sendHttpPostRequest(String urlToSend, String requestPayLoad, String interpretEncoding) throws IOException {
        try {
            URI targetUri = new URI(urlToSend);

            targetUri = new URIBuilder(targetUri).build();
            HttpPost postObject = new HttpPost(targetUri);
            postObject.setEntity(new StringEntity(requestPayLoad));

            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(postObject);
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);

            content = new String(content.getBytes(interpretEncoding));
            return content;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.println("HttpRequest::sendHttpPostRequest - Uri를 올바르게 생성할 수 없습니다.");
            return Constant.EMPTY_STRING;
        }
    }
}

