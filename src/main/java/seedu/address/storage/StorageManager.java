package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlySummonersBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of SummonersBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private SummonersBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code SummonersBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(SummonersBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage) {
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ SummonersBook methods ==============================

    @Override
    public Path getSummonersBookFilePath() {
        return addressBookStorage.getSummonersBookFilePath();
    }

    @Override
    public Optional<ReadOnlySummonersBook> readSummonersBook() throws DataLoadingException {
        return readSummonersBook(addressBookStorage.getSummonersBookFilePath());
    }

    @Override
    public Optional<ReadOnlySummonersBook> readSummonersBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readSummonersBook(filePath);
    }

    @Override
    public void saveSummonersBook(ReadOnlySummonersBook addressBook) throws IOException {
        saveSummonersBook(addressBook, addressBookStorage.getSummonersBookFilePath());
    }

    @Override
    public void saveSummonersBook(ReadOnlySummonersBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveSummonersBook(addressBook, filePath);
    }

}
