package counter;

public class IncrementCommand implements Command {
    private Counter counter;

    public IncrementCommand(Counter counter) {
        this.counter = counter;
    }

    public void execute() {
        counter.increment();
    }

    public void undo() {
        counter.decrement();
    }

    public void redo() {
        counter.increment();
    }
}
