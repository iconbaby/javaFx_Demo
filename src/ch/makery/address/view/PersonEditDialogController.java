package ch.makery.address.view;

import ch.makery.address.util.DateUtil;
import org.controlsfx.dialog.Dialogs;



import ch.makery.address.model.Person;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PersonEditDialogController {
	@FXML
	private TextField firstNameFiled;
	@FXML
	private TextField lastNameFiled;
	@FXML
	private TextField cityFiled;
	@FXML
	private TextField streetFiled;
	@FXML
	private TextField postalCodeFiled;
	@FXML
	private TextField birthdayFiled;

	private Stage dialogStage;
	private Person person;
	private boolean okClicked;

	@FXML
	private void Initialize() {

	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setPerson(Person person) {
		this.person = person;
		firstNameFiled.setText(person.getFirstName());
		lastNameFiled.setText(person.getLastName());
		streetFiled.setText(person.getStreet());
		postalCodeFiled.setText(Integer.toString(person.getPostalCode()));
		cityFiled.setText(person.getCity());
		birthdayFiled.setText(DateUtil.format(person.getBirthday()));
		birthdayFiled.setPromptText("dd.mm.yyyy");

	}

	public boolean isOkChecked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {
		if (isInputValid()) {
			person.setFirstName(firstNameFiled.getText());
			person.setLastName(lastNameFiled.getText());
			person.setStreet(streetFiled.getText());
			person.setPostalCode(Integer.parseInt(postalCodeFiled.getText()));
			person.setCity(cityFiled.getText());
			person.setBirthday(DateUtil.parse(birthdayFiled.getText()));
			okClicked = true;
			dialogStage.close();

		}
	}
	@FXML
	private void handleCancle(){
		dialogStage.close();
	}

	private boolean isInputValid() {
		String errorMessage = "";

        if (firstNameFiled.getText() == null || firstNameFiled.getText().length() == 0) {
            errorMessage += "No valid first name!\n";
        }
        if (lastNameFiled.getText() == null || lastNameFiled.getText().length() == 0) {
            errorMessage += "No valid last name!\n";
        }
        if (streetFiled.getText() == null || streetFiled.getText().length() == 0) {
            errorMessage += "No valid street!\n";
        }

        if (postalCodeFiled.getText() == null || postalCodeFiled.getText().length() == 0) {
            errorMessage += "No valid postal code!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(postalCodeFiled.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid postal code (must be an integer)!\n";
            }
        }

        if (cityFiled.getText() == null || cityFiled.getText().length() == 0) {
            errorMessage += "No valid city!\n";
        }

        if (birthdayFiled.getText() == null || birthdayFiled.getText().length() == 0) {
            errorMessage += "No valid birthday!\n";
        } else {
            if (!DateUtil.validDate(birthdayFiled.getText())) {
                errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Dialogs.create()
                .title("Invalid Fields")
                .masthead("Please correct invalid fields")
                .message(errorMessage)
                .showError();
            return false;
        }
	}
}
