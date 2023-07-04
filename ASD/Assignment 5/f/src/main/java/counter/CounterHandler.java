package counter;

abstract class CounterHandler {
    private CounterHandler nextHandler;

    public CounterHandler(CounterHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public void handle(int counterValue) {
        if (canHandle(counterValue)) {
            printOutput(counterValue);
        } else if (nextHandler != null) {
            nextHandler.handle(counterValue);
        }
    }

    protected abstract boolean canHandle(int counterValue);

    protected abstract void printOutput(int counterValue);
}

class RedHandler extends CounterHandler {
    public RedHandler(CounterHandler nextHandler) {
        super(nextHandler);

    }

    @Override
    protected boolean canHandle(int counterValue) {
        return (counterValue % 2 == 0) && ((counterValue < 10) || (counterValue == 12 || counterValue == 13));
    }

    @Override
    protected void printOutput(int counterValue) {
        System.out.println("Red");
    }
}

class GreenHandler extends CounterHandler {
    public GreenHandler(CounterHandler nextHandler) {
        super(nextHandler);

    }

    @Override
    protected boolean canHandle(int counterValue) {
        return (counterValue % 2 == 0) && (counterValue >= 10) && (counterValue != 12 && counterValue != 13);
    }

    @Override
    protected void printOutput(int counterValue) {
        System.out.println("Green");
    }
}

class BlueHandler extends CounterHandler {
    public BlueHandler(CounterHandler nextHandler) {
        super(nextHandler);

    }

    @Override
    protected boolean canHandle(int counterValue) {
        return (counterValue % 2 != 0) && ((counterValue < 15) || (counterValue == 17 || counterValue == 19));
    }

    @Override
    protected void printOutput(int counterValue) {
        System.out.println("Blue");
    }
}

class OrangeHandler extends CounterHandler {
    public OrangeHandler(CounterHandler nextHandler) {
        super(nextHandler);

    }

    @Override
    protected boolean canHandle(int counterValue) {
        return (counterValue % 2 != 0) && (counterValue >= 15) && (counterValue != 17 && counterValue != 19);
    }

    @Override
    protected void printOutput(int counterValue) {
        System.out.println("Orange");
    }
}