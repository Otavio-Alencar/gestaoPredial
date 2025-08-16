package dados.Sindico;

import negocio.entidade.Sindico;

public class SessaoSindico {
    private static Sindico sindicoLogado = null;

    public static void login(Sindico sindico) {
        sindicoLogado = sindico;
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
