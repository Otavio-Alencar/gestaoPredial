package dados.base;

import java.util.List;

public interface IRepositorioBase<T> {
    void adicionar(T entidade);
    void remover(T entidade);
    List<T> listar();
}