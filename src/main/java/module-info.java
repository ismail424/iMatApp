module com.imatapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.imatapp to javafx.fxml;
    exports com.imatapp;
    
    opens com.imatapp.pages to javafx.fxml;
    exports com.imatapp.pages;
}