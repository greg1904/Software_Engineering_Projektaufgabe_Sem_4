package team16.vehicle;

import org.jetbrains.annotations.NotNull;
import team16.configuration.Configuration;
import team16.storage.pallet.Pallet;

import java.util.Arrays;
import java.util.Objects;

public class Trailer {

    private final Pallet[][] pallets = new Pallet[2][Configuration.INSTANCE.trailerPalletCount];

    @SuppressWarnings("unused")
    @NotNull
    public Pallet[] getLeftPallets() {
        return pallets[0];
    }

    @SuppressWarnings("unused")
    @NotNull
    public Pallet[] getRightPallets() {
        return pallets[1];
    }

    public boolean isFull() {
        return Arrays.stream(pallets).flatMap(Arrays::stream).noneMatch(Objects::isNull);
    }

    public boolean hasRoom() {
        return !isFull();
    }

    public boolean hasLoad() {
        return Arrays.stream(pallets)
                .flatMap(Arrays::stream)
                .anyMatch(Objects::nonNull);
    }

    public boolean addPallet(Pallet pallet) {
        return addPalletLeft(pallet) || addPalletRight(pallet);
    }

    public boolean addPalletLeft(Pallet pallet) {
        for (int i = 0; i < pallets[0].length; i++) {
            if (pallets[0][i] == null) {
                pallets[0][i] = pallet;
                return true;
            }
        }
        return false;
    }

    public boolean addPalletRight(Pallet pallet) {
        for (int i = 0; i < pallets[1].length; i++) {
            if (pallets[1][i] == null) {
                pallets[1][i] = pallet;
                return true;
            }
        }
        return false;
    }

    public boolean addPalletLeft(Pallet pallet, int pos) {
        if (pallets[0][pos] == null) {
            pallets[0][pos] = pallet;
            return true;
        }
        return false;
    }

    public boolean addPalletRight(Pallet pallet, int pos) {
        if (pallets[1][pos] == null) {
            pallets[1][pos] = pallet;
            return true;
        }
        return false;
    }

    public Pallet getNextPallet() {
        if (hasLoad()) {
            Pallet pallet = getNextPalletFromLeft();
            return pallet == null ? getNextPalletFromRight() : pallet;
        }
        return null;
    }

    public Pallet getNextPalletFromLeft() {
        for (int i = pallets[0].length - 1; i >= 0; i--) {
            if (pallets[0][i] != null) {
                Pallet pallet = pallets[0][i];
                pallets[0][i] = null;
                return pallet;
            }
        }
        return null;
    }

    public Pallet getNextPalletFromRight() {
        for (int i = pallets[1].length - 1; i >= 0; i--) {
            if (pallets[1][i] != null) {
                Pallet pallet = pallets[1][i];
                pallets[1][i] = null;
                return pallet;
            }
        }
        return null;
    }

    public int[] getPosition(Pallet pallet) {
        for (int i = 0; i < pallets.length; i++) {
            for (int j = 0; j < pallets[i].length; j++) {
                if (pallets[i][j] == pallet) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{};
    }
}
