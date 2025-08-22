package dados.base;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class RepositorioBase<T> {

    protected List<T> lista;

    public RepositorioBase() {
        this.lista = new ArrayList<>();
    }

    /**
     * Adiciona um item no repositório.
     */
    public void adicionar(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Item não pode ser nulo");
        }
        lista.add(item);
    }

    /**
     * Lista todos os itens do repositório.
     */
    public List<T> listar() {
        return new ArrayList<>(lista); // retorna cópia para evitar manipulação externa
    }

    /**
     * Remove todos os itens que satisfaçam o predicado.
     */
    public void removerPor(Predicate<T> condicao) {
        boolean removido = lista.removeIf(condicao);
        if (!removido) {
            throw new RuntimeException("Nenhum item encontrado para remoção");
        }
    }

    /**
     * Busca itens que satisfaçam uma condição específica.
     */
    public List<T> buscarPor(Predicate<T> condicao) {
        List<T> resultado = new ArrayList<>();
        for (T item : lista) {
            if (condicao.test(item)) {
                resultado.add(item);
            }
        }
        return resultado;
    }

    /**
     * Verifica se o repositório está vazio.
     */
    public boolean estaVazio() {
        return lista.isEmpty();
    }

    /**
     * Limpa todos os itens do repositório.
     */
    public void limpar() {
        lista.clear();
    }

    
}
