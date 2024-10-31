package produto_Classe;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import produto_Classe.Produto;


public class ProdutoController {
    @FXML private TextField searchField;
    @FXML private ComboBox<String> preferenceComboBox;
    @FXML private TableView<Produto> productTable;
    @FXML private TableColumn<Produto, String> nameColumn;
    @FXML private TableColumn<Produto, Double> priceColumn;
    @FXML private Label cartCountLabel;
    @FXML private Button deliveryButton;

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
        preferenceComboBox.getItems().addAll("Todos", "Higiene", "Horti-fruto", "Bebidas", "Sem glúten");

        // Configurar colunas do TableView
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPreco()).asObject());
// Exibir produtos na tabela
        productTable.setItems(filteredProducts);
        updateFilteredProducts();
        atualizarCarrinho();

        // Desativar botão de entrega inicialmente
        deliveryButton.setDisable(true);
    }

    // Método para carregar produtos simulados
    private void loadProducts() {
        allProducts.addAll(
                new Produto("Maçã", 1.50, "Horti-fruti"),
                new Produto("Refrigerante", 3.75, "Bebidas"),
                new Produto("Pão", 2.00, "Sem glúten"),
                new Produto("Escova de dente", 5.50, "Higiene")
        );
    }

    // Atualiza a lista filtrada conforme a busca e o filtro de preferência
    @FXML
    private void updateFilteredProducts() {
        String searchQuery = searchField.getText().toLowerCase();
        String selectedPreference = preferenceComboBox.getValue();

        filteredProducts.setAll(allProducts.filtered(product -> {
            boolean matchesSearch = product.getNome().toLowerCase().contains(searchQuery);
            boolean matchesPreference = selectedPreference == null || product.getTipo().equals(selectedPreference);
            return matchesSearch && matchesPreference;
        }));
    }

    // Adiciona o produto ao carrinho e atualiza o contador e o botão de entrega
    @FXML
    private void addCarrinho(Produto produto) {
        cart.add(produto);
        atualizarCarrinho();
    }

    private void atualizarCarrinho() {
        cartCountLabel.setText("Itens no Carrinho: " + cart.size());
        deliveryButton.setDisable(cart.size() < 20); // Ativa o botão se o carrinho tiver 20 ou mais produtos
    }

    // Ação para o botão de entrega
    @FXML
    private void cuidarEntrega() {
        if (cart.size() >= 20) {
            // Aqui vai o código de processamento da entrega
            System.out.println("Entrega ativada para " + cart.size() + " itens.");
        }
    }
}
