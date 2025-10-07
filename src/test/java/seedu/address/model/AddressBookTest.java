package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPlayers.ALICE;
import static seedu.address.testutil.TypicalPlayers.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Player;
import seedu.address.model.person.exceptions.DuplicatePlayerException;
import seedu.address.testutil.PlayerBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPlayerList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePlayers_throwsDuplicatePlayerException() {
        // Two persons with the same identity fields
        Player editedAlice = new PlayerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Player> newPlayers = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPlayers);

        assertThrows(DuplicatePlayerException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPlayer_nullPlayer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPlayer(null));
    }

    @Test
    public void hasPlayer_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPlayer(ALICE));
    }

    @Test
    public void hasPlayer_personInAddressBook_returnsTrue() {
        addressBook.addPlayer(ALICE);
        assertTrue(addressBook.hasPlayer(ALICE));
    }

    @Test
    public void hasPlayer_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPlayer(ALICE);
        Player editedAlice = new PlayerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPlayer(editedAlice));
    }

    @Test
    public void getPlayerList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPlayerList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{persons=" + addressBook.getPlayerList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Player> persons = FXCollections.observableArrayList();

        AddressBookStub(Collection<Player> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Player> getPlayerList() {
            return persons;
        }
    }

}
