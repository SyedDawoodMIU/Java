package counter;

public class LogObserver implements Observer {

    @Override
    public void update(int count) {
        System.out.println("Count is " + count);
    }

}
