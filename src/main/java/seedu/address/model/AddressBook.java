package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Player;
import seedu.address.model.person.UniquePlayerList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePlayer comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePlayerList persons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePlayerList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Players in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPlayers(List<Player> persons) {
        this.persons.setPlayers(persons);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPlayers(newData.getPlayerList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPlayer(Player person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPlayer(Player p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPlayer}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPlayer} must not be the same as another existing person in the address book.
     */
    public void setPlayer(Player target, Player editedPlayer) {
        requireNonNull(editedPlayer);

        persons.setPlayer(target, editedPlayer);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePlayer(Player key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Player> getPlayerList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
