
import framework.Car;

public class SpeedChangeCommand implements ICommand {
    private Car car;
    private int direction;

    public SpeedChangeCommand(Car car, int direction) {
        this.car = car;
        this.direction = direction;
    }

    @Override
    public void execute() {
        if (direction > 0) {
            car.increaseSpeed();
        } else {
            car.decreaseSpeed();
        }
    }
}
