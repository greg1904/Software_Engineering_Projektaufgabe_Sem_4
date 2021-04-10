package team16.communication.events;

public class ControlCarEvent implements IEvent {

    private final int id;

    public ControlCarEvent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
