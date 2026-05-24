
import Modelo.Usuario;
import Persistencia.UsuarioDAOSql;

public class TesteSistema {
    public static void main(String[] args) {
        // 1. Instancia o seu DAO (isso já deve criar a tabela no MySQL)
        UsuarioDAOSql banco = new UsuarioDAOSql();

        System.out.println("--- INICIANDO TESTES DO BANCO DE DADOS ---");
        // 2. Simula o colega da INTERFACE criando um novo usuário
        Usuario[] usuarios = {

            new Usuario("Rachel", "Aavd301093#"),
            new Usuario("Danielle", "Aavd301093#"),
            new Usuario("Thomas", "Pikachu123"),
            new Usuario("Mei","Queijoquente"),
            new Usuario("Guilherme", "Lakersthebest"),
            new Usuario("Julia", "Soylinda123"),

        };

        for (Usuario u : usuarios) {
            if (banco.cadastrar(u)) {
                System.out.println ("Cadastrado: " + u.getLogin());
            } else {
                System.out.println ("Já existe: " + u.getLogin());
            }
        }

        System.out.println ("\n--- VERIFICANDO LOGINS ---");

        //Testa os logins 
        for (Usuario u : usuarios) {
            if (banco.autenticar(u.getLogin(), u.getSenha())) {
                System.out.println ("Login OK: " + u.getLogin());
            } else {
                System.out.println ("Falhou: " + u.getLogin());
            }
        }
    }
}