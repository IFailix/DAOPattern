package presentationLayer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import businessObjects.ITrainer;
import dataLayer.DataLayerManager;
import dataLayer.businessObjects.Trainer;
import dataLayer.dataAccessObjects.ITrainerDao;
import dataLayer.dataAccessObjects.xml.TrainerDaoXml;
import exceptions.NoNextTrainerFoundException;
import exceptions.NoPreviousTrainerFoundException;
import exceptions.NoTrainerFoundException;
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
    @FXML
    private Button neuButton;

    private ITrainer currentTrainer;

    private ObjectProperty<ViewTrainerViewController> currentEntry = new SimpleObjectProperty<>();
    ITrainerDao db;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	try {
			db = DataLayerManager.getInstance().getDataLayer().getITrainerDao();

		} catch ( Exception e)
		{

		}
        // TODO Auto-generated method stub
        System.out.println(trainerIdSucheField.getText());
        BindEvents();
    }

    private void BindEvents() {
        editMode.disableProperty().bind(currentEntryProperty().isNull().or(editMode.selectedProperty()));
        loeschenButton.disableProperty().bind(currentEntryProperty().isNull().or(editMode.selectedProperty()));
		nextValueButton.disableProperty().bind(currentEntryProperty().isNull());
		prevValueButton.disableProperty().bind(currentEntryProperty().isNull());
        editMode.setOnAction(action -> BindToEntry(currentEntry));
		firstValueButton.setOnAction(action -> {
			try {
				display(db.first().getId(), false);
			} catch (IOException e) {
				System.out.println("Trainer Empty!");
			} catch (NoTrainerFoundException e) {
				System.out.println("No next Trainer!");
			}
		});
		lastValueButton.setOnAction(action -> {
			try {
				display(db.last().getId(), false);
			} catch (IOException e) {
				System.out.println("Trainer Empty!");
			} catch (NoTrainerFoundException e) {
				System.out.println("No next Trainer!");
			}
		});
		nextValueButton.setOnAction(action -> {
			try {
				display(db.next(currentTrainer).getId(), false);
			} catch (IOException e) {
				System.out.println("No next Trainer!");
			} catch (NoNextTrainerFoundException e) {
				e.printStackTrace();
			}
		});
		prevValueButton.setOnAction(action -> {
			try {
				display(db.previous(currentTrainer).getId(), false);
			} catch (IOException e) {
				System.out.println("No next Trainer!");
			} catch (NoPreviousTrainerFoundException e) {
				e.printStackTrace();
			} catch (NoNextTrainerFoundException e) {
				e.printStackTrace();
			}
		});
		neuButton.setOnAction(action -> createNewTrainer());
        trainerIdSucheField.setOnKeyPressed(action -> {
            if (action.getCode() == KeyCode.ENTER) {
                TextField sender = (TextField) action.getSource();
                if (sender != null) {
                    //TODO: Trainer Suchen
                    try {
                        display(Integer.getInteger(sender.getText()),false);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        //TODO: Error displaying trainer
                    }
                }
            }
        });
    }

	private void createNewTrainer() {
		try {
			ITrainer t = db.create();
			display(t.getId(), true);
		} catch (IOException e) {
			System.out.println("Could not create Trainer!");
		}
	}

	private void BindToEntry(ObjectProperty<ViewTrainerViewController> entry) {
        if (entry == null) return;
        entry.get().isEditProperty().addListener((observable, oldValue, newValue) -> editMode.selectedProperty().setValue(newValue));
        entry.get().isEditProperty().bind(editMode.selectedProperty());
    }

    private void display(int id, boolean isNew) throws IOException, IllegalArgumentException {
        //controller.init(new Trainer(1, id, 12, 99));
        //controller.init(new Trainer());
        try {
            ITrainer trainer = db.select(id);
            currentTrainer = trainer;
            showTrainer(currentTrainer, isNew);
		} catch (NoTrainerFoundException e) {
			System.out.printf("Trainer %s not found!", id);
		}
    }
    private void showTrainer(ITrainer trainer, boolean isNew) throws IOException {
		FXMLLoader loader = new FXMLLoader(ViewTrainerViewController.class.getResource("ViewTrainerView.fxml"));
		Node node = loader.load();

		ViewTrainerViewController controller = loader.getController();
		controller.init(trainer, db);
		if(isNew){
			controller.isNewProperty().setValue(true);
		}
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
