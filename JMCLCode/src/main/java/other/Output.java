package other;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface Output {
    static void output(OutputLevel outputLevel, String content) {
        output(outputLevel, Thread.currentThread().getName(), content);
    }

    static void output(OutputLevel outputLevel, String ThreadName, String content) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("[" + dateTime.format(formatter) + "]:[" + ThreadName + "/" + outputLevel + "] " + content);
    }

    static String input(OutputLevel outputLevel, String content) throws IOException {
        return input(outputLevel, Thread.currentThread().getName(), content);
    }

    static String input(OutputLevel outputLevel, String ThreadName, String content) throws IOException {
        output(outputLevel, ThreadName, content);
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        return input.readLine();
    }

    enum OutputLevel {
        Test, Ordinary, Debug, Info, Warning, Error;

    }
}
