package com.example.sistemamercado.produto;

import com.example.sistemamercado.Database.ProdutoDao;
import com.example.sistemamercado.Main;
import com.example.sistemamercado.pedido.Pedido;
import com.example.sistemamercado.pedido.PedidoController;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class ProdutoController {
    private static final Logger logger = LoggerFactory.getLogger(ProdutoDao.class);

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> preferenceComboBox;

    @FXML
    private TableView<Produto> productTable;

    @FXML
    private TableColumn<Produto, String> nameColumn;

    @FXML
    private TableColumn<Produto, Double> priceColumn;

    @FXML
    private Label cartCountLabel;

    @FXML
    private Button deliveryButton;

    @FXML
    private Button searchButton;

    @FXML
    void filteredProducts(ActionEvent event) {
        String value = preferenceComboBox.getValue();
        List<Produto> produtoList = produtoDao.buscarPorTipo(value);
        filteredProducts.setAll(produtoList);
        updateFilteredProducts();
    }

    @FXML
    void updateFilteredProducts(ActionEvent event) {
    }

    ProdutoDao produtoDao;

    public ProdutoController() {
        this.produtoDao = new ProdutoDao(); // Inicializando o serviço;
    }

    private ObservableList<Produto> allProducts; // Lista de todos os produtos
    private ObservableList<Produto> filteredProducts; // Lista de produtos filtrados
    private ObservableList<Produto> cart; // Lista de produtos no carrinho

    public void initialize() {
        // Configuração inicial dos componentes
        allProducts = FXCollections.observableArrayList();
        filteredProducts = FXCollections.observableArrayList();
        cart = FXCollections.observableArrayList();

        // Carregar produtos
        loadProducts();

        // Configurar ComboBox com preferências
        if (preferenceComboBox.getItems().isEmpty()) {
            preferenceComboBox.getItems().addAll("Todos", "Higiene", "Horti-fruto", "Bebidas", "Sem glúten");
        }

        // Configurar colunas do TableView
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPreco()).asObject());

        // Exibir produtos na tabela
        productTable.setItems(filteredProducts);
        updateFilteredProducts();
//        atualizarCarrinho();

        // Desativar botão de entrega inicialmente
        deliveryButton.setDisable(true);

        // Adicionar listener ao searchField para atualização em tempo real
        searchField.textProperty().addListener((observable, oldValue, newValue) -> updateFilteredProducts());

        // Configurar evento de clique duplo no TableView
        productTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Verifica se é um clique duplo
                Produto selectedProduct = productTable.getSelectionModel().getSelectedItem();
                if (selectedProduct != null) {
                    adicionarProdutoAoCarrinho(selectedProduct);
                }
            }
        });

    }

    private Pedido pedido = new Pedido(); // Instância do pedido

    // Método para adicionar o produto ao carrinho
    private void adicionarProdutoAoCarrinho(Produto produto) {
        pedido.adicionarProduto(produto); // Adiciona o produto ao pedido
        atualizarCarrinho(produto); // Atualiza a exibição do carrinho
    }

    // Método para carregar produtos simulados
    private void loadProducts() {
        allProducts.addAll(produtoDao.listar());
    }

    // Atualiza a lista filtrada conforme a busca e o filtro de preferência
    @FXML
    private void updateFilteredProducts() {
        String searchQuery = searchField.getText().toLowerCase();
        String selectedPreference = preferenceComboBox.getValue();

        // Filtra a lista allProducts e atualiza filteredProducts
        filteredProducts.setAll(allProducts.filtered(product -> {
            boolean matchesSearch = product.getNome().toLowerCase().contains(searchQuery);
            boolean matchesPreference = selectedPreference == null || selectedPreference.equals("Todos") || product.getTipo().equals(selectedPreference);
            return matchesSearch && matchesPreference;
        }));
    }

    @FXML
    void btnAtualizarCarrinho(ActionEvent event) {
        logger.info("Clicou no botão Entregar");
        cuidarEntrega();
    }

    @FXML
    private void atualizarCarrinho(Produto produto) {
        // Atualiza o contador de itens no carrinho
        cartCountLabel.setText("Itens no Carrinho: " + pedido.getQuantidadeTotal());

        // Ativa o botão de entrega se houver 10 ou mais itens no pedido
        deliveryButton.setDisable(pedido.getQuantidadeTotal() < 20);
        cart.add(produto);
//        cuidarEntrega();
    }

    @FXML
    private void cuidarEntrega() {
        if (cart.size() >= 20) {
            System.out.println("Entrega ativada para " + cart.size() + " itens.");
            try {
                // Ajustar o caminho do FXML para o formato correto
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/example/sistemamercado/entrega-view.fxml"));
                Parent root = loader.load();

                // Configurar o controlador da nova janela
                PedidoController pedidoController = loader.getController();
                pedidoController.setPedido(pedido); // Passa o pedido atual para o controlador

                // Configurar e exibir a nova janela de entrega
                Stage stage = new Stage();
                stage.setTitle("Detalhes de Entrega");
                stage.setScene(new Scene(root, 600, 400));
                stage.show();


            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Quantidade Insuficiente");
            alert.setContentText("É necessário ter pelo menos 20 itens no carrinho para ativar a entrega.");
            alert.showAndWait();
        }
    }
}

