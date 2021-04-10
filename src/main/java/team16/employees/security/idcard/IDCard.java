package team16.employees.security.idcard;

import team16.base.Configuration;

public class IDCard {
    private final MagnetStripe magnetStripe;
    private IDCardState state = IDCardState.ACTIVE;
    private int wrongPinEnteredCount = 0;
    private int wrongSuperPinEnteredCount = 0;

    public IDCard(MagnetStripe stripe) {
        this.magnetStripe = stripe;
    }

    public MagnetStripe getMagnetStripe() {
        return magnetStripe;
    }

    public IDCardState getState() {
        return state;
    }

    public void setState(IDCardState state) {
        this.state = state;
    }

    public void increaseWrongPinCount() {
        wrongPinEnteredCount++;
        if (wrongPinEnteredCount == Configuration.instance.maxWrongPinCount && state == IDCardState.ACTIVE) {
            state = IDCardState.LOCKED;
            System.out.println("Card has been locked.");
        }
    }

    public void resetWrongPinCount() {
        wrongPinEnteredCount = 0;
    }

    public void increaseWrongSuperPinCount() {
        wrongSuperPinEnteredCount++;
        if (wrongSuperPinEnteredCount == Configuration.instance.maxWrongSuperPinCount && state == IDCardState.LOCKED) {
            state = IDCardState.INVALID;
            System.out.println("Card has been invalidated.");
        }
    }

    public void resetWrongSuperPinCount() {
        wrongSuperPinEnteredCount = 0;
    }
}
