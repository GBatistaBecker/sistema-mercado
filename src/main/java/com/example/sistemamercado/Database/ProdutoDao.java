package com.example.sistemamercado.Database;

import com.example.sistemamercado.config.FabricaDeConexao;

import com.example.sistemamercado.produto.Produto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProdutoDao {
    private static final Logger logger = LoggerFactory.getLogger(ProdutoDao.class);

    public void insertProduct(Produto produto) {
        String sql = "INSERT INTO produtos (nome, preco, tipo) VALUES (?, ?, ?)";

        try (Connection conn = FabricaDeConexao.obterConexao();

             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, produto.getNome());
            pstmt.setDouble(2, produto.getPreco());
            pstmt.setString(3, produto.getTipo());
            pstmt.executeUpdate();
            System.out.println("Produto adicionado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar produto: " + e.getMessage());
        }
    }

    // Método para listar todos os técnicos
    public List<Produto> listar() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos";

        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement statement = conexao.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {
                Produto produto = new Produto();
                produto.setNome(resultado.getString("nome"));
                produto.setPreco(resultado.getDouble("preco"));
                produto.setTipo(resultado.getString("tipo"));
                //faltas os outros campos


                produtos.add(produto);
            }
            logger.info("Lista de produtos recuperada com sucesso.");

        } catch (SQLException e) {
            logger.error("Erro ao listar produtos: {}", e.getMessage());
        }
        return produtos;
    }

    public List<Produto> buscarPorTipo(String tipo) {

        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT nome, preco, tipo FROM produtos WHERE tipo LIKE ?";


        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setString(1,  tipo + "%");
            ResultSet resultado = statement.executeQuery();

            while (resultado.next()) {
                Produto produto = new Produto();
                logger.info("Listando produto: " + produto.getNome());
                produto.setNome(resultado.getString("nome"));
                produto.setPreco(resultado.getDouble("preco"));
                produto.setTipo(resultado.getString("tipo"));
                //faltas os outros campos


                produtos.add(produto);
            }
            logger.info("Lista de produtos recuperada com sucesso.");

        } catch (SQLException e) {
            logger.error("Erro ao listar produtod: {}", e.getMessage());
        }
        return produtos;
    }

    public static void main(String[] args) {
        ProdutoDao produtoDao = new ProdutoDao();
        produtoDao.insertProduct(new Produto("Morango", 11.00, "Horti-fruti"));
        produtoDao.insertProduct(new Produto("Cerveja Brahma", 3.75, "Bebidas"));
        produtoDao.insertProduct(new Produto("Castanha do Pará",50.00, "Sem glúten"));
        produtoDao.insertProduct(new Produto("Trufa Divine", 35.00, "Sem glúten"));
        produtoDao.insertProduct(new Produto("Cachaça Velho Barreiro",13.00 , "Bebidas"));
        produtoDao.insertProduct(new Produto("Limão", 6.00, "Horti-fruti"));
        produtoDao.insertProduct(new Produto("Neve", 12.00, "Higiene"));
        produtoDao.insertProduct(new Produto("Pasta de dente Sensodyne", 15.00, "Higiene"));
        produtoDao.insertProduct(new Produto("Pão de sanduíche", 10.00, "Sem glúten"));
        produtoDao.insertProduct(new Produto("Whisky Johnny Walker", 120.00, "Bebidas"));
        produtoDao.insertProduct(new Produto("Mamão", 8.00, "Horti-fruti"));
        produtoDao.insertProduct(new Produto("Vinho Casillero Del Diablo", 50.00, "Bebidas"));
        produtoDao.insertProduct(new Produto("Pasta de dente Sorriso", 7.00, "Higiene"));
        produtoDao.insertProduct(new Produto("Shampoo Clear Man anticaspa", 12.00, "Higiene"));
        produtoDao.insertProduct(new Produto("Talco Johnson & Johnson", 10.00, "Higiene"));
        produtoDao.insertProduct(new Produto("Bergamota", 2.00, "Horti-fruti"));
        produtoDao.insertProduct(new Produto("Macarrão Caseiro Nona Franca", 25.00, "Sem glúten"));
        produtoDao.insertProduct(new Produto("Absorvente Always", 6.00, "Higiene"));
        produtoDao.insertProduct(new Produto("Vinho Cambruzzi", 60.00, "Bebidas"));
        produtoDao.insertProduct(new Produto("Banana", 4.00, "Horti-fruti"));
        produtoDao.insertProduct(new Produto("Energético Red Bull", 11.00, "Bebidas"));
        produtoDao.insertProduct(new Produto("Coca-Cola", 13.00, "Bebidas"));
        produtoDao.insertProduct(new Produto("Fanta uva", 10.00, "Bebidas"));
        produtoDao.insertProduct(new Produto("Melancia inteira", 25.00, "Horti-fruti"));
    }
}
