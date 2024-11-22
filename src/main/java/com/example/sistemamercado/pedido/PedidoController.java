package com.example.sistemamercado.pedido;

import com.example.sistemamercado.produto.Produto;
import com.mysql.cj.jdbc.DatabaseMetaData;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class PedidoController {

    @FXML
    private TextField addressField;

    @FXML
    private TableColumn<Produto, String> nameColumn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TableColumn<Produto, Double> priceColumn;

    @FXML
    private TableView<Produto> productTable;

    @FXML
    private Label totalLabel;

    private Pedido pedido;
    private ObservableList<Produto> pedidos;
    private DatabaseMetaData FabricaDeConexao;


    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
        pedidos = FXCollections.observableArrayList(pedido.getListaDeProdutos());
        productTable.setItems(pedidos);


        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPreco()).asObject());

        // Exibir o valor total dos produtos
        atualizarTotal();
    }

    // Atualiza o valor total dos produtos na label
    private void atualizarTotal() {
        double total = pedido.getListaDeProdutos().stream().mapToDouble(Produto::getPreco).sum();
        totalLabel.setText(String.format("R$ %.2f", total));
    }

    // Confirma a entrega após verificar campos obrigatórios
    @FXML
    private void confirmarEntrega(ActionEvent event) {
        String nome = nameField.getText();
        String endereco = addressField.getText();
        String telefone = phoneField.getText();

        if (nome.isEmpty() || endereco.isEmpty() || telefone.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos obrigatórios");
            alert.setContentText("Preencha todos os campos para a entrega.");
            alert.showAndWait();
            return;
        }
        // Chama o popup de confirmação após salvar o pedido com sucesso
        mostrarPopup();

    }


    // Método para mostrar o popup de confirmação
    private void mostrarPopup() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sistemamercado/notificacao-entrega.fxml"));
            Parent root = fxmlLoader.load();
            Stage popupStage = new Stage();
            popupStage.setScene(new Scene(root, 300, 150));
            popupStage.setTitle("Confirmação");
            popupStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Não foi possível abrir o popup de confirmação.");
            alert.showAndWait();
        }
    }
}