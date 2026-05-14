
import Modelo.Usuario;
import Persistencia.UsuarioDAOSql;

public class TesteSistema {
    public static void main(String[] args) {
        // 1. Instancia o seu DAO (isso já deve criar a tabela no MySQL)
        UsuarioDAOSql banco = new UsuarioDAOSql();

        System.out.println("--- INICIANDO TESTES DO BANCO DE DADOS ---");

        // 2. Simula o colega da INTERFACE criando um novo usuário
        Usuario novoUser = new Usuario("Rachel", "Aavd301093#");

        System.out.println("Tentando cadastrar usuário...");
        if (banco.cadastrar(novoUser)) {
            System.out.println("✅ SUCESSO: Usuário cadastrado com senha criptografada!");
        } else {
            System.out.println("❌ ERRO: O usuário já existe ou o MySQL está desligado.");
        }

        // 3. Simula o colega da REDE (Chat) tentando fazer login
        System.out.println("\nTentando fazer login com a senha CORRETA...");
        if (banco.autenticar("Rachel", "Aavd301093#")) {
            System.out.println("✅ SUCESSO: Login autorizado!");
        } else {
            System.out.println("❌ ERRO: Sistema não reconheceu a senha.");
        }

        // 4. Teste de segurança (Tentando login com senha errada)
        System.out.println("\nTentando fazer login com a senha ERRADA...");
        if (!banco.autenticar("Rachel", "senha_falsa")) {
            System.out.println("✅ SUCESSO: O sistema barrou o acesso invasor!");
        } else {
            System.out.println("❌ ERRO CRÍTICO: O sistema aceitou uma senha errada.");
        }
    }
}