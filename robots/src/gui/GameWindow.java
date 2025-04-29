package gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;

public class GameWindow extends Window {
    private final GameVisualizer visualizer;

    public GameWindow() {
        super("Игровое поле");
        visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    public GameVisualizer getVisualizer() {
        return visualizer;
    }
}