package bonjour.helloworld;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

public final class JsonUtils {
    

    public static <T> T toObject(InputStream in, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(in, clazz);
        } catch (IOException e) {
            return null;
        }
    }

    public static void write(Writer writer, Object value) throws IOException {
        new ObjectMapper().writeValue(writer, value);
    }
}
