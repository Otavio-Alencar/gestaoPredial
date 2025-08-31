package dados.listaEspera;


import negocio.entidade.PessoaListaEspera;

import java.util.List;

public interface IRepositorioListaEspera{
    void adicionarPessoa(String nome, String cpf, String contato,
                         boolean ppi, boolean quilombola, boolean pcd,
                         boolean escolaPublica, boolean baixaRenda);
    void removerPessoa(String cpf);

    ;
    List<PessoaListaEspera>  getPessoas();
    int tamanhoFila();

}