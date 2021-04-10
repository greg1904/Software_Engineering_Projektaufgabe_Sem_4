package team16.location.access.hardware;

import org.jetbrains.annotations.NotNull;
import team16.base.Configuration;
import team16.employees.security.access.ControlUnitAcessRole;
import team16.employees.security.idcard.CardContent;
import team16.employees.security.idcard.IDCard;
import team16.employees.security.idcard.IDCardState;

import java.util.Arrays;

public class IDCardReader {

    public boolean validateCard(IDCard card, int pin) {
        return switch (card.getState()) {
            case LOCKED, INVALID -> false;
            default -> getCardContent(card, CardContent.PIN).equals(Integer.toString(pin));
        };
    }

    public boolean unlockCard(IDCard card, int superPin) {
        switch (card.getState()) {
            case INVALID:
                System.out.println("Card is already invalid!");
                return false;
            case ACTIVE:
                System.out.println("Card already unlocked!");
                return true;
            case LOCKED:
                return getCardContent(card, CardContent.SUPERPIN).equals(Integer.toString(superPin));
        }
        return false;
    }

    public ControlUnitAcessRole getRole(IDCard card) {
        return ControlUnitAcessRole.valueOf(getCardContent(card, CardContent.ROLE));
    }

    public void changeCardState(IDCard card, IDCardState state) {
        card.setState(state);
    }

    private String getCardContent(IDCard card, CardContent content) {
        return getCardContent(card)[content.ordinal()];
    }

    @NotNull
    private String[] getCardContent(IDCard card) {
        String dec = Configuration.instance.encryptionStrategy.decrypt(card.getMagnetStripe().getStoredData(), Configuration.instance.encryptionKey);
        String[] content = new String[CardContent.values().length];
        String[] parsed =  Arrays.stream(dec.split(";"))
                .map(c -> c.substring(1))
                .map(c -> c.substring(0, c.length() - 1))
                .toArray(String[]::new);
        content[CardContent.ID.ordinal()] = parsed[0];
        content[CardContent.NAME.ordinal()] = parsed[1];
        content[CardContent.ROLE.ordinal()] = parsed[2];
        content[CardContent.PIN.ordinal()] = parsed[3];
        content[CardContent.SUPERPIN.ordinal()] = parsed[4];//Make sure the ordinal matches the Content
        return content;
    }
}
