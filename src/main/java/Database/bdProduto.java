package Database;

import produto_Classe.Produto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    // URL do banco de dados MySQL, com o nome do banco e as credenciais
    private static final String URL = "jdbc:mysql://localhost:3306/supermercado";
    private static final String USER = "root"; // substitua pelo seu usuário MySQL
    private static final String PASSWORD = "Root"; // substitua pela sua senha MySQL

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
public static void insertProduct(String nome, double preco, String tipo) {
    String sql = "INSERT INTO produtos (nome, preco, tipo) VALUES (?, ?, ?)";

    try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, nome);
        pstmt.setDouble(2, preco);
        pstmt.setString(3, tipo);
        pstmt.executeUpdate();
        System.out.println("Produto adicionado com sucesso!");
    } catch (SQLException e) {
        System.out.println("Erro ao adicionar produto: " + e.getMessage());
    }
}
public static List<Produto> getProduto() {
    List<Produto> produtoList = new ArrayList<>();
    String sql = "SELECT * FROM produtos";

    try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
            Produto produto = new Produto(
                    rs.getString("nome"),
                    rs.getDouble("preço"),
                    rs.getString("tipo")
            );
            productList.add(produto);
        }
    } catch (SQLException e) {
        System.out.println("Erro ao buscar produtos: " + e.getMessage());
    }
    return productList;
}