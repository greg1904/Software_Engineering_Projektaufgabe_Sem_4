package team16.location.logistics.temporarystorage;

import team16.data.datainstances.pallet.Pallet;

import java.util.Arrays;
import java.util.Objects;

public class TemporaryPalletStoragePosition {
    private final Pallet[] pallets = new Pallet[2];

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
        for (Pallet pallet : pallets) {
            if (pallet != null) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        for (Pallet pallet : pallets) {
            if (pallet != null) {
                return false;
            }
        }
        return true;
    }

    public boolean hasRoom() {
        return Arrays.stream(pallets).anyMatch(Objects::isNull);
    }

    public int getPallets() {
        int count = 0;
        for (Pallet pallet : pallets) {
            if (pallet != null)
                count++;
        }

        return count;
    }

    public String getPalletsString() {
        StringBuilder builder = new StringBuilder();
        for (Pallet pallet : pallets) {
            builder.append(pallet).append(", ");
        }

        return builder.toString();
    }
}
