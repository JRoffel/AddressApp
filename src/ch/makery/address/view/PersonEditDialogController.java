package ch.makery.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;

public class PersonEditDialogController {
	
	@FXML
	private TextField firstNameField = new TextField();
	@FXML
	private TextField lastNameField = new TextField();
	@FXML
	private TextField streetField = new TextField();
	@FXML
	private TextField postalCodeField = new TextField();
	@FXML
	private TextField cityField = new TextField();
	@FXML
	private TextField birthdayField = new TextField();
	
	private Stage dialogStage;
	private Person person = new Person();
	private boolean okClicked = false;
	
	
	@FXML
	private void initialize() {
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setPerson(Person person) {
		this.person = person;
		firstNameField.setText(person.getFirstName());
		lastNameField.setText(person.getLastName());
		streetField.setText(person.getStreet());
		postalCodeField.setText(Integer.toString(person.getPostalCode()));
		cityField.setText(person.getCity());
		birthdayField.setText(DateUtil.format(person.getBirthday()));
		birthdayField.setPromptText("dd.mm.yyyy");
	}
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			person.setFirstName(firstNameField.getText());
			person.setLastName(lastNameField.getText());
			person.setStreet(streetField.getText());
			person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
			person.setCity(cityField.getText());
			person.setBirthday(DateUtil.parse(birthdayField.getText()));
			okClicked = true;
			dialogStage.close();
		}
	}
	
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
	
	private boolean isInputValid() {
		String errorMessage = "";
		
		if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
			errorMessage += "No valid first name!\n";
		}
		if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
			errorMessage += "No valid last name!\n";
		}
		if (streetField.getText() == null || streetField.getText().length() == 0) {
			errorMessage += "No valid street!\n";
		}
		
		if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0){
			errorMessage += "No valid postal code!\n";
		}else {
			try {
				Integer.parseInt(postalCodeField.getText());
			}catch (NumberFormatException e) {
				errorMessage += "No valid postal code (Must be an integer)!\n";
			}
		}
		
		if (cityField.getText() == null || cityField.getText().length() == 0){
			errorMessage += "No valid city!\n";
		}
		
		if (birthdayField.getText() == null || cityField.getText().length() == 0) {
			errorMessage += "No valid birthday!\n";
		}else {
			if (!DateUtil.validDate(birthdayField.getText())) {
				errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";
			}
		}
		
		if (errorMessage.length() == 0) {
			return true;
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(this.dialogStage);
			alert.setTitle("Invalid Fields!");
			alert.setHeaderText("Please Correct Invalid Fields");
			alert.setContentText(errorMessage);
			
			alert.showAndWait();
			
			return false;
		}
	}
}
