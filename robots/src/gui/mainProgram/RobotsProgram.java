package gui.mainProgram;

import gui.windows.mainWindow.MainApplicationFrame;

import java.awt.Frame;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class RobotsProgram {
    public static void main(String[] args) {
        try {
            Locale.setDefault(new Locale("ru", "RU"));

            ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

            UIManager.put("OptionPane.yesButtonText", bundle.getString("OptionPane.yesButtonText"));
            UIManager.put("OptionPane.noButtonText", bundle.getString("OptionPane.noButtonText"));
            UIManager.put("OptionPane.cancelButtonText", bundle.getString("OptionPane.cancelButtonText"));
            UIManager.put("OptionPane.okButtonText", bundle.getString("OptionPane.okButtonText"));

            UIManager.put("FileChooser.openButtonText", bundle.getString("FileChooser.openButtonText"));
            UIManager.put("FileChooser.saveButtonText", bundle.getString("FileChooser.saveButtonText"));
            UIManager.put("FileChooser.cancelButtonText", bundle.getString("FileChooser.cancelButtonText"));
            UIManager.put("FileChooser.fileNameLabelText", bundle.getString("FileChooser.fileNameLabelText"));
            UIManager.put("FileChooser.filesOfTypeLabelText", bundle.getString("FileChooser.filesOfTypeLabelText"));

            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            MainApplicationFrame frame = new MainApplicationFrame();
            frame.pack();
            frame.setVisible(true);
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        });
    }
}
