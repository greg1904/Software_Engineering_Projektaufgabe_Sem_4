package team16.security.authorization;

import team16.configuration.Configuration;

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
            System.out.println("Card locked");
            state = IDCardState.LOCKED;
        }
    }

    public void resetWrongPinCount() {
        wrongPinEnteredCount = 0;
    }

    public void increaseWrongSuperPinCount() {
        wrongSuperPinEnteredCount++;
        if (wrongSuperPinEnteredCount == Configuration.instance.maxWrongSuperPinCount && state == IDCardState.LOCKED) {
            System.out.println("Card invalid");
            state = IDCardState.INVALID;
        }
    }

    public void resetWrongSuperPinCount() {
        wrongSuperPinEnteredCount = 0;
    }
}
