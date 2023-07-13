
import java.util.ArrayList;
import java.util.List;

import framework.Car;
import framework.CarImpl;

public class RaceApplication {
    private Car car;
    private List<IFrame> frames;

    public RaceApplication() {
        this.car = new CarImpl();
        this.frames = new ArrayList<>();
        this.frames.add(new WindowFrame());

    }

    public void init() {

        for (IFrame frame : frames) {
            frame.print(car.speed());
        }

    }

    public void incrementSpeed() {
        new SpeedChangeCommand(car, 1).execute();
    }

    public void decreaseSpeed() {
        new SpeedChangeCommand(car, -1).execute();
    }

    public void jump() {
        new JumpCommand(car).execute();
    }
}
