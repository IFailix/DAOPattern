package presentationLayer;

import businessObjects.ITrainer;
import dataLayer.dataAccessObjects.ITrainerDao;
import exceptions.NoTrainerFoundException;
import javafx.beans.property.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ViewTrainerViewController implements Initializable {

    private final BooleanProperty isEdit = new SimpleBooleanProperty(false);
    private final BooleanProperty isNew = new SimpleBooleanProperty(false);

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final IntegerProperty alter = new SimpleIntegerProperty();
    private final IntegerProperty erfahrung = new SimpleIntegerProperty();

    private static final StringConverter<Number> numberStringConverter = getNumberConverter();
    private static final Pattern numberPattern = Pattern.compile("^[0-9]*$");

    private ITrainer backingObject;

    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField alterField;
    @FXML
    private TextField erfahrungField;
    @FXML
    private Button speichernButton;
    @FXML
    private Button abbrechenButton;
    private ITrainerDao db;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBindings();
    }

    private static void onlyPassNumbers(KeyEvent handler) {
        String newChar = handler.getCharacter();
        if (!numberPattern.matcher(newChar).find())
            handler.consume();
    }

    private void setBindings() {
        speichernButton.visibleProperty().bind(isNew.or(isEdit));
        abbrechenButton.visibleProperty().bind(speichernButton.visibleProperty());
        abbrechenButton.setOnAction(action -> {
            isEdit.unbind();
            isEdit.setValue(false);
            ITrainer trainer = backingObject;
            if (isNew.get()){
                removeTrainer(trainer);
            }
            if (trainer != null) fillFrom(trainer);
        });
        speichernButton.setOnAction(action -> {
            ITrainer trainer = backingObject;
            fillTo(trainer);
            persist(trainer);
            fillFrom(trainer);
            isEdit.unbind();
            isEdit.setValue(false);
        });
        idField.setOnKeyTyped(ViewTrainerViewController::onlyPassNumbers);
        alterField.setOnKeyTyped(ViewTrainerViewController::onlyPassNumbers);
        erfahrungField.setOnKeyTyped(ViewTrainerViewController::onlyPassNumbers);

        idField.textProperty().bind(id.asString());
        alterField.textProperty().bindBidirectional(alter, numberStringConverter);
        erfahrungField.textProperty().bindBidirectional(erfahrung, numberStringConverter);
        nameField.textProperty().bindBidirectional(name);
    }

    private void removeTrainer(ITrainer trainer) {
        if (trainer == null) return;

        try {
            db.delete(trainer);
        } catch (NoTrainerFoundException e) {
            System.out.printf("Could not remove trainer %s", trainer.getId());
        }
    }

    private static StringConverter<Number> getNumberConverter() {
        return new StringConverter<Number>() {
            @Override
            public String toString(Number number) {
                return number.toString();
            }

            @Override
            public Number fromString(String string) {
                return (string.isEmpty() || string == "") ? 0: Integer.parseInt(string);
            }
        };
    }

    private void fillTo(ITrainer trainer) {
        trainer.setAlter(alter.get());
        trainer.setName(name.get());
        trainer.setErfahrung(erfahrung.get());
    }

    private boolean persist(ITrainer trainer) {
        //TODO: Persist changes
        try {
            db.save(trainer);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public void init(ITrainer trainer, ITrainerDao db) throws IllegalArgumentException {
        if (trainer == null) throw new IllegalArgumentException("Parameter 'trainer' can not be null!");
        fillFrom(trainer);
        this.db = db;
        backingObject = trainer;
    }

    private void fillFrom(ITrainer trainer) {
        id.set(trainer.getId());
        name.set(trainer.getName());
        alter.set(trainer.getAlter());
        erfahrung.set(trainer.getErfahrung());
    }

    public final boolean getIsEdit() {
        return this.isEdit.get();
    }

    public final void setIsEdit(boolean value) {
        this.isEdit.set(value);
    }

    public final BooleanProperty isEditProperty() {
        return this.isEdit;
    }

    public boolean isIsNew() {
        return isNew.get();
    }

    public BooleanProperty isNewProperty() {
        return isNew;
    }
}
