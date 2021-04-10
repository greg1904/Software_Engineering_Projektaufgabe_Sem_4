package team16.security.authorization;

import team16.configuration.Configuration;

public class IDCard {

    private final MagnetStripe stripe;
    private CardState state = CardState.ACTIVE;
    private int wrongPinCount = 0;
    private int wrongSuperPinCount = 0;

    public IDCard(MagnetStripe stripe) {
        this.stripe = stripe;
    }

    public MagnetStripe getStripe() {
        return stripe;
    }

    public CardState getState() {
        return state;
    }

    public void setState(CardState state) {
        this.state = state;
    }

    public void increaseWrongPinCount() {
        wrongPinCount++;
        if (wrongPinCount == Configuration.instance.maxWrongPinCount && state == CardState.ACTIVE) {
            System.out.println("Card locked");
            state = CardState.LOCKED;
        }
    }

    public void resetWrongPinCount() {
        wrongPinCount = 0;
    }

    public void increaseWrongSuperPinCount() {
        wrongSuperPinCount++;
        if (wrongSuperPinCount == Configuration.instance.maxWrongSuperPinCount && state == CardState.LOCKED) {
            System.out.println("Card invalid");
            state = CardState.INVALID;
        }
    }

    public void resetWrongSuperPinCount() {
        wrongSuperPinCount = 0;
    }
}
