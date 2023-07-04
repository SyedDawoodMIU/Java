package counter;

public class HandlerObserver implements Observer {
    private CounterHandler handler;

    @Override
    public void update(int count) {
        handler.handle(count);
    }

    public void setHandler(CounterHandler nextHandler) {
        this.handler = nextHandler;
    }

}
