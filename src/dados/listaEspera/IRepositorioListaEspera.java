package dados.listaEspera;

import negocio.entidade.PessoaListaEspera;

public interface IRepositorioListaEspera {
    void adicionarPessoa(String nome, String cpf, String contato,
                         boolean ppi, boolean quilombola, boolean pcd,
                         boolean escolaPublica, boolean baixaRenda);

    PessoaListaEspera chamarProxima();

    PessoaListaEspera verProxima();

    void listarFila();

    int tamanhoFila();

    void removerPessoa(int id);

}
