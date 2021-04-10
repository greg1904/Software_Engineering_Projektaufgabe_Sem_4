package team16.building.component;

import team16.command.ICommand;
import team16.security.ProxyControlUnit;
import team16.security.authorization.CardReader;
import team16.security.authorization.CardState;
import team16.security.authorization.IDCard;

public class Terminal {

    private final CardReader cardReader;
    private final TouchPad touchPad = new TouchPad(this);
    private final ProxyControlUnit proxy;
    private IDCard currentCard;
    private boolean loggedIn = false;

    public Terminal(CardReader cardReader, ProxyControlUnit proxy) {
        this.cardReader = cardReader;
        this.proxy = proxy;
    }

    public boolean startFunctionOnTouchPad(ICommand command) {
        if (!checkIfLoggedIn()) {
            System.out.println("Not logged in!");
            return false;
        }
        System.out.println("Execute Command " + command.getClass().getSimpleName());
        return touchPad.accessControlUnit(cardReader.getRole(currentCard), command);
    }

    public boolean insertCard(IDCard card) {
        if (currentCard == null) {
            System.out.println("Card inserted!");
            currentCard = card;
            return true;
        }
        System.out.println("There is already a card inserted!");
        return false;
    }

    public IDCard ejectCard() {
        if (!checkIfCardInserted()) {
            System.out.println("No Card Injected!");
            return null;
        }
        IDCard card = currentCard;
        currentCard = null;
        loggedIn = false;
        System.out.println("Card ejected!");
        return card;
    }

    public boolean login(int pin) {
        if (!checkIfCardInserted()) {
            return false;
        }
        boolean ret = cardReader.validateCard(currentCard, pin);
        if (!ret) {
            System.out.println("Wrong Pin: " + pin);
            currentCard.increaseWrongPinCount();
        } else {
            System.out.println("Successfully logged in!");
            currentCard.resetWrongPinCount();
            loggedIn = true;
        }
        return ret;
    }

    @SuppressWarnings("unused")
    public boolean unlockCard(int superPin) {
        if (!checkIfCardInserted()) {
            System.out.println("No Card inserted!");
            return false;
        }
        boolean ret = cardReader.unlockCard(currentCard, superPin);
        if (!ret) {
            System.out.println("Wrong superPIN: " + superPin);
            currentCard.increaseWrongSuperPinCount();
        } else {
            System.out.println("Successfully unlocked!");
            changeCardState(CardState.ACTIVE);
            currentCard.resetWrongSuperPinCount();
        }
        return ret;
    }

    @SuppressWarnings("SameParameterValue")
    private void changeCardState(CardState state) {
        cardReader.changeCardState(currentCard, state);
    }

    private boolean checkIfLoggedIn() {
        return loggedIn && checkIfCardInserted();
    }

    private boolean checkIfCardInserted() {
        if (currentCard == null) {
            System.out.println("Please insert Card first");
            return false;
        }
        return true;
    }

    public ProxyControlUnit getProxy() {
        return proxy;
    }
}