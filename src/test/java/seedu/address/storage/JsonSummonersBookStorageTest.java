package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPlayers.ALICE;
import static seedu.address.testutil.TypicalPlayers.HOON;
import static seedu.address.testutil.TypicalPlayers.IDA;
import static seedu.address.testutil.TypicalPlayers.getTypicalSummonersBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlySummonersBook;
import seedu.address.model.SummonersBook;

public class JsonSummonersBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSummonersBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readSummonersBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readSummonersBook(null));
    }

    private java.util.Optional<ReadOnlySummonersBook> readSummonersBook(String filePath) throws Exception {
        return new JsonSummonersBookStorage(Paths.get(filePath))
            .readSummonersBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readSummonersBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readSummonersBook("notJsonFormatSummonersBook.json"));
    }

    @Test
    public void readSummonersBook_invalidPlayerSummonersBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readSummonersBook("invalidPlayerSummonersBook.json"));
    }

    @Test
    public void readSummonersBook_invalidAndValidPlayerSummonersBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readSummonersBook("invalidAndValidPlayerSummonersBook.json"));
    }

    @Test
    public void readAndSaveSummonersBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempSummonersBook.json");
        SummonersBook original = getTypicalSummonersBook();
        JsonSummonersBookStorage jsonSummonersBookStorage = new JsonSummonersBookStorage(filePath);

        // Save in new file and read back
        jsonSummonersBookStorage.saveSummonersBook(original, filePath);
        ReadOnlySummonersBook readBack = jsonSummonersBookStorage.readSummonersBook(filePath).get();
        assertEquals(original, new SummonersBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPlayer(HOON);
        original.removePlayer(ALICE);
        jsonSummonersBookStorage.saveSummonersBook(original, filePath);
        readBack = jsonSummonersBookStorage.readSummonersBook(filePath).get();
        assertEquals(original, new SummonersBook(readBack));

        // Save and read without specifying file path
        original.addPlayer(IDA);
        jsonSummonersBookStorage.saveSummonersBook(original); // file path not specified
        readBack = jsonSummonersBookStorage.readSummonersBook().get(); // file path not specified
        assertEquals(original, new SummonersBook(readBack));

    }

    @Test
    public void saveSummonersBook_nullSummonersBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveSummonersBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code summonersBook} at the specified {@code filePath}.
     */
    private void saveSummonersBook(ReadOnlySummonersBook summonersBook, String filePath) {
        try {
            new JsonSummonersBookStorage(Paths.get(filePath))
                    .saveSummonersBook(summonersBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveSummonersBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveSummonersBook(new SummonersBook(), null));
    }
}
