package network;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by Lumin on 2015-11-29.
 * HttpRequest를 요청할 수 있도록 도와주는 클래스입니다. (정적 클래스입니다.)
 */
public class HttpRequest {
    private static String EMPTY_STRING = "";

    /**
     * HttpRequest 클래스의 인스턴스를 초기화합니다. HttpRequest는 정적 클래스이기 때문에 외부에서 인스턴스화 할 수 없습니다.
     */
    private HttpRequest() {

    }


    /**
     * Url로부터, GET 방법을 통해 HttpRequest를 Url에 요청합니다.
     * @param urlToSend Request를 요청할 Url 정보입니다.
     * @return Url에 대한 Reqeust 정보를 반환합니다. 빈 문자열이 반환될 경우 문제가 발생한 것입니다.
     */
    public static String sendHttpGetRequest(String urlToSend) {
        try {
            URI targetUri = new URI(urlToSend);
            targetUri = new URIBuilder(targetUri).build();

            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(new HttpGet(targetUri));
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);

            return content;
        } catch (URISyntaxException e) {
            System.out.println("HttpRequest::sendHttpGetRequest - Uri를 올바르게 생성할 수 없습니다.");
            return EMPTY_STRING;
        } catch (IOException e) {
            System.out.println("HttpRequest::sendHttpGetRequest - HttpClient가 GET Request를 수행하는데 문제가 발생했거나, 올바르지 않은 Entity를 String으로 바꾸려고 합니다.");
            return EMPTY_STRING;
        }
    }

    /**
     * Url로부터, GET 방법을 통해 HttpRequest를 Url에 요청합니다.
     * @param urlToSend Request를 요청할 Url 정보입니다.
     * @param urlParameter Url에 추가할 Parameter를 가리킵니다.
     * @return Url에 대한 Reqeust 정보를 반환합니다. 빈 문자열이 반환될 경우 문제가 발생한 것입니다.
     */
    public static String sendHttpGetRequest(String urlToSend, Map<String, String> urlParameter) {
        try {
            URI targetUri = new URI(urlToSend);
            targetUri = addParameterToUri(targetUri, urlParameter);

            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(new HttpGet(targetUri));
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);

            return content;
        } catch (URISyntaxException e) {
            System.out.println("HttpRequest::sendHttpGetRequest - Uri를 올바르게 생성할 수 없습니다.");
            return EMPTY_STRING;
        } catch (IOException e) {
            System.out.println("HttpRequest::sendHttpGetRequest - HttpClient가 GET Request를 수행하는데 문제가 발생했거나, 올바르지 않은 Entity를 String으로 바꾸려고 합니다.");
            return EMPTY_STRING;
        }
    }


    /**
     * Url로부터, POST 방법을 통해 HttpRequest를 Url에 요청합니다.
     * @param urlToSend Request를 요청할 Url 정보입니다.
     * @param postParameter POST에 추가할 Form-data를 가리킵니다.
     * @return Url에 대한 Reqeust 정보를 반환합니다. 빈 문자열이 반환될 경우 문제가 발생한 것입니다.
     */
    public static String sendHttpPostRequest(String urlToSend, Map<String, String> postParameter) {
        try {
            URI targetUri = new URI(urlToSend);

            targetUri = new URIBuilder(targetUri).build();
            HttpPost postObject = new HttpPost(targetUri);
            postObject.setEntity(new UrlEncodedFormEntity(fetchNameValuePair(postParameter)));
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(postObject);
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);

            return content;
        } catch (URISyntaxException e) {
            System.out.println("HttpRequest::sendHttpPostRequest - Uri를 올바르게 생성할 수 없습니다.");
            return EMPTY_STRING;
        } catch (IOException e) {
            System.out.println("HttpRequest::sendHttpPostRequest - HttpClient가 POST Request를 수행하는데 문제가 발생했거나, 올바르지 않은 Entity를 String으로 바꾸려고 합니다.");
            return EMPTY_STRING;
        }
    }

    /**
     * Url로부터, POST 방법을 통해 HttpRequest를 Url에 요청합니다.
     * @param urlToSend Request를 요청할 Url 정보입니다.
     * @param requestPayLoad POST에 추가할 RequestPayLoad를 가리킵니다.
     * @return Url에 대한 Reqeust 정보를 반환합니다. 빈 문자열이 반환될 경우 문제가 발생한 것입니다.
     */
    public static String sendHttpPostRequest(String urlToSend, String requestPayLoad) {
        try {
            URI targetUri = new URI(urlToSend);

            targetUri = new URIBuilder(targetUri).build();
            HttpPost postObject = new HttpPost(targetUri);
            postObject.setEntity(new StringEntity(requestPayLoad));
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(postObject);
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);

            return content;
        } catch (URISyntaxException e) {
            System.out.println("HttpRequest::sendHttpPostRequest - Uri를 올바르게 생성할 수 없습니다.");
            return EMPTY_STRING;
        } catch (IOException e) {
            System.out.println("HttpRequest::sendHttpPostRequest - HttpClient가 POST Request를 수행하는데 문제가 발생했거나, 올바르지 않은 Entity를 String으로 바꾸려고 합니다.");
            return EMPTY_STRING;
        }
    }

    /**
     * Url로부터, POST 방법을 통해 HttpRequest를 Url에 요청합니다.
     * @param urlToSend Request를 요청할 Url 정보입니다.
     * @param postParameter POST에 추가할 Form-data를 가리킵니다.
     * @param urlParameter Url에 추가할 Parameter를 가리킵니다.
     * @return Url에 대한 Reqeust 정보를 반환합니다. 빈 문자열이 반환될 경우 문제가 발생한 것입니다.
     */
    public static String sendHttpPostRequest(String urlToSend, Map<String, String> urlParameter, Map<String, String> postParameter) {
        try {
            URI targetUri = new URI(urlToSend);
            targetUri = addParameterToUri(targetUri, urlParameter);

            targetUri = new URIBuilder(targetUri).build();
            HttpPost postObject = new HttpPost(targetUri);
            postObject.setEntity(new UrlEncodedFormEntity(fetchNameValuePair(postParameter)));

            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(postObject);
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);

            return content;
        } catch (URISyntaxException e) {
            System.out.println("HttpRequest::sendHttpPostRequest - Uri를 올바르게 생성할 수 없습니다.");
            return EMPTY_STRING;
        } catch (IOException e) {
            System.out.println("HttpRequest::sendHttpPostRequest - HttpClient가 POST Request를 수행하는데 문제가 발생했거나, 올바르지 않은 Entity를 String으로 바꾸려고 합니다.");
            return EMPTY_STRING;
        }
    }

    /**
     * Url로부터, POST 방법을 통해 HttpRequest를 Url에 요청합니다.
     * @param urlToSend Request를 요청할 Url 정보입니다.
     * @param keys POST에 추가할 Form-data의 Key들을 가리킵니다.
     * @param values POST에 추가할 value들을 가리킵니다.
     * @return Url에 대한 Reqeust 정보를 반환합니다. 빈 문자열이 반환될 경우 문제가 발생한 것입니다.
     */
    public static String sendHttpPostRequest(String urlToSend, String[] keys, String[] values) {
        if (keys.length != values.length) {
            System.out.println("HttpRequest::sendHttpPostRequest - key와 value의 개수가 다릅니다.");
            return EMPTY_STRING;
        }

        Hashtable<String, String> parameterHash = new Hashtable<>();

        for (int i = 0; i < keys.length; i++) {
            parameterHash.put(keys[i], values[i]);
        }

        return sendHttpPostRequest(urlToSend, parameterHash);
    }

    /**
     * Uri에 Parameter를 추가하여 새로운 Uri를 만듭니다.
     * @param uri 해당되는 Uri입니다.
     * @param parameter Uri에 추가하고자 하는 Parameter의 Map을 가리킵니다.
     * @return Parameter가 추가된 Uri입니다.
     * @throws URISyntaxException Uri를 정상적으로 생성할 수 없을 때, 예외가 발생합니다.
     */
    private static URI addParameterToUri(URI uri, Map<String, String> parameter) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(uri);

        for (String mapKey : parameter.keySet()) {
            uriBuilder = uriBuilder.addParameter(mapKey, parameter.get(mapKey));
        }

        return uriBuilder.build();
    }

    /**
     * 주어진 Name/Value Map으로부터, Name/Value Pair의 리스트를 만듭니다.
     * @param parameter 리스트를 만들 Name/Value Map입니다.
     * @return 만들어진 Name/Value Pair 리스트입니다.
     */
    private static List<NameValuePair> fetchNameValuePair(Map<String, String> parameter) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();

        for (String mapKey : parameter.keySet()) {
            nameValuePairs.add(new BasicNameValuePair(mapKey, parameter.get(mapKey)));
        }

        return nameValuePairs;
    }
}

