package seedu.address.testutil;

import seedu.address.model.SummonersBook;
import seedu.address.model.player.Player;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code SummonersBook ab = new SummonersBookBuilder().withPlayer("John", "Doe").build();}
 */
public class SummonersBookBuilder {

    private SummonersBook summonersBook;

    public SummonersBookBuilder() {
        summonersBook = new SummonersBook();
    }

    public SummonersBookBuilder(SummonersBook summonersBook) {
        this.summonersBook = summonersBook;
    }

    /**
     * Adds a new {@code Player} to the {@code SummonersBook} that we are building.
     */
    public SummonersBookBuilder withPlayer(Player player) {
        summonersBook.addPlayer(player);
        return this;
    }

    public SummonersBook build() {
        return summonersBook;
    }
}
