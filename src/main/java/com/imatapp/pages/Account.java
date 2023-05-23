package com.imatapp.pages;

import com.imatapp.events.SwitchPageEvent;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import se.chalmers.cse.dat216.project.IMatDataHandler;

public class Account {
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    @FXML
    private TextField nameField, lastNameField, emailField, addressField, postCodeField, phoneNumberField;

    @FXML
    private Button historyButton;

    @FXML
    public void initialize() {
        nameField.setText(iMatDataHandler.getCustomer().getFirstName());
        lastNameField.setText(iMatDataHandler.getCustomer().getLastName());
        emailField.setText(iMatDataHandler.getCustomer().getEmail());
        addressField.setText(iMatDataHandler.getCustomer().getAddress());
        postCodeField.setText(iMatDataHandler.getCustomer().getPostCode());
        phoneNumberField.setText(iMatDataHandler.getCustomer().getMobilePhoneNumber());

        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            iMatDataHandler.getCustomer().setFirstName(newValue);
        });
        
        lastNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            iMatDataHandler.getCustomer().setLastName(newValue);
        });

        emailField.textProperty().addListener((observable, oldValue, newValue) -> {
            iMatDataHandler.getCustomer().setEmail(newValue);
        });

        addressField.textProperty().addListener((observable, oldValue, newValue) -> {
            iMatDataHandler.getCustomer().setAddress(newValue);
        });

        postCodeField.textProperty().addListener((observable, oldValue, newValue) -> {
            iMatDataHandler.getCustomer().setPostCode(newValue);
        });

        phoneNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            iMatDataHandler.getCustomer().setMobilePhoneNumber(newValue);
        });

        historyButton.setOnAction(e -> {
            Event.fireEvent(historyButton, new SwitchPageEvent("History"));
        });
    }
}
