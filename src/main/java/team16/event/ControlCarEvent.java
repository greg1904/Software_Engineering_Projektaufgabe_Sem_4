package team16.event;

public class ControlCarEvent implements IEvent {

    private final int id;

    public ControlCarEvent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
