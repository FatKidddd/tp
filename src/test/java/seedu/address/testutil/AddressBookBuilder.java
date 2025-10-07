package seedu.address.testutil;

import seedu.address.model.SummonersBook;
import seedu.address.model.player.Player;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code SummonersBook ab = new SummonersBookBuilder().withPlayer("John", "Doe").build();}
 */
public class SummonersBookBuilder {

    private SummonersBook addressBook;

    public SummonersBookBuilder() {
        addressBook = new SummonersBook();
    }

    public SummonersBookBuilder(SummonersBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Player} to the {@code SummonersBook} that we are building.
     */
    public SummonersBookBuilder withPlayer(Player player) {
        addressBook.addPlayer(player);
        return this;
    }

    public SummonersBook build() {
        return addressBook;
    }
}
