package gui.windows;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WindowStateManager {
    private static final String FILE_NAME = "window_states.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void saveWindowStates(Map<String, Window.WindowState> states) throws IOException {
        objectMapper.writeValue(new File(FILE_NAME), states);
    }

    public Map<String, Window.WindowState> loadWindowStates() throws IOException {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new HashMap<>();
        }
        return objectMapper.readValue(file, new TypeReference<Map<String, Window.WindowState>>() {});
    }
}