import java.io.IOException;
import java.text.ParseException;
import java.net.URL;
import java.net.HttpURLConnection;

import jdk.jfr.Configuration;
import jdk.jfr.consumer.EventStream;
import jdk.jfr.consumer.RecordingStream;

import java.io.DataOutputStream;

public class Consumer {
    public static void main(String[] args) throws java.io.IOException, java.text.ParseException {
        System.out.println(System.getProperty("java.version"));

        var path = args[0];

        System.out.println("JFR Path=" + args[0]);

        try (EventStream es = EventStream.openRepository(java.nio.file.Path.of(path))) {

            es.onEvent("jdk.GarbageCollection", System.out::println);
            es.onEvent("jdk.CPULoad", System.out::println);
            es.onEvent("jdk.FileRead", System.out::println);
            es.onEvent("jdk.FileWrite", System.out::println);
            es.onEvent("jdk.SocketRead", System.out::println);
            es.onEvent("jdk.SocketWrite", System.out::println);
            es.onEvent("jdk.JVMInformation", System.out::println);

            es.start();
        }
    }
}
