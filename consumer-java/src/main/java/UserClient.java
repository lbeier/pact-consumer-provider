import org.apache.http.client.fluent.Request;

import java.io.IOException;

public class UserClient {

    private String url;

    public UserClient(String url) {
        this.url = url;
    }

    public String get(String path) {
        try {
            return Request.Get(this.url + path)
                    .addHeader("Content-Type", "application/json")
                    .execute().returnContent().asString();
        } catch (IOException e) {
            return "";
        }
    }
}
