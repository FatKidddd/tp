package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlySummonersBook;

/**
 * A class to access SummonersBook data stored as a json file on the hard disk.
 */
public class JsonSummonersBookStorage implements SummonersBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonSummonersBookStorage.class);

    private Path filePath;

    public JsonSummonersBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getSummonersBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlySummonersBook> readSummonersBook() throws DataLoadingException {
        return readSummonersBook(filePath);
    }

    /**
     * Similar to {@link #readSummonersBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlySummonersBook> readSummonersBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableSummonersBook> jsonSummonersBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableSummonersBook.class);
        if (!jsonSummonersBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonSummonersBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveSummonersBook(ReadOnlySummonersBook addressBook) throws IOException {
        saveSummonersBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveSummonersBook(ReadOnlySummonersBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveSummonersBook(ReadOnlySummonersBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableSummonersBook(addressBook), filePath);
    }

}
