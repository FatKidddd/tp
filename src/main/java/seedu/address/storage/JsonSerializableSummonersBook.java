package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.SummonersBook;
import seedu.address.model.ReadOnlySummonersBook;
import seedu.address.model.player.Player;

/**
 * An Immutable SummonersBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableSummonersBook {

    public static final String MESSAGE_DUPLICATE_player = "Players list contains duplicate player(s).";

    private final List<JsonAdaptedPlayer> players = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableSummonersBook} with the given players.
     */
    @JsonCreator
    public JsonSerializableSummonersBook(@JsonProperty("players") List<JsonAdaptedPlayer> players) {
        this.players.addAll(players);
    }

    /**
     * Converts a given {@code ReadOnlySummonersBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableSummonersBook}.
     */
    public JsonSerializableSummonersBook(ReadOnlySummonersBook source) {
        players.addAll(source.getPlayerList().stream().map(JsonAdaptedPlayer::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code SummonersBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public SummonersBook toModelType() throws IllegalValueException {
        SummonersBook addressBook = new SummonersBook();
        for (JsonAdaptedPlayer jsonAdaptedPlayer : players) {
            Player player = jsonAdaptedPlayer.toModelType();
            if (addressBook.hasPlayer(player)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_player);
            }
            addressBook.addPlayer(player);
        }
        return addressBook;
    }

}
