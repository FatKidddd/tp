package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePlayerException;
import seedu.address.model.person.exceptions.PlayerNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Player#isSamePlayer(Player)}. As such, adding and updating of
 * persons uses Player#isSamePlayer(Player) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePlayerList. However, the removal of a person uses Player#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Player#isSamePlayer(Player)
 */
public class UniquePlayerList implements Iterable<Player> {

    private final ObservableList<Player> internalList = FXCollections.observableArrayList();
    private final ObservableList<Player> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Player toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePlayer);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Player toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePlayerException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPlayer}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPlayer} must not be the same as another existing person in the list.
     */
    public void setPlayer(Player target, Player editedPlayer) {
        requireAllNonNull(target, editedPlayer);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PlayerNotFoundException();
        }

        if (!target.isSamePlayer(editedPlayer) && contains(editedPlayer)) {
            throw new DuplicatePlayerException();
        }

        internalList.set(index, editedPlayer);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Player toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PlayerNotFoundException();
        }
    }

    public void setPlayers(UniquePlayerList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPlayers(List<Player> persons) {
        requireAllNonNull(persons);
        if (!personsAreUnique(persons)) {
            throw new DuplicatePlayerException();
        }

        internalList.setAll(persons);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Player> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Player> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniquePlayerList)) {
            return false;
        }

        UniquePlayerList otherUniquePlayerList = (UniquePlayerList) other;
        return internalList.equals(otherUniquePlayerList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean personsAreUnique(List<Player> persons) {
        for (int i = 0; i < persons.size() - 1; i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                if (persons.get(i).isSamePlayer(persons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
