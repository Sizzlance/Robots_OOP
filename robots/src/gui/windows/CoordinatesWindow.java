package gui.windows;

import gui.robot.RobotModel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class CoordinatesWindow extends Window implements Observer {
    private final JLabel coordinatesLabel = new JLabel("X: 0, Y: 0");
    private RobotModel robotModel;

    public CoordinatesWindow() {
        super("Координаты робота");
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        coordinatesLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(coordinatesLabel);
        getContentPane().add(panel);
        pack();
    }

    public void setRobotModel(RobotModel model) {
        if (this.robotModel != null) {
            this.robotModel.deleteObserver(this);
        }
        this.robotModel = model;
        if (this.robotModel != null) {
            this.robotModel.addObserver(this);
            if (robotModel != null) {
                coordinatesLabel.setText(String.format("X: %.1f, Y: %.1f",
                        robotModel.getRobotPositionX(),
                        robotModel.getRobotPositionY()));
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof double[] position) {
            coordinatesLabel.setText(String.format("X: %.1f, Y: %.1f",
                    position[0], position[1]));
        }
    }
}