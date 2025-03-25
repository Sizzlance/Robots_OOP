package gui;

import java.awt.*;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.beans.PropertyVetoException;
import java.io.*;
import java.util.Map;
import java.util.HashMap;


public class Window extends JInternalFrame {
    Map<String, Integer> windowData = new HashMap<>();
    private final String name;

    public Window(String name) {
        super(name, true, true, true, true);
        this.name = name;
        JPanel panel = new JPanel(new BorderLayout());
        getContentPane().add(panel);
        pack();
        loadWindowData();
    }

    public void setPosition(Map<String, Integer> windowData) {
        windowData.put(name + " x", getX());
        windowData.put(name + " y", getY());
        windowData.put(name + " width", getWidth());
        windowData.put(name + " height", getHeight());
        if (isIcon) {
            windowData.put(name + " state", 1);
        } else {
            windowData.put(name + " state", 0);
        }
    }

    public void saveWindowData(Map<String, Integer> windowData) {
        String filename = "position.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Map.Entry<String, Integer> entry : windowData.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    public void loadWindowData() {
        String filename = "position.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    windowData.put(key, Integer.valueOf(value));
                }
            }
            EventQueue.invokeLater(() -> {
                if (windowData.containsKey(name + " x") && windowData.containsKey(name + " y")) {
                    setLocation(windowData.get(name + " x"), windowData.get(name + " y"));
                }
                if (windowData.containsKey(name + " width") && windowData.containsKey(name + " height")) {
                    setSize(windowData.get(name + " width"), windowData.get(name + " height"));
                }
                if (windowData.containsKey(name + " state")) {
                    try {
                        if (windowData.get(name + " state") == 1) {
                            setIcon(true);
                        } else {
                            setIcon(false);
                        }
                    } catch (PropertyVetoException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке данных: " + e.getMessage());
        }
    }
}
