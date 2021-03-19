package pb.lms_desktop;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class API {
    private String baseURL;
    private HttpClient client;

    public API(String baseURL) {
        this.baseURL = baseURL;
        this.client = HttpClientBuilder.create().build();
    }

    public HttpResponse get(String s) throws IOException {
        return client.execute(new HttpGet(baseURL + s));
    }

    public HttpResponse post(String s) throws IOException {
        return client.execute(new HttpPost(baseURL + s));
    }

    public HttpResponse delete(String s) throws IOException {
        return client.execute(new HttpDelete(baseURL + s));
    }

    public HttpResponse put(String s) throws IOException {
        return client.execute(new HttpPut(baseURL + s));
    }

    public HttpResponse patch(String s) throws IOException {
        return client.execute(new HttpPatch(baseURL + s));
    }

    public static String toReadable(HttpResponse response) {
        String s = "";
        try {
            int data = response.getEntity().getContent().read();
            while (data != -1) {
                s += (char) data;
                data = response.getEntity().getContent().read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
}
