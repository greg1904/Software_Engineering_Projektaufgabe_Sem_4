package team16.storage.room.interim;

import team16.configuration.Configuration;
import team16.storage.pallet.Pallet;

import java.util.Arrays;

public class InterimPalletStorage {
    private final Position[] positions = new Position[Configuration.instance.interimPositionsMax];

    public InterimPalletStorage() {
        Arrays.setAll(positions, i -> new Position());
    }

    public boolean addPallet(Pallet pallet) {
        if (Arrays.stream(positions).noneMatch(Position::hasRoom)) {
            return false;
        }
        return Arrays.stream(positions)
                .filter(Position::hasRoom)
                .findFirst()
                .orElseThrow()
                .addPallet(pallet);
    }

    public boolean hasPallets() {
        return Arrays.stream(positions).anyMatch(Position::hasPallets);
    }

    public Pallet removePallet() {
        if (!hasPallets()) {
            return null;
        }
        return Arrays.stream(positions)
                .filter(Position::hasPallets)
                .findFirst()
                .orElseThrow()
                .removePallet();
    }
}
