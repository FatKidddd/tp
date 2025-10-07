package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.player.Player;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final SummonersBook summonersBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Player> filteredPlayers;

    /**
     * Initializes a ModelManager with the given summonersBook and userPrefs.
     */
    public ModelManager(ReadOnlySummonersBook summonersBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(summonersBook, userPrefs);

        logger.fine("Initializing with address book: " + summonersBook + " and user prefs " + userPrefs);

        this.summonersBook = new SummonersBook(summonersBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPlayers = new FilteredList<>(this.summonersBook.getPlayerList());
    }

    public ModelManager() {
        this(new SummonersBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getSummonersBookFilePath() {
        return userPrefs.getSummonersBookFilePath();
    }

    @Override
    public void setSummonersBookFilePath(Path summonersBookFilePath) {
        requireNonNull(summonersBookFilePath);
        userPrefs.setSummonersBookFilePath(summonersBookFilePath);
    }

    //=========== SummonersBook ================================================================================

    @Override
    public void setSummonersBook(ReadOnlySummonersBook summonersBook) {
        this.summonersBook.resetData(summonersBook);
    }

    @Override
    public ReadOnlySummonersBook getSummonersBook() {
        return summonersBook;
    }

    @Override
    public boolean hasPlayer(Player player) {
        requireNonNull(player);
        return summonersBook.hasPlayer(player);
    }

    @Override
    public void deletePlayer(Player target) {
        summonersBook.removePlayer(target);
    }

    @Override
    public void addPlayer(Player player) {
        summonersBook.addPlayer(player);
        updateFilteredPlayerList(PREDICATE_SHOW_ALL_PLAYERS);
    }

    @Override
    public void setPlayer(Player target, Player editedPlayer) {
        requireAllNonNull(target, editedPlayer);

        summonersBook.setPlayer(target, editedPlayer);
    }

    //=========== Filtered Player List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Player} backed by the internal list of
     * {@code versionedSummonersBook}
     */
    @Override
    public ObservableList<Player> getFilteredPlayerList() {
        return filteredPlayers;
    }

    @Override
    public void updateFilteredPlayerList(Predicate<Player> predicate) {
        requireNonNull(predicate);
        filteredPlayers.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return summonersBook.equals(otherModelManager.summonersBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPlayers.equals(otherModelManager.filteredPlayers);
    }

}
