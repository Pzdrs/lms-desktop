package pb.lms_desktop;

import javafx.scene.control.Alert;
import javafx.util.Pair;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class API {
    private HttpClient client;
    private String baseURL;

    public API(String baseURL) {
        this.baseURL = baseURL;
        this.client = HttpClients.createDefault();
    }

    public void setHeaders(Header... headers) {
        this.client = HttpClients.custom().setDefaultHeaders(Arrays.asList(headers)).build();
    }

    public HttpResponse get(String s) throws IOException {
        return client.execute(new HttpGet(baseURL + s));
    }

    public HttpResponse post(String s, BasicNameValuePair... data) throws IOException {
        HttpPost req = new HttpPost(baseURL + s);
        req.setEntity(new UrlEncodedFormEntity(new ArrayList<>(Arrays.asList(data)), StandardCharsets.UTF_8));
        return client.execute(req);
    }

    public HttpResponse delete(String s) throws IOException {
        return client.execute(new HttpDelete(baseURL + s));
    }

    public HttpResponse put(String s) throws IOException {
        return client.execute(new HttpPut(baseURL + s));
    }

    public HttpResponse patch(String s, BasicNameValuePair... data) throws IOException {
        HttpPatch req = new HttpPatch(baseURL + s);
        req.setEntity(new UrlEncodedFormEntity(new ArrayList<>(Arrays.asList(data)), StandardCharsets.UTF_8));
        return client.execute(req);
    }

    public Pair<Integer, String> changeUserProperty(String key, String value) throws IOException {
        HttpResponse response = patch("/users/" + Main.getStore().getUser().getId(), new BasicNameValuePair(key, value));
        return new Pair<>(response.getStatusLine().getStatusCode(), asText(response.getEntity().getContent()));
    }

    public Pair<Integer, String> login(String username, String password) {
        try {
            HttpResponse response = post("/auth/login",
                    new BasicNameValuePair("username", username),
                    new BasicNameValuePair("password", password));
            return new Pair<>(response.getStatusLine().getStatusCode(), asText(response.getEntity().getContent()));
        } catch (IOException e) {
            // Show error dialog
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Login error");
            error.setHeaderText("Service unavailable, please try again later.");
            error.show();
        }
        return new Pair<>(0, "");
    }

    public static String asText(HttpResponse response) throws IOException {
        return asText(response.getEntity().getContent());
    }

    public static String asText(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        StringBuilder s = new StringBuilder();
        try {
            int data = reader.read();
            while (data != -1) {
                s.append((char) data);
                data = reader.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s.toString();
    }
}
