module hellofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    
    requires java.sql;
    
    opens com.tomas.bankingprogram to javafx.fxml;
    exports com.tomas.bankingprogram;
    exports com.tomas.bankingprogram.controllers;
    opens com.tomas.bankingprogram.controllers to javafx.fxml;
}
