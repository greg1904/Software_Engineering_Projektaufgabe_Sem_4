package team16.storage.room.interim;

import team16.configuration.Configuration;
import team16.storage.pallet.Pallet;

import java.util.Arrays;
import java.util.Objects;

public class TemporaryPalletStoragePosition {
    private final Pallet[] pallets = new Pallet[Configuration.instance.interimPositionsMaxRoom];

    public boolean addPallet(Pallet pallet) {
        if (hasRoom()) {
            for (int i = 0; i < pallets.length; i++) {
                if (pallets[i] == null) {
                    pallets[i] = pallet;
                    return true;
                }
            }
        }
        return false;
    }

    public Pallet removePallet() {
        System.out.println("Removing pallet: " + !isEmpty());
        if (!isEmpty()) {
            for (int i = pallets.length - 1; i >= 0; i--) {
                if (pallets[i] != null) {
                    Pallet pallet = pallets[i];
                    pallets[i] = null;
                    return pallet;
                }
            }
        }
        return null;
    }

    public boolean hasPallets() {
        for(Pallet pallet : pallets){
            if(pallet != null) {
                return true;
            }
        }
//        return Arrays.stream(pallets).anyMatch(Objects::nonNull);
        return false;
    }

    @SuppressWarnings("unused")
    public boolean isFull() {
        return Arrays.stream(pallets).noneMatch(Objects::isNull);
    }

    public boolean isEmpty() {
        for(Pallet pallet : pallets){
            System.out.println(pallet);
            if(pallet != null) {
                System.out.println("a pallet was found: ");
                return false;
            }
        }

        System.out.println("returning true!");
        return true;
//        return Arrays.stream(pallets).noneMatch(Objects::nonNull);
    }

    public boolean hasRoom() {
        return Arrays.stream(pallets).anyMatch(Objects::isNull);
    }

    public int getPallets() {
        int count = 0;
        for(Pallet pallet : pallets){
            if(pallet != null)
                count++;
        }

        return count;
    }

    public String getPalletsString() {
        StringBuilder builder = new StringBuilder();
        for(Pallet pallet : pallets){
            builder.append(pallet).append(", ");
        }

        return builder.toString();
    }
}
