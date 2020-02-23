import java.io.IOException;
import java.text.ParseException;
import java.net.URL;
import java.net.HttpURLConnection;

import jdk.jfr.Configuration;
import jdk.jfr.consumer.EventStream;
import jdk.jfr.consumer.RecordingStream;

import java.io.DataOutputStream;

public class Hello {
    public static void main(String[] args) throws java.io.IOException, java.text.ParseException {
        System.out.println(System.getProperty("java.version"));
        Configuration config = Configuration.getConfiguration("default");
        System.out.println(config.getDescription());
        System.out.println("settings:");
        config.getSettings().forEach((key, value) -> System.out.println(key + ": " + value));

        Thread thread = new Thread() {
            public void run() {
                while (true) {
                    try {
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();

// open a stream and start local recording
        try (EventStream es = new RecordingStream(config)) {

// register event handlers
            es.onEvent("jdk.GarbageCollection", System.out::println);
            es.onEvent("jdk.CPULoad", System.out::println);
            es.onEvent("jdk.FileRead", System.out::println);
            es.onEvent("jdk.FileWrite", System.out::println);
            es.onEvent("jdk.SocketRead", System.out::println);
            es.onEvent("jdk.SocketWrite", System.out::println);
            es.onEvent("jdk.JVMInformation", System.out::println);

// start and block
            es.start();
        }
        System.out.println("Hello");
    }
}
