module com.imatapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires projectbackend;
    
    opens com.imatapp to javafx.fxml;
    exports com.imatapp;
    
    opens com.imatapp.pages to javafx.fxml;
    exports com.imatapp.pages;

    opens com.imatapp.components to javafx.fxml;
    exports com.imatapp.components;

    opens com.imatapp.events to javafx.fxml;
    exports com.imatapp.events;
} 