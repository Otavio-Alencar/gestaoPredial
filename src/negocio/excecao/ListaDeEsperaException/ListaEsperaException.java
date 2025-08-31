package negocio.excecao.ListaDeEsperaException;
@SuppressWarnings("serial")
public class ListaEsperaException extends Exception {
        private String msg;

        public ListaEsperaException(String msg) {
            super(msg);
            this.msg = msg;
        }

        @Override
        public String getMessage() {
            return msg;
        }

    }


