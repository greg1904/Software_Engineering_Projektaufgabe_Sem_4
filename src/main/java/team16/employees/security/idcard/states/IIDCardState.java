package team16.employees.security.idcard.states;

import team16.employees.security.idcard.IDCard;

public interface IIDCardState { //SOLID-Prinzip: State
    void wrongInput(IDCard card);

    void rightInput(IDCard card);

    void lock(IDCard card);

    void unlock(IDCard card);
}
