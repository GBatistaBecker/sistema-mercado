module com.example.sistemamercado {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sistemamercado to javafx.fxml;
    exports com.example.sistemamercado;
}