import java.io.IOException;
import java.text.ParseException;
import java.net.URL;
import java.net.HttpURLConnection;

import jdk.jfr.Configuration;
import jdk.jfr.consumer.EventStream;
import jdk.jfr.consumer.RecordingStream;

import java.io.DataOutputStream;

public class Producer {
    public static void main(String[] args) throws Exception {
        while (true) {
            java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
            java.net.http.HttpRequest request = java.net.http.HttpRequest
                    .newBuilder()
                    .uri(java.net.URI
                                 .create("http://slowwly.robertomurray.co.uk/delay/300/url/http://www.google.co.uk"))
                    .GET()
                    .build();

            System.gc();

            var resp = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
            System.out.println(resp.body().length());
        }
    }
}

