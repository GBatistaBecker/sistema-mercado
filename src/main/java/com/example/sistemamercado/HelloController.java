package com.example.sistemamercado;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ProductController {
    @FXML private TextField searchField;
    @FXML private ComboBox<String> preferenceComboBox;
    @FXML private TableView<Produtos> productTable;
    @FXML private Label cartCountLabel;
    @FXML private Button deliveryButton;

    private ObservableList<Produtos> cart = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        deliveryButton.setDisable(true); // Botão começa desativado
    }

    @FXML
    private void updateCart(Produtos product) {
        cart.add(produto);
        cartCountLabel.setText("Itens no Carrinho: " + cart.size());
        deliveryButton.setDisable(cart.size() < 20); // Ativa o botão com 20 itens ou mais
    }
}