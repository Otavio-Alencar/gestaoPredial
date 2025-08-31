package negocio;

import dados.sindico.IRepositorioLogin;
import negocio.entidade.Sindico;
import negocio.excecao.SindicoException.JaTemSindicoException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class NegocioLogin {

    private IRepositorioLogin repositorio;
    public NegocioLogin(IRepositorioLogin login) {
        this.repositorio = login;

    }
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

    public Sindico autenticarSindico(String nome, String senha) {
        try {
            return repositorio.autenticar(nome, senha);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Ainda não existe síndico cadastrado.", e);
        } catch (IOException e) {
            throw new RuntimeException("Erro de leitura ao autenticar síndico.", e);
        }
    }


    public void removerSindico() {
        try {
            repositorio.removerSindico();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao remover síndico: " + e.getMessage(), e);
        }
    }
    void alterarSindico(Sindico sindico){
        try{
            repositorio.alterarSindico(sindico);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao alterar síndico: " + e.getMessage(), e);
        }
    };

}
