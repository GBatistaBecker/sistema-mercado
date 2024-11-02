module com.example.sistemamercado {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.slf4j;
    requires com.zaxxer.hikari;


    opens com.example.sistemamercado to javafx.fxml;
    exports com.example.sistemamercado;
    opens com.example.sistemamercado.produto;
    exports com.example.sistemamercado.produto;
}