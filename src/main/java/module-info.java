module com.imatapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    opens com.imatapp to javafx.fxml;
    exports com.imatapp;
}