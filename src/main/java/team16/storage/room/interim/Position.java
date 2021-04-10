package team16.storage.room.interim;

import team16.configuration.Configuration;
import team16.storage.pallet.Pallet;

import java.util.Arrays;
import java.util.Objects;

public class Position {
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
        return Arrays.stream(pallets).anyMatch(Objects::nonNull);
    }

    @SuppressWarnings("unused")
    public boolean isFull() {
        return Arrays.stream(pallets).noneMatch(Objects::isNull);
    }

    public boolean isEmpty() {
        return Arrays.stream(pallets).noneMatch(Objects::nonNull);
    }

    public boolean hasRoom() {
        return Arrays.stream(pallets).anyMatch(Objects::isNull);
    }
}
