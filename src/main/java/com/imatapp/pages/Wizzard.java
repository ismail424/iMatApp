package com.imatapp.pages;


import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import com.imatapp.events.RefreshOrdersEvent;
import com.imatapp.events.SwitchPageEvent;
import com.imatapp.events.SwitchWizzardEvent;

import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingCartListener;


public class Wizzard {
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
    @FXML
    private BorderPane successPane, uppgifterPane, leveransPane, betalningPane, overviewPane;

    @FXML
    private VBox formPane;

    @FXML
    private StackPane stackPane;

    @FXML
    private BorderPane todayDate, tomorrowDate, twoDaysDate;
    @FXML
    private Text todayDateText, tomorrowDateText, twoDaysDateText;

    @FXML
    private HBox stepper;

    @FXML
    private Button uppgifterStep, leveransStep, betalningStep, overviewStep, nextStep, previousStep, stopStep, successButton;

    @FXML
    private Line leveransLine, betalningLine, overviewLine;

    private Button[] allStepsCircle;
    private Line[] allStepsLine;

    @FXML   
    private TextField firstNameField, lastNameField, emailField, addressField, postCodeField, phoneNumberField, nameOnCard, cardNumber, cardMonth, cardYear, cardCVC;

    @FXML
    private Text orderNmr, orderAmount, orderName, orderAdress,orderPrice;

    @FXML
    private StackPane stepPane;

    private BorderPane currentPane;

    @FXML
    public void initialize() {
        currentPane = uppgifterPane;

        allStepsCircle = new Button[]{uppgifterStep, leveransStep, betalningStep, overviewStep};
        allStepsLine = new Line[]{leveransLine, betalningLine, overviewLine};
        fillInfo();
        fillInfoCreditcard();

        stopStep.setOnAction(e -> {
            Event.fireEvent(stopStep, new SwitchWizzardEvent( false ));
            System.out.println("Avbryter köp");
        });
        // Next pane when clicking next
        nextStep.setOnAction(e -> {
            if (currentPane == uppgifterPane) {

                nextStep(leveransPane);
            }
            else if (currentPane == leveransPane) {
                nextStep(betalningPane);
            }
            else if (currentPane == betalningPane) {
                //validatePayment();
                nextStep(overviewPane);
            }
            else if (currentPane == overviewPane) {
                System.out.println("Bekräftar köp");
                confirmOrder();

                System.out.println("Köp bekräftat");
                goToSuccess();
            }
        });

        // Previous pane when clicking previous
        previousStep.setOnAction(e -> {
            if (currentPane == leveransPane) {
                nextStep(uppgifterPane);
            }
            else if (currentPane == betalningPane) {
                nextStep(leveransPane);
            }
            else if (currentPane == overviewPane) {
                nextStep(betalningPane);
            }
        });


        for (Button step : allStepsCircle) {
            step.setOnAction(e -> {
                if (step == uppgifterStep) {
                    nextStep(uppgifterPane);
                }
                else if (step == leveransStep) {
                    nextStep(leveransPane);
                }
                else if (step == betalningStep) {
                    nextStep(betalningPane);
                }
                else if (step == overviewStep) {
                    nextStep(overviewPane);
                }
            });
        }

        firstNameField.textProperty().addListener((observable, oldValue, newValue) -> {
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
        nameOnCard.textProperty().addListener((observable, oldValue, newValue) -> {
            iMatDataHandler.getCreditCard().setHoldersName(newValue);
        });
        cardNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            iMatDataHandler.getCreditCard().setCardNumber(newValue);
        });
        cardMonth.textProperty().addListener((observable, oldValue, newValue) -> {
            iMatDataHandler.getCreditCard().setValidMonth(Integer.parseInt(newValue));
        });
        cardYear.textProperty().addListener((observable, oldValue, newValue) -> {
            iMatDataHandler.getCreditCard().setValidYear(Integer.parseInt(newValue));
        });
        cardCVC.textProperty().addListener((observable, oldValue, newValue) -> {
            iMatDataHandler.getCreditCard().setVerificationCode(Integer.parseInt(newValue));
        });
    
        // set listner for price and amount
        ShoppingCart shoppingCart = iMatDataHandler.getShoppingCart();
        shoppingCart.addShoppingCartListener(new ShoppingCartListener() {
            @Override
            public void shoppingCartChanged(CartEvent cartEvent) {
                orderAmount.setText(String.valueOf(shoppingCart.getItems().size()));
                orderPrice.setText(String.valueOf((int) shoppingCart.getTotal()));
            }
        });

        successButton.setOnAction(e -> {
            Event.fireEvent(successButton, new RefreshOrdersEvent ());
            Event.fireEvent(successButton, new SwitchWizzardEvent( false ));
            Event.fireEvent(successButton, new SwitchPageEvent( "Paket" ));
        });
    }

    private void fillInfoCreditcard(){
        CreditCard creditCard = iMatDataHandler.getCreditCard();
        nameOnCard.setText(creditCard.getHoldersName());
        creditCard.setCardNumber(cardNumber.getText());
        creditCard.setValidMonth(Integer.parseInt(cardMonth.getText()));
        creditCard.setValidYear(Integer.parseInt(cardYear.getText()));
        creditCard.setVerificationCode(Integer.parseInt(cardCVC.getText()));
    }

    private void confirmOrder(){
        iMatDataHandler.placeOrder();

    }

    private void fillInfo(){
        firstNameField.setText(iMatDataHandler.getCustomer().getFirstName());
        lastNameField.setText(iMatDataHandler.getCustomer().getLastName());
        emailField.setText(iMatDataHandler.getCustomer().getEmail());
        addressField.setText(iMatDataHandler.getCustomer().getAddress());
        postCodeField.setText(iMatDataHandler.getCustomer().getPostCode());
        phoneNumberField.setText(iMatDataHandler.getCustomer().getMobilePhoneNumber());

        nameOnCard.setText(iMatDataHandler.getCreditCard().getHoldersName());
        cardNumber.setText(iMatDataHandler.getCreditCard().getCardNumber());
        cardMonth.setText(String.valueOf(iMatDataHandler.getCreditCard().getValidMonth()));
        cardYear.setText(String.valueOf(iMatDataHandler.getCreditCard().getValidYear()));
        cardCVC.setText(String.valueOf(iMatDataHandler.getCreditCard().getVerificationCode()));

        orderNmr.setText("Order nummer: " +String.valueOf(iMatDataHandler.getOrders().size() + 1));
        orderAmount.setText("Antal Varor: " +  String.valueOf(iMatDataHandler.getShoppingCart().getItems().size()));
        orderName.setText( firstNameField.getText() + " " + lastNameField.getText() );
        orderAdress.setText( addressField.getText() + ", " + postCodeField.getText() );
        orderPrice.setText( String.valueOf( (int) iMatDataHandler.getShoppingCart().getTotal() ) + " kr" );
    }


    public void nextStep( BorderPane nextPane ) {

        currentPane.setVisible(false);
        currentPane.toBack();
        nextPane.setVisible(true);
        nextPane.toFront();
        previousStep.setVisible(true);
        // remove active class from all steps
        for (Button step : allStepsCircle) {
            step.getStyleClass().remove("wizzard_stepper_curent");
        }
        for (Line step : allStepsLine) {
            step.getStyleClass().remove("wizzard_stepper_line_current");
        }

        // add active class to current step
        if (nextPane == uppgifterPane) {
            uppgifterStep.getStyleClass().add("wizzard_stepper_curent");
            previousStep.setVisible(false);
        }
        else if (nextPane == leveransPane) {
            uppgifterStep.getStyleClass().add("wizzard_stepper_curent");
            leveransStep.getStyleClass().add("wizzard_stepper_curent");
            leveransLine.getStyleClass().add("wizzard_stepper_line_current");
        }
        else if (nextPane == betalningPane) {
            uppgifterStep.getStyleClass().add("wizzard_stepper_curent");
            leveransStep.getStyleClass().add("wizzard_stepper_curent");
            leveransLine.getStyleClass().add("wizzard_stepper_line_current");
            betalningStep.getStyleClass().add("wizzard_stepper_curent");
            betalningLine.getStyleClass().add("wizzard_stepper_line_current");
        }
        else if (nextPane == overviewPane) {
            uppgifterStep.getStyleClass().add("wizzard_stepper_curent");
            leveransStep.getStyleClass().add("wizzard_stepper_curent");
            leveransLine.getStyleClass().add("wizzard_stepper_line_current");
            betalningStep.getStyleClass().add("wizzard_stepper_curent");
            betalningLine.getStyleClass().add("wizzard_stepper_line_current");
            overviewStep.getStyleClass().add("wizzard_stepper_curent");
            overviewLine.getStyleClass().add("wizzard_stepper_line_current");
        }
        
        currentPane = nextPane;
    }

    private void goToSuccess() {
        formPane.setVisible(false);
        formPane.toBack();
        successPane.setVisible(true);
        successPane.toFront();

    }
    
}
