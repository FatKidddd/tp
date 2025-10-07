package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPlayerAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_player;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_player;
import static seedu.address.testutil.TypicalPlayers.getTypicalSummonersBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.player.Player;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalSummonersBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Player playerToDelete = model.getFilteredPlayerList().get(INDEX_FIRST_player.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_player);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_player_SUCCESS,
                Messages.format(playerToDelete));

        ModelManager expectedModel = new ModelManager(model.getSummonersBook(), new UserPrefs());
        expectedModel.deletePlayer(playerToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPlayerList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_player_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPlayerAtIndex(model, INDEX_FIRST_player);

        Player playerToDelete = model.getFilteredPlayerList().get(INDEX_FIRST_player.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_player);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_player_SUCCESS,
                Messages.format(playerToDelete));

        Model expectedModel = new ModelManager(model.getSummonersBook(), new UserPrefs());
        expectedModel.deletePlayer(playerToDelete);
        showNoPlayer(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPlayerAtIndex(model, INDEX_FIRST_player);

        Index outOfBoundIndex = INDEX_SECOND_player;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getSummonersBook().getPlayerList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_player_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_player);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_player);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_player);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different player -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPlayer(Model model) {
        model.updateFilteredPlayerList(p -> false);

        assertTrue(model.getFilteredPlayerList().isEmpty());
    }
}
