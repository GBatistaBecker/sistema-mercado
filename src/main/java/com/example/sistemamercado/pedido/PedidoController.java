package com.example.sistemamercado.pedido;

import com.example.sistemamercado.produto.Produto;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    // Configura o pedido atual e inicializa a exibição dos produtos e valor total
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
        pedidos = FXCollections.observableArrayList(pedido.getListaDeProdutos());
        productTable.setItems(pedidos);

        // Configurar colunas da tabela de produtos
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPreco()).asObject());

        // Exibir o valor total dos produtos
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

        salvarPedidoNoBanco(nome, endereco, telefone);
    }

    // Salva o pedido no banco de dados com os dados de contato e o valor total
    private void salvarPedidoNoBanco(String nome, String endereco, String telefone) {
        String sql = "INSERT INTO pedidos (nome, endereco, telefone, valor_total) VALUES (?, ?, ?, ?)";

        DatabaseMetaData FabricaDeConexao = null;
        try (Connection conn = FabricaDeConexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            double total = pedido.getListaDeProdutos().stream().mapToDouble(Produto::getPreco).sum();

            stmt.setString(1, nome);
            stmt.setString(2, endereco);
            stmt.setString(3, telefone);
            stmt.setDouble(4, total);

            stmt.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pedido Confirmado");
            alert.setContentText("Pedido salvo com sucesso!");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Ocorreu um erro ao salvar o pedido.");
            alert.showAndWait();
        }
    }
}