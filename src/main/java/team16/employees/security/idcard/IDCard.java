package team16.employees.security.idcard;

import team16.base.Configuration;
import team16.employees.security.idcard.states.IDCardActiveState;
import team16.employees.security.idcard.states.IIDCardState;

public class IDCard {
    private final MagnetStripe magnetStripe;
    private IIDCardState state = new IDCardActiveState();   //SOLID-Prinzip: State

    public IDCard(MagnetStripe stripe) {
        this.magnetStripe = stripe;
    }

    public void wrongPinEntered(){
        state.wrongInput(this);
    }

    public void correctPinEntered(){
        state.rightInput(this);
    }

    public MagnetStripe getMagnetStripe() {
        return magnetStripe;
    }

    public IIDCardState getState() {
        return state;
    }

    public void setState(IIDCardState state) {
        this.state = state;
    }
}
