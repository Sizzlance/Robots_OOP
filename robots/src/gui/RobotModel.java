package gui;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class RobotModel extends Observable {
    private volatile double m_robotPositionX = 100;
    private volatile double m_robotPositionY = 100;
    private volatile double m_robotDirection = 0;
    private volatile int m_targetPositionX = 150;
    private volatile int m_targetPositionY = 100;

    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.003;

    private static Timer modelTimer() {
        return new Timer("events generator", true);
    }
    public RobotModel() {
        Timer m_timer = modelTimer();
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateModel();
            }
        }, 0, 10);
    }

    public double getRobotPositionX() {
        return m_robotPositionX;
    }

    public double getRobotPositionY() {
        return m_robotPositionY;
    }

    public double getRobotDirection() {
        return m_robotDirection;
    }

    public int getTargetPositionX() {
        return m_targetPositionX;
    }

    public int getTargetPositionY() {
        return m_targetPositionY;
    }

    public void setTargetPosition(int x, int y) {
        m_targetPositionX = x;
        m_targetPositionY = y;
        setChanged();
        notifyObservers("targetPosition");
    }

    public void updateModel() {
        double distance = distance(m_targetPositionX, m_targetPositionY,
                m_robotPositionX, m_robotPositionY);
        if (distance < 0.5) {
            return;
        }

        double angleToTarget = angleTo(m_robotPositionX, m_robotPositionY,
                m_targetPositionX, m_targetPositionY);
        double angleDiff = asNormalizedRadians(angleToTarget - m_robotDirection);

        double angularVelocity = 0;
        if (angleDiff > Math.PI) {
            angularVelocity = -maxAngularVelocity;
        } else if (angleDiff > 0.01) {
            angularVelocity = maxAngularVelocity;
        } else if (angleDiff < -Math.PI) {
            angularVelocity = maxAngularVelocity;
        } else if (angleDiff < -0.01) {
            angularVelocity = -maxAngularVelocity;
        }

        moveRobot(maxVelocity, angularVelocity, 10);
        setChanged();
        notifyObservers("robotPosition");
    }

    private void moveRobot(double velocity, double angularVelocity, double duration) {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);

        double newX = m_robotPositionX + velocity / angularVelocity *
                (Math.sin(m_robotDirection + angularVelocity * duration) -
                        Math.sin(m_robotDirection));
        if (!Double.isFinite(newX)) {
            newX = m_robotPositionX + velocity * duration * Math.cos(m_robotDirection);
        }

        double newY = m_robotPositionY - velocity / angularVelocity *
                (Math.cos(m_robotDirection + angularVelocity * duration) -
                        Math.cos(m_robotDirection));
        if (!Double.isFinite(newY)) {
            newY = m_robotPositionY + velocity * duration * Math.sin(m_robotDirection);
        }

        m_robotPositionX = newX;
        m_robotPositionY = newY;
        m_robotDirection = asNormalizedRadians(m_robotDirection + angularVelocity * duration);
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY) {
        double diffX = toX - fromX;
        double diffY = toY - fromY;
        return Math.atan2(diffY, diffX);
    }

    private static double asNormalizedRadians(double angle) {
        while (angle < -Math.PI) {
            angle += 2 * Math.PI;
        }
        while (angle >= Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }

    private static double applyLimits(double value, double min, double max) {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }
}