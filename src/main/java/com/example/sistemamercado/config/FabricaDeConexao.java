package com.example.sistemamercado.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class FabricaDeConexao {

    private static HikariDataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(FabricaDeConexao.class);

    // Configuração inicial
    static {
        criarBanco("jdbc:mysql://localhost:3306", "supermercado", "root", "Root");
        configurarPoolDeConexoes();
        inicializarTabelas();
    }

    // Método para criar o banco de dados, se necessário
    private static void criarBanco(String jdbcUrl, String dbName, String username, String password) {
        String urlSemBanco = jdbcUrl;
        try (Connection conexao = DriverManager.getConnection(urlSemBanco, username, password);
             Statement stmt = conexao.createStatement()) {

            String sql = "CREATE DATABASE IF NOT EXISTS " + dbName;
            stmt.executeUpdate(sql);
            logger.info("Banco de dados '{}' verificado/criado com sucesso.", dbName);

        } catch (SQLException e) {
            logger.error("Erro ao criar/verificar o banco de dados '{}': {}", dbName, e.getMessage());
            throw new RuntimeException("Erro ao criar/verificar o banco de dados.", e);
        }
    }

    // Configuração do pool de conexões
    private static void configurarPoolDeConexoes() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/supermercado");
        config.setUsername("root");
        config.setPassword("Root");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setMaxLifetime(1800000);

        dataSource = new HikariDataSource(config);
    }

    // Método para criar tabelas no banco de dados
    private static void inicializarTabelas() {
        String criarTabelaProdutos = """
                CREATE TABLE IF NOT EXISTS produtos (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    nome VARCHAR(255) NOT NULL,
                    preco DOUBLE NOT NULL,
                    tipo VARCHAR(100) NOT NULL
                );
                """;

        try (Connection conexao = obterConexao();
             Statement stmt = conexao.createStatement()) {

            // Executar criação de tabelas
            stmt.executeUpdate(criarTabelaProdutos);
            logger.info("Tabela 'produtos' criada/verificada com sucesso.");

        } catch (SQLException e) {
            logger.error("Erro ao criar/verificar tabelas: {}", e.getMessage());
            throw new RuntimeException("Erro ao criar/verificar tabelas no banco de dados.", e);
        }
    }

    // Método para obter uma conexão
    public static Connection obterConexao() throws SQLException {
        logger.info("Obtendo conexão do pool...");
        return dataSource.getConnection();
    }

    // Método para fechar o pool de conexões
    public static void fecharDataSource() {
        if (dataSource != null) {
            logger.info("Fechando o pool de conexões...");
            dataSource.close();
        }
    }

    public static void main(String[] args) {
        try (Connection conexao = FabricaDeConexao.obterConexao()) {
            logger.info("Conexão obtida com sucesso!");
        } catch (SQLException e) {
            logger.error("Erro ao obter a conexão: {}", e.getMessage());
        }
    }
}