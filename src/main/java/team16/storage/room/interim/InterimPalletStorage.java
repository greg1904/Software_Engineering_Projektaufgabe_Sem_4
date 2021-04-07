package team16.storage.room.interim;

import team16.configuration.Configuration;
import team16.storage.pallet.Pallet;

import java.util.Arrays;
import java.util.Objects;

public class InterimPalletStorage {

    private final Position[] positions = new Position[Configuration.INSTANCE.interimPositionsMax];

    public InterimPalletStorage() {
        Arrays.setAll(positions, i -> new Position());
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean addPallet(Pallet pallet) {
        return Objects.requireNonNull(Arrays.stream(positions)
                .filter(Position::hasRoom)
                .findFirst()
                .orElse(null))
                .addPallet(pallet);
    }

    public boolean hasPallets() {
        return Arrays.stream(positions).anyMatch(Position::hasPallets);
    }

    public Pallet removePallet() {
        return Objects.requireNonNull(Arrays.stream(positions)
                .filter(Position::hasPallets)
                .findFirst()
                .orElse(null))
                .removePallet();
    }
}
