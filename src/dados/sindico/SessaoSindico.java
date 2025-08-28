package dados.sindico;

import negocio.NegocioEdificio;
import negocio.entidade.Edificio;
import negocio.entidade.Sindico;

public class SessaoSindico {
    private static Sindico sindicoLogado = null;

    public static void login(Sindico sindico) {
        sindicoLogado = sindico;

        // Sincroniza o edifício com o que está no repositório
        Edificio edificioPersistido = NegocioEdificio.getInstancia().getEdificio();
        if (edificioPersistido != null) {
            sindicoLogado.setEdificio(edificioPersistido);
        }
    }

    public static void logout() {
        sindicoLogado = null;
    }

    public static boolean isLogado() {
        return sindicoLogado != null;
    }

    public static Sindico getSindicoLogado() {
        return sindicoLogado;
    }
}
