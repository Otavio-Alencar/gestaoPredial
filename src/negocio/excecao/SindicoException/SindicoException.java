package negocio.excecao.SindicoException;
@SuppressWarnings("serial")
public class SindicoException extends Exception {
    private String msg;

    public SindicoException(String msg) {
        super(msg);
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }

}


