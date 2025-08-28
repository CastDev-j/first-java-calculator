module com.manual {
    requires transitive javafx.controls;
    requires javafx.fxml;

    opens com.manual to javafx.fxml;
    exports com.manual;
}
