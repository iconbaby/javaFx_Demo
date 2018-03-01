package ch.makery.address.view;

import ch.makery.address.MainApp;
import ch.makery.address.util.DateUtil;
import org.controlsfx.dialog.Dialogs;


import ch.makery.address.model.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PersonOverviewContoller {

    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;
    @FXML
    private Label firstNameLable;
    @FXML
    private Label lastNameLable;
    @FXML
    private Label streetLable;
    @FXML
    private Label postalCodeLable;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private Button btnDelete;

    private MainApp mainApp;

    public PersonOverviewContoller() {

    }

    @FXML
    private void initialize() {
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        showPersonDetail(null);

        personTable.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> showPersonDetail(newValue));
    }

    public void setMainApp(MainApp mianapp) {
        this.mainApp = mianapp;
        personTable.setItems(mainApp.getPersonData());
    }

    private void showPersonDetail(Person person) {
        if (person != null) {
            firstNameLable.setText(person.getFirstName());
            lastNameLable.setText(person.getLastName());
            streetLable.setText(person.getStreet());
            postalCodeLable.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
        } else {
            firstNameLable.setText("");
            lastNameLable.setText("");
            streetLable.setText("");
            postalCodeLable.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
        }
    }

    @FXML
    private void handleDeletePerson() {
        System.out.println("handleDeletePerson");
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        } else {
            Dialogs.create()
                    .title("no Selection")
                    .masthead("no person selected")
                    .message("please select a person in the table")
                    .showWarning();
        }

    }

    @FXML
    private void handleNewPerson() {
        Person tmpPerson = new Person();
        boolean okClicked = mainApp.showPersonEditDialog(tmpPerson);
        if (okClicked) {
            mainApp.getPersonData().add(tmpPerson);
        }
    }

    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okSelected = mainApp.showPersonEditDialog(selectedPerson);
            if (okSelected) {
                showPersonDetail(selectedPerson);

            }
        } else {
            Dialogs.create().title("No Selection").masthead("No Person Selected").message("Please select a person in the table.").showWarning();
        }
    }
}
