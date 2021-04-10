package team16.employees.security.idcard.states;

import team16.employees.security.idcard.IDCard;

public class IDCardActiveState implements IIDCardState{ //SOLID-Prinzip: State
    private int wrongCount = 0;

    @Override
    public void wrongInput(IDCard card) {
        wrongCount++;

        if(wrongCount >= 3){
            lock(card);
        }
    }

    @Override
    public void rightInput(IDCard card) {
        wrongCount = 0;
    }

    @Override
    public void lock(IDCard card) {
        card.setState(new IDCardLockedState());
    }

    @Override
    public void unlock(IDCard card) {
        card.setState(new IDCardActiveState());
    }
}
