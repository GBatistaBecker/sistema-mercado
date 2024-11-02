package com.example.sistemamercado.Database;

import com.example.sistemamercado.config.FabricaDeConexao;

import com.example.sistemamercado.produto.Produto;
import javafx.collections.FXCollections;
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
            logger.info("Lista de técnicos recuperada com sucesso.");

        } catch (SQLException e) {
            logger.error("Erro ao listar técnicos: {}", e.getMessage());
        }
        return produtos;
    }

//     Método para atualizar um técnico no banco de dados
        public boolean atualizar(Produto produto) {
           boolean isInserido = false; // Variável para armazenar o status da inserção

            if (produto.getTipo() == null) {
                logger.warn("ID do técnico não pode ser nulo. Atualização não realizada.");
                throw new IllegalArgumentException("ID do técnico não pode ser nulo.");
            }

            String sql = "UPDATE tecnico SET nome = ?, preco = ?, tipo = ?,  WHERE id = ?";

            try (Connection conexao = FabricaDeConexao.obterConexao();
                 PreparedStatement statement = conexao.prepareStatement(sql)) {

                statement.setString(1, produto.getNome());
                statement.setDouble(2, produto.getPreco());
                statement.setString(3, produto.getTipo());

                int linhasAfetadas = statement.executeUpdate();
                if (linhasAfetadas > 0) {
                    isInserido = true; // A atualização foi bem-sucedida
                    logger.info("Produto atualizado com sucesso: {}", produto.getTipo());
                }

            } catch (SQLException e) {
                logger.error("Erro ao atualizar produto: {}", e.getMessage());
            }
            return isInserido;
        }
//
        // Método para excluir um técnico do banco de dados
        public void excluir(Long id) {
            String sql = "DELETE FROM produto WHERE id = ?";

            try (Connection conexao = FabricaDeConexao.obterConexao();
                 PreparedStatement statement = conexao.prepareStatement(sql)) {

                statement.setLong(1, id);
                statement.executeUpdate();
                logger.info("Produto excluído com sucesso: {}", id);

            } catch (SQLException e) {
                logger.error("Erro ao excluir produto: {}", e.getMessage());
            }
        }

        // Método para buscar um técnico pelo ID
        public Produto buscarPorId(Long id) {
            Produto produto = null;
            String sql = "SELECT nome, preco, tipo FROM produtos WHERE id = ?";

            try (Connection conexao = FabricaDeConexao.obterConexao();
                 PreparedStatement statement = conexao.prepareStatement(sql)) {

                statement.setLong(1, id);
                ResultSet resultado = statement.executeQuery();

                if (resultado.next()) {
                    produto = new Produto();
                    produto.setNome(resultado.getString("nome"));
                    produto.setPreco(resultado.getDouble("preco"));
                    produto.setTipo(resultado.getString("tipo"));
                    logger.info("Produto encontrado: {}", produto.getNome());
                } else {
                    logger.warn("Nenhum produto encontrado com o ID: {}", id);
                }

            } catch (SQLException e) {
                logger.error("Erro ao buscar produto por ID: {}", e.getMessage());
            }
            return produto;
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
        produtoDao.insertProduct(new Produto("Cerveja", 3.75, "Bebidas"));
        produtoDao.insertProduct(new Produto("Castanha do Pará",50.00, "Sem glúten"));
        produtoDao.insertProduct(new Produto("Talco", 15.00, "Higiene"));
    }
}
