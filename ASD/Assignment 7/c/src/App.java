import java.util.ArrayList;
import java.util.List;

class CameraRecord {
    private String licensePlate;
    private int speed;
    private int cameraId;

    public CameraRecord(String licensePlate, int speed, int cameraId) {
        this.licensePlate = licensePlate;
        this.speed = speed;
        this.cameraId = cameraId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public int getSpeed() {
        return speed;
    }

    public int getCameraId() {
        return cameraId;
    }
}

abstract class Handler {
    private Handler nextHandler;

    public Handler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract void handle(CameraRecord cameraRecord);

    public Handler getNextHandler() {
        return nextHandler;
    }
}

class StolenCarHandler extends Handler {

    public StolenCarHandler(Handler handler) {
        super(handler);
    }

    @Override
    public void handle(CameraRecord cameraRecord) {
        if (isCarStolen(cameraRecord)) {
            System.out.println(
                    "Car with license plate " + cameraRecord.getLicensePlate() + " is stolen. Notifying the police.");
        } else if (getNextHandler() != null) {
            getNextHandler().handle(cameraRecord);
        }
    }

    private boolean isCarStolen(CameraRecord cameraRecord) {

        return false;
    }
}

class SpeedingCarHandler extends Handler {

    public SpeedingCarHandler(Handler handler) {
        super(handler);
    }

    @Override
    public void handle(CameraRecord cameraRecord) {
        if (isCarSpeeding(cameraRecord)) {
            System.out.println("Car with license plate " + cameraRecord.getLicensePlate()
                    + " is speeding. Sending a speeding ticket to the owner.");
        } else if (getNextHandler() != null) {
            getNextHandler().handle(cameraRecord);
        }
    }

    private boolean isCarSpeeding(CameraRecord cameraRecord) {

        return false;
    }
}

class UnregisteredCarHandler extends Handler {

    public UnregisteredCarHandler(Handler handler) {
        super(handler);
    }

    @Override
    public void handle(CameraRecord cameraRecord) {
        if (isCarUnregistered(cameraRecord)) {
            System.out.println("Car with license plate " + cameraRecord.getLicensePlate()
                    + " is unregistered. Sending a ticket to the owner.");
        } else if (getNextHandler() != null) {
            getNextHandler().handle(cameraRecord);
        }
    }

    private boolean isCarUnregistered(CameraRecord cameraRecord) {

        return false;
    }
}

class UnpaidTicketHandler extends Handler {

    public UnpaidTicketHandler(Handler handler) {
        super(handler);
    }

    @Override
    public void handle(CameraRecord cameraRecord) {
        if (hasUnpaidTickets(cameraRecord)) {
            System.out.println("Car with license plate " + cameraRecord.getLicensePlate()
                    + " has unpaid tickets. Notifying the police.");
        } else {
            System.out.println("Car with license plate " + cameraRecord.getLicensePlate()
                    + " has no unpaid tickets.");
        }
    }

    private boolean hasUnpaidTickets(CameraRecord cameraRecord) {

        return false;
    }
}

public class App {
    private Handler handler;

    public App() {

        Handler unpaidTicketHandler = new UnpaidTicketHandler(null);
        Handler unregisteredCarHandler = new UnregisteredCarHandler(unpaidTicketHandler);
        Handler speedingCarHandler = new SpeedingCarHandler(unregisteredCarHandler);
        handler = new StolenCarHandler(speedingCarHandler);

    }

    public void processCameraRecords(List<CameraRecord> cameraRecords) {
        for (CameraRecord cameraRecord : cameraRecords) {
            handler.handle(cameraRecord);
        }
    }

    public static void main(String[] args) {

        List<CameraRecord> cameraRecords = new ArrayList<>();
        cameraRecords.add(new CameraRecord("ABC123", 100, 1));
        cameraRecords.add(new CameraRecord("XYZ789", 120, 2));

        App application = new App();
        application.processCameraRecords(cameraRecords);
    }
}
