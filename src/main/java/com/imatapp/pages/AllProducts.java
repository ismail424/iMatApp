package com.imatapp.pages;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ProductCategory;

public class AllProducts {

    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    @FXML
    private Label berry, bread, cabbage, citrus_fruit, cold_drinks, dairies, exotic_fruit, fish, flour_sugar_salt, fruit, herb, hot_drinks, meat, melons, nuts_and_seeds, pasta, pod, potato_rice, root_vegetable, sweet, vegetable_fruit;


    @FXML
    public void initialize(){

        

    }

}
