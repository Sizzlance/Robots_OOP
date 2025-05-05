package gui.windows.mainWindow;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

import gui.windows.*;
import log.Logger;

public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final WindowStateManager stateManager = new WindowStateManager();
    private final List<Window> windows = new ArrayList<>();


    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);

        setContentPane(desktopPane);

        windows.add(createCoordinatesWindow());
        windows.add(createGameWindow());
        windows.add(createLogWindow());

        Map<String, Window.WindowState> savedStates = loadWindowStates();

        for (Window window : windows) {
            applyWindowState(window, savedStates);
            addWindow(window);
        }

        if (windows.get(0) instanceof CoordinatesWindow coordinatesWindow && windows.get(1) instanceof GameWindow gameWindow) {
            coordinatesWindow.setRobotModel(gameWindow.getVisualizer().getRobotModel());
        }

        MenuBarGenerator menuBarGenerator = new MenuBarGenerator(this);
        setJMenuBar(menuBarGenerator.generateMenuBar());

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmExit();
            }
        });
    }

    void confirmExit() {
        UIManager.put("OptionPane.yesButtonText", "Да");
        UIManager.put("OptionPane.noButtonText", "Нет");
        int result = JOptionPane.showConfirmDialog(
                this,
                "Вы уверены, что хотите выйти?",
                "Подтверждение выхода",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            saveWindowStates();
            System.exit(0);
        }
    }

    private Map<String, Window.WindowState> loadWindowStates() {
        try {
            return stateManager.loadWindowStates();
        } catch (IOException e) {
            Logger.error("Ошибка загрузки состояний окон: " + e.getMessage());
            return new HashMap<>();
        }
    }

    private void applyWindowState(Window window, Map<String, Window.WindowState> states) {
        Window.WindowState state = states.get(window.getWindowName());
        if (state != null) {
            window.applyWindowState(state);
        }
    }

    private void saveWindowStates() {
        Map<String, Window.WindowState> states = new HashMap<>();

        for (Window window : windows) {
            states.put(window.getWindowName(), window.getWindowState());
        }

        try {
            stateManager.saveWindowStates(states);
        } catch (IOException e) {
            Logger.error("Ошибка сохранения состояний окон: " + e.getMessage());
        }
    }

    protected LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        Logger.debug("Протокол работает");
        return logWindow;
    }

    protected GameWindow createGameWindow() {
        return new GameWindow();
    }

    protected CoordinatesWindow createCoordinatesWindow() {
        return new CoordinatesWindow();
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
}