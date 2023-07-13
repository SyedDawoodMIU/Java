package framework;

public class CarImpl implements Car
{
       
    private int speed;
    private Listener listener;

    

    public int speed() {
        return this.speed;
    }

    public void increaseSpeed() {
        this.speed++;
        if (listener != null) {
            listener.onSpeedChanged(this.speed);
        }
    }

    public void decreaseSpeed() {
        this.speed--;
        if (listener != null) {
            listener.onSpeedChanged(this.speed);
        }
    }

    public void logSpeed() {
        Logger.log(this.speed);
    }

    public void jump() {
        this.speed = 100;
        if (listener != null) {
            listener.onSpeedChanged(this.speed);
        }
    }

}