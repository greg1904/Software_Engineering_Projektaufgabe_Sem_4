package team16.employees.security.idcard.states;

import team16.employees.security.idcard.IDCard;

public class IDCardLockedState implements IIDCardState{
    private int wrongCounter = 0;

    @Override
    public void wrongInput(IDCard card) { //SOLID-Prinzip: State
        wrongCounter++;

        if(wrongCounter >= 2){
            lock(card);
        }
    }

    @Override
    public void rightInput(IDCard card) {
        wrongCounter = 0;

        unlock(card);
    }

    @Override
    public void lock(IDCard card) {
        card.setState(new IDCardInvalidState());
        System.out.println("Card has been invalidated.");
    }

    @Override
    public void unlock(IDCard card) {
        card.setState(new IDCardActiveState());
        System.out.println("Card has been reset with Super Pin.");
    }
}
