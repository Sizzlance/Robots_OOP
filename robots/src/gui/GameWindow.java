package gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;

public class GameWindow extends Window {

    public GameWindow() {
        super("Игровое поле");
        GameVisualizer m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }
}
