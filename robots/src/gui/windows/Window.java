package gui.windows;

import java.awt.*;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.beans.PropertyVetoException;

public class Window extends JInternalFrame {
    private final String name;
    private WindowState state;

    public Window(String name) {
        super(name, true, true, true, true);
        this.name = name;
        this.state = new WindowState();
        JPanel panel = new JPanel(new BorderLayout());
        getContentPane().add(panel);
        pack();
    }

    public WindowState getWindowState() {
        state.x = getX();
        state.y = getY();
        state.width = getWidth();
        state.height = getHeight();
        state.isIcon = isIcon();
        return state;
    }

    public void applyWindowState(WindowState state) {
        this.state = state;
        EventQueue.invokeLater(() -> {
            setLocation(state.x, state.y);
            setSize(state.width, state.height);
            try {
                setIcon(state.isIcon);
            } catch (PropertyVetoException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public String getWindowName() {
        return name;
    }

    public static class WindowState {
        public int x;
        public int y;
        public int width;
        public int height;
        public boolean isIcon;
    }
}