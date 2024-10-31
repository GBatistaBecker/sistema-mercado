module com.example.sistemamercado {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.sistemamercado to javafx.fxml;
    exports com.example.sistemamercado;
}