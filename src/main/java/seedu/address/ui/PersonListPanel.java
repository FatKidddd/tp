package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Player;

/**
 * Panel containing the list of persons.
 */
public class PlayerListPanel extends UiPart<Region> {
    private static final String FXML = "PlayerListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PlayerListPanel.class);

    @FXML
    private ListView<Player> personListView;

    /**
     * Creates a {@code PlayerListPanel} with the given {@code ObservableList}.
     */
    public PlayerListPanel(ObservableList<Player> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PlayerListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Player} using a {@code PlayerCard}.
     */
    class PlayerListViewCell extends ListCell<Player> {
        @Override
        protected void updateItem(Player person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PlayerCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
