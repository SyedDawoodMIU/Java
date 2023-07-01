package counter;

interface CounterState {
    CounterState checkState();

    int increment();

    int decrement();
}

class SingleDigitState implements CounterState {
    private Counter counter;

    public SingleDigitState(Counter counter) {
        this.counter = counter;
    }

    @Override
    public int increment() {
        return counter.getValue() + 1;
    }

    @Override
    public int decrement() {
        return counter.getValue() - 1;
    }

    @Override
    public CounterState checkState() {
        if (counter.getValue() < 10) {
            return this;
        }
        return new DoubleDigitState(counter);
    }
}

class DoubleDigitState implements CounterState {
    private Counter counter;

    public DoubleDigitState(Counter counter) {
        this.counter = counter;
    }

    @Override
    public int increment() {
        return counter.getValue() + 2;
    }

    @Override
    public int decrement() {
        return counter.getValue() - 2;
    }

    @Override
    public CounterState checkState() {
        if (counter.getValue() < 10) {
            return new SingleDigitState(counter);
        } else if (counter.getValue() < 100) {
            return this;
        }
        return new TripleDigitState(counter);
    }
}

class TripleDigitState implements CounterState {
    private Counter counter;

    public TripleDigitState(Counter counter) {
        this.counter = counter;
    }

    @Override
    public int increment() {
        return counter.getValue() + 3;
    }

    @Override
    public int decrement() {
        return counter.getValue() - 3;
    }

    @Override
    public CounterState checkState() {
        if (counter.getValue() < 100) {
            return new DoubleDigitState(counter);
        }
        return this;
    }
}
