package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPlayers.getTypicalSummonersBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.SummonersBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptySummonersBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptySummonersBook_success() {
        Model model = new ModelManager(getTypicalSummonersBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalSummonersBook(), new UserPrefs());
        expectedModel.setSummonersBook(new SummonersBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
