package team16.security.authorization;

import org.jetbrains.annotations.NotNull;
import team16.configuration.Configuration;

import java.util.Arrays;

public class CardReader {

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

    public Role getRole(IDCard card) {
        return Role.valueOf(getCardContent(card, CardContent.ROLE));
    }

    public void changeCardState(IDCard card, CardState state) {
        card.setState(state);
    }

    private String getCardContent(IDCard card, CardContent content) {
        return getCardContent(card)[content.ordinal()];
    }

    @NotNull
    private String[] getCardContent(IDCard card) {
        String dec = Configuration.INSTANCE.encryptionStrategy.decrypt(card.getStripe().getContent(), Configuration.INSTANCE.key);
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
