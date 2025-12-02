package advent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Util {
    private Util() {}

    public static String getFile(final String fileName) {
        InputStream inputStream = Util.class.getClassLoader().getResourceAsStream(fileName);

        assert inputStream != null;

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException("File not found: " + fileName);
        }
    }
}
