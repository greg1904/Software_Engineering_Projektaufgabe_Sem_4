package team16.employees.security.idcard.states;

import team16.employees.security.idcard.IDCard;

public class IDCardInvalidState implements IIDCardState { //SOLID-Prinzip: State
    @Override
    public void wrongInput(IDCard card) {
        System.out.println("Card is invalid - no Inputs will be accepted.");
    }

    @Override
    public void rightInput(IDCard card) {
        System.out.println("Card is invalid - no Inputs will be accepted.");
    }

    @Override
    public void lock(IDCard card) {
        System.out.println("Card is invalid - no Inputs will be accepted.");
    }

    @Override
    public void unlock(IDCard card) {
        System.out.println("Card is invalid - no Inputs will be accepted.");
    }
}
