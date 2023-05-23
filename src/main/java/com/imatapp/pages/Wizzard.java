package com.imatapp.pages;


import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import com.imatapp.events.SwitchWizzardEvent;
import se.chalmers.cse.dat216.project.IMatDataHandler;


public class Wizzard {
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
    @FXML
    private BorderPane successPane, uppgifterPane, leveransPane, betalningPane, overviewPane;

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
                nextStep(overviewPane);
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
    
}
