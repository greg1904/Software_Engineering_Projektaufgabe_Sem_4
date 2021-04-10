package team16.location.access.hardware;

import team16.communication.commands.ICommand;
import team16.employees.security.idcard.IDCard;
import team16.location.access.software.ProxyControlUnit;

public class Terminal {
    private final IDCardReader IDCardReader;
    private final TouchPad touchPad = new TouchPad(this);
    private final ProxyControlUnit proxy;
    private IDCard insertedCard;
    private boolean loggedIn = false;

    public Terminal(IDCardReader IDCardReader, ProxyControlUnit proxy) {
        this.IDCardReader = IDCardReader;
        this.proxy = proxy;
    }

    public boolean startFunctionOnTouchPad(ICommand command) {
        if (!checkIfLoggedIn()) {
            System.out.println("Not logged in!");
            return false;
        }
        System.out.println("Execute Command " + command.getClass().getSimpleName());
        return touchPad.accessControlUnit(IDCardReader.getRole(insertedCard), command);
    }

    public boolean insertCard(IDCard card) {
        if (insertedCard == null) {
            System.out.println("Card has been inserted!");
            insertedCard = card;
            return true;
        }
        System.out.println("There is already a card in the Card Reader!");
        return false;
    }

    public IDCard ejectCard() {
        if (!checkIfCardInserted()) {
            System.out.println("No Card available to eject!");
            return null;
        }
        IDCard card = insertedCard;
        insertedCard = null;
        loggedIn = false;
        System.out.println("Card has been ejected!");
        return card;
    }

    public boolean login(int pin) {
        if (!checkIfCardInserted()) {
            return false;
        }
        boolean ret = IDCardReader.validateCard(insertedCard, pin);
        if (!ret) {
            System.out.println("Wrong Pin: " + pin);
            insertedCard.wrongPinEntered();
        } else {
            System.out.println("Successfully logged in!");
            insertedCard.correctPinEntered();
            loggedIn = true;
        }
        return ret;
    }

    public boolean unlockCard(int superPin) {
        if (!checkIfCardInserted()) {
            System.out.println("No Card inserted!");
            return false;
        }
        boolean ret = IDCardReader.unlockCard(insertedCard, superPin);
        if (!ret) {
            System.out.println("Wrong superPIN: " + superPin);
            insertedCard.wrongPinEntered();
        } else {
            System.out.println("Successfully unlocked!");
            insertedCard.correctPinEntered();
        }
        return ret;
    }

    private boolean checkIfLoggedIn() {
        return loggedIn && checkIfCardInserted();
    }

    private boolean checkIfCardInserted() {
        if (insertedCard == null) {
            System.out.println("A Card has to be inserted.");
            return false;
        }
        return true;
    }

    public ProxyControlUnit getProxy() {
        return proxy;
    }
}
