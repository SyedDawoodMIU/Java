package counter;

class DecrementCommand implements Command {
    private Counter counter;

    public DecrementCommand(Counter counter) {
        this.counter = counter;
    }

    public void execute() {
        counter.decrement();
    }

    public void undo() {
        counter.increment();
    }

    public void redo() {
        counter.decrement();
    }
}