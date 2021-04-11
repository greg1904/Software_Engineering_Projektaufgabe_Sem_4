package team16.location.access.hardware;

import team16.base.Configuration;
import team16.employees.security.access.ControlUnitAccessRole;
import team16.employees.security.encryption.EncryptionStrategyContext;
import team16.employees.security.idcard.CardContent;
import team16.employees.security.idcard.IDCard;
import team16.employees.security.idcard.states.IDCardActiveState;
import team16.employees.security.idcard.states.IDCardInvalidState;
import team16.employees.security.idcard.states.IDCardLockedState;

import java.util.Arrays;

public class IDCardReader {
    public boolean validateCard(IDCard card, int pin) {
        if (card.getState() instanceof IDCardLockedState || card.getState() instanceof IDCardInvalidState)
            return false;
        else
            return getCardContent(card, CardContent.PIN).equals(Integer.toString(pin));
    }

    public boolean unlockCard(IDCard card, int superPin) {
        if (card.getState() instanceof IDCardActiveState) {
            System.out.println("Card is already unlocked.");
            return true;
        } else if (card.getState() instanceof IDCardInvalidState) {
            System.out.println("Card is already invalid!");
            return false;
        } else if (card.getState() instanceof IDCardLockedState) {
            return getCardContent(card, CardContent.SUPERPIN).equals(Integer.toString(superPin));
        }

        return false;
    }

    public ControlUnitAccessRole getRole(IDCard card) {
        return ControlUnitAccessRole.valueOf(getCardContent(card, CardContent.ROLE));
    }

    private String getCardContent(IDCard card, CardContent content) {
        return getCardContent(card)[content.ordinal()];
    }

    private String[] getCardContent(IDCard card) {
        String dec = new EncryptionStrategyContext(Configuration.instance.currentEncryptionStrategy).decrypt(card.getMagnetStripe().getStoredData(), Configuration.instance.encryptionKey);
        String[] content = new String[CardContent.values().length];
        String[] parsed = Arrays.stream(dec.split(";"))
                .map(c -> c.substring(1))
                .map(c -> c.substring(0, c.length() - 1))
                .toArray(String[]::new);
        content[CardContent.ID.ordinal()] = parsed[0];
        content[CardContent.NAME.ordinal()] = parsed[1];
        content[CardContent.ROLE.ordinal()] = parsed[2];
        content[CardContent.PIN.ordinal()] = parsed[3];
        content[CardContent.SUPERPIN.ordinal()] = parsed[4];
        return content;
    }
}
