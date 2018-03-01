package ch.makery.address;

import java.io.IOException;

import ch.makery.address.model.Person;
import ch.makery.address.view.PersonEditDialogController;
import ch.makery.address.view.PersonOverviewContoller;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {
	private Stage primaryStage;
	private BorderPane rootlayout;
	private ObservableList<Person> personData = FXCollections.observableArrayList();

	public MainApp() {
		// Add some sample data
		personData.add(new Person("Hans", "Muster"));
		personData.add(new Person("Ruth", "Mueller"));
		personData.add(new Person("Heinz", "Kurz"));
		personData.add(new Person("Cornelia", "Meier"));
		personData.add(new Person("Werner", "Meyer"));
		personData.add(new Person("Lydia", "Kunz"));
		personData.add(new Person("Anna", "Best"));
		personData.add(new Person("Stefan", "Meier"));
		personData.add(new Person("Martin", "Mueller"));
	}

	public ObservableList<Person> getPersonData() {
		return personData;
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("AddressApp");

		initRootLayout();
		showPersonOverview();
	}

	private void showPersonOverview() {
		// TODO Auto-generated method stub
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
		try {
			AnchorPane anchorPane = fxmlLoader.load();
			rootlayout.setCenter(anchorPane);

			PersonOverviewContoller controll = fxmlLoader.getController();
			controll.setMainApp(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean showPersonEditDialog(Person person){
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
		try {
			AnchorPane page = fxmlLoader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Person");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			PersonEditDialogController controller = fxmlLoader.getController();
			controller.setDialogStage(dialogStage);
			controller.setPerson(person);
			dialogStage.showAndWait();

			return controller.isOkChecked();
		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	private void initRootLayout() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
		try {
			rootlayout = (BorderPane) loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Scene scene = new Scene(rootlayout);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
