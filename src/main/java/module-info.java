module com.example.sistemamercado {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.slf4j;
    requires com.zaxxer.hikari;
    requires mysql.connector.j;

    opens com.example.sistemamercado to javafx.fxml;
    opens com.example.sistemamercado.produto to javafx.fxml;
    opens com.example.sistemamercado.pedido to javafx.fxml;
    opens com.example.sistemamercado.popup to javafx.fxml;
    exports com.example.sistemamercado;
    exports com.example.sistemamercado.pedido;
    exports com.example.sistemamercado.produto;
    exports com.example.sistemamercado.popup;
}