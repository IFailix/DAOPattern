package presentationLayer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dataLayer.businessObjects.Trainer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class MainViewController implements Initializable {

    @FXML
    private TextField trainerIdSucheField;
    @FXML
    private Button firstValueButton;
    @FXML
    private Button prevValueButton;
    @FXML
    private Button nextValueButton;
    @FXML
    private Button lastValueButton;
    @FXML
    private Pane trainerView;
    @FXML
    private ToggleButton editMode;
    @FXML
    private Button loeschenButton;

    private ObjectProperty<ViewTrainerViewController> currentEntry = new SimpleObjectProperty<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        System.out.println(trainerIdSucheField.getText());
        BindEvents();
    }

    private void BindEvents() {
        editMode.disableProperty().bind(currentEntryProperty().isNull().or(editMode.selectedProperty()));
        loeschenButton.disableProperty().bind(currentEntryProperty().isNull().or(editMode.selectedProperty()));
        editMode.setOnAction(action -> BindToEntry(currentEntry));
        trainerIdSucheField.setOnKeyPressed(action -> {
            if (action.getCode() == KeyCode.ENTER) {
                TextField sender = (TextField) action.getSource();
                if (sender != null) {
                    //TODO: Trainer Suchen
                    try {
                        display(sender.getText());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        //TODO: Error displaying trainer
                    }
                }
            }
        });
    }

    private void BindToEntry(ObjectProperty<ViewTrainerViewController> entry) {
        if (entry == null) return;
        entry.get().isEditProperty().addListener((observable, oldValue, newValue) -> editMode.selectedProperty().setValue(newValue));
        entry.get().isEditProperty().bind(editMode.selectedProperty());
    }

    private void display(String id) throws IOException, IllegalArgumentException {
        FXMLLoader loader = new FXMLLoader(ViewTrainerViewController.class.getResource("ViewTrainerView.fxml"));
        Node node = loader.load();
        ViewTrainerViewController controller = loader.getController();
        //controller.init(new Trainer(1, id, 12, 99));
        //controller.init(new Trainer());

        trainerView.getChildren().clear();
        currentEntry.set(controller);
        trainerView.getChildren().add(node);
    }

    public final ViewTrainerViewController getCurrentEntry() {
        return currentEntry.get();
    }

    public final ObjectProperty<ViewTrainerViewController> currentEntryProperty() {
        return currentEntry;
    }

}
