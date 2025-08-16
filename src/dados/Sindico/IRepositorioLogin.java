package dados.Sindico;

import negocio.entidade.Sindico;

public interface IRepositorioLogin {


    /**
     * Cadastra um síndico no sistema, caso ainda não exista um registro.
     * <p>
     * O método verifica se já existe um síndico cadastrado.
     * Caso exista, o cadastro não será realizado.
     *
     * @param sindico Objeto {@link Sindico} contendo nome e senha para cadastro.
     * @throws RuntimeException se já houver um síndico registrado no sistema.
     */
    void cadastrarSindico(Sindico sindico);



    /**
     * Autentica um síndico no sistema verificando se as credenciais são válidas.
     * <p>
     * O método compara o nome e a senha fornecidos com os dados do síndico cadastrado.
     * Caso correspondam, a autenticação é realizada com sucesso.
     *
     * @param nome  nome do síndico para login
     * @param senha senha do síndico para login
     * @return {@code true} se a autenticação for bem-sucedida, {@code false} caso contrário
     * @throws RuntimeException se não houver síndico cadastrado ou se as credenciais forem inválidas
     */
    boolean autenticar(String nome, String senha);


    /**
     * Remove o sindico cadastrado no sistema.
     * <p>
     * O método verifica se já existe um síndico cadastrado.
     * Caso exista, o mesmo será removido.
     *
     * @throws RuntimeException se não houver síndico cadastrado.
     */
    void removerSindico();
    void alterarSindico();


    /**
     * Verifica no sistema a ausencia de um sindico registrado.
     * <p>
     * O método consulta o sistema para verificar se há sindico cadastrado.
     * Caso tenha, retorna true.
     *
     *
     * @return {@code true} se não houver sindico cadastrado, {@code false} caso contrário
     * @throws RuntimeException se não houver síndico cadastrado ou se as credenciais forem inválidas
     */
    Boolean NaoTemSindico();
}
