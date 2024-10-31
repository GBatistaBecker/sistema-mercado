package pedido_Classe;

import produto_Classe.Produto;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private List<Produto> listaDeProdutos = new ArrayList<>();

    public void adicionarProduto(Produto produto) {
        listaDeProdutos.add(produto);
    }

    public void removerProduto(Produto produto) {
        listaDeProdutos.remove(produto);
    }

    public int getQuantidadeTotal() {
        return listaDeProdutos.size();
    }

    public List<Produto> getListaDeProdutos() {
        return listaDeProdutos;
    }

    public boolean podeSolicitarEntrega() {
        return getQuantidadeTotal() >= 20;
    }
}
