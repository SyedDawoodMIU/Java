import framework.Car;

public class JumpCommand implements ICommand {
    private Car car;

    public JumpCommand(Car car) {
        this.car = car;
    }

    @Override
    public void execute() {
        car.jump();
    }
}
