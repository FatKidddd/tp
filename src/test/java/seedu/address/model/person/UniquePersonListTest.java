package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPlayers.ALICE;
import static seedu.address.testutil.TypicalPlayers.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicatePlayerException;
import seedu.address.model.person.exceptions.PlayerNotFoundException;
import seedu.address.testutil.PlayerBuilder;

public class UniquePlayerListTest {

    private final UniquePlayerList uniquePlayerList = new UniquePlayerList();

    @Test
    public void contains_nullPlayer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePlayerList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniquePlayerList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniquePlayerList.add(ALICE);
        assertTrue(uniquePlayerList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniquePlayerList.add(ALICE);
        Player editedAlice = new PlayerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniquePlayerList.contains(editedAlice));
    }

    @Test
    public void add_nullPlayer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePlayerList.add(null));
    }

    @Test
    public void add_duplicatePlayer_throwsDuplicatePlayerException() {
        uniquePlayerList.add(ALICE);
        assertThrows(DuplicatePlayerException.class, () -> uniquePlayerList.add(ALICE));
    }

    @Test
    public void setPlayer_nullTargetPlayer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePlayerList.setPlayer(null, ALICE));
    }

    @Test
    public void setPlayer_nullEditedPlayer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePlayerList.setPlayer(ALICE, null));
    }

    @Test
    public void setPlayer_targetPlayerNotInList_throwsPlayerNotFoundException() {
        assertThrows(PlayerNotFoundException.class, () -> uniquePlayerList.setPlayer(ALICE, ALICE));
    }

    @Test
    public void setPlayer_editedPlayerIsSamePlayer_success() {
        uniquePlayerList.add(ALICE);
        uniquePlayerList.setPlayer(ALICE, ALICE);
        UniquePlayerList expectedUniquePlayerList = new UniquePlayerList();
        expectedUniquePlayerList.add(ALICE);
        assertEquals(expectedUniquePlayerList, uniquePlayerList);
    }

    @Test
    public void setPlayer_editedPlayerHasSameIdentity_success() {
        uniquePlayerList.add(ALICE);
        Player editedAlice = new PlayerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniquePlayerList.setPlayer(ALICE, editedAlice);
        UniquePlayerList expectedUniquePlayerList = new UniquePlayerList();
        expectedUniquePlayerList.add(editedAlice);
        assertEquals(expectedUniquePlayerList, uniquePlayerList);
    }

    @Test
    public void setPlayer_editedPlayerHasDifferentIdentity_success() {
        uniquePlayerList.add(ALICE);
        uniquePlayerList.setPlayer(ALICE, BOB);
        UniquePlayerList expectedUniquePlayerList = new UniquePlayerList();
        expectedUniquePlayerList.add(BOB);
        assertEquals(expectedUniquePlayerList, uniquePlayerList);
    }

    @Test
    public void setPlayer_editedPlayerHasNonUniqueIdentity_throwsDuplicatePlayerException() {
        uniquePlayerList.add(ALICE);
        uniquePlayerList.add(BOB);
        assertThrows(DuplicatePlayerException.class, () -> uniquePlayerList.setPlayer(ALICE, BOB));
    }

    @Test
    public void remove_nullPlayer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePlayerList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPlayerNotFoundException() {
        assertThrows(PlayerNotFoundException.class, () -> uniquePlayerList.remove(ALICE));
    }

    @Test
    public void remove_existingPlayer_removesPlayer() {
        uniquePlayerList.add(ALICE);
        uniquePlayerList.remove(ALICE);
        UniquePlayerList expectedUniquePlayerList = new UniquePlayerList();
        assertEquals(expectedUniquePlayerList, uniquePlayerList);
    }

    @Test
    public void setPlayers_nullUniquePlayerList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePlayerList.setPlayers((UniquePlayerList) null));
    }

    @Test
    public void setPlayers_uniquePlayerList_replacesOwnListWithProvidedUniquePlayerList() {
        uniquePlayerList.add(ALICE);
        UniquePlayerList expectedUniquePlayerList = new UniquePlayerList();
        expectedUniquePlayerList.add(BOB);
        uniquePlayerList.setPlayers(expectedUniquePlayerList);
        assertEquals(expectedUniquePlayerList, uniquePlayerList);
    }

    @Test
    public void setPlayers_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePlayerList.setPlayers((List<Player>) null));
    }

    @Test
    public void setPlayers_list_replacesOwnListWithProvidedList() {
        uniquePlayerList.add(ALICE);
        List<Player> personList = Collections.singletonList(BOB);
        uniquePlayerList.setPlayers(personList);
        UniquePlayerList expectedUniquePlayerList = new UniquePlayerList();
        expectedUniquePlayerList.add(BOB);
        assertEquals(expectedUniquePlayerList, uniquePlayerList);
    }

    @Test
    public void setPlayers_listWithDuplicatePlayers_throwsDuplicatePlayerException() {
        List<Player> listWithDuplicatePlayers = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatePlayerException.class, () -> uniquePlayerList.setPlayers(listWithDuplicatePlayers));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniquePlayerList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniquePlayerList.asUnmodifiableObservableList().toString(), uniquePlayerList.toString());
    }
}
