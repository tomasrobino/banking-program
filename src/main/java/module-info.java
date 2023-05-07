module hellofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    
    requires java.sql;
    
    opens com.tomas.bankingprogram to javafx.fxml;
    exports com.tomas.bankingprogram;
}
