package negocio;

import dados.sindico.IRepositorioLogin;
import negocio.entidade.Sindico;
import negocio.excecao.SindicoException.JaTemSindicoException;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Classe responsável pela lógica de negócio relacionada ao login e gerenciamento do síndico do sistema.
 * <p>
 * Essa classe utiliza um {@link IRepositorioLogin} para persistir, autenticar, alterar e remover
 * as informações do síndico. Também garante que apenas um síndico possa ser cadastrado no sistema.
 * @author Otavio Alencar & Erik Nascimento
 * @version 1.0
 * </p>
 */
public class NegocioLogin {

    /** Repositório para manipulação dos dados do síndico. */
    private IRepositorioLogin repositorio;

    /**
     * Construtor da classe que inicializa o repositório de login.
     *
     * @param login implementação de {@link IRepositorioLogin} responsável por persistência de dados.
     */
    public NegocioLogin(IRepositorioLogin login) {
        this.repositorio = login;
    }

    /**
     * Cadastra um novo síndico no sistema.
     * <p>
     * Caso já exista um síndico cadastrado, uma {@link JaTemSindicoException} será lançada.
     * Se o arquivo de dados ainda não existir, ele será criado automaticamente.
     * </p>
     *
     * @param sindico objeto {@link Sindico} contendo os dados do síndico.
     * @throws JaTemSindicoException se já houver um síndico cadastrado.
     */
    public void cadastrarSindico(Sindico sindico) throws JaTemSindicoException {
        try {
            if (repositorio.naoTemSindico()) {
                repositorio.cadastrarSindico(sindico);
            } else {
                throw new JaTemSindicoException();
            }
        } catch (FileNotFoundException e) {
            try {
                repositorio.cadastrarSindico(sindico);
            } catch (IOException ex) {
                throw new RuntimeException("Erro ao cadastrar síndico.", ex);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao verificar existência de síndico.", e);
        }
    }

    /**
     * Autentica um síndico no sistema com base no nome e senha informados.
     *
     * @param nome  nome do síndico.
     * @param senha senha do síndico.
     * @return o objeto {@link Sindico} autenticado.
     * @throws RuntimeException se não houver síndico cadastrado ou ocorrer um erro de leitura.
     */
    public Sindico autenticarSindico(String nome, String senha) {
        try {
            return repositorio.autenticar(nome, senha);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Ainda não existe síndico cadastrado.", e);
        } catch (IOException e) {
            throw new RuntimeException("Erro de leitura ao autenticar síndico.", e);
        }
    }

    /**
     * Remove o síndico cadastrado do sistema.
     *
     * @throws RuntimeException se ocorrer um erro de I/O ao tentar remover o síndico.
     */
    public void removerSindico() {
        try {
            repositorio.removerSindico();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao remover síndico: " + e.getMessage(), e);
        }
    }

    /**
     * Altera os dados do síndico cadastrado.
     *
     * @param sindico objeto {@link Sindico} contendo os novos dados.
     * @throws RuntimeException se ocorrer um erro de I/O ao tentar alterar os dados.
     */
    void alterarSindico(Sindico sindico) {
        try {
            repositorio.alterarSindico(sindico);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao alterar síndico: " + e.getMessage(), e);
        }
    }
}
