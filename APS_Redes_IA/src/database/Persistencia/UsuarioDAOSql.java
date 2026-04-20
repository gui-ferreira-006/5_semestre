package Persistencia;
import Modelo.Usuario;
import java.sql.*;

public class UsuarioDAOSql implements UsuarioDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/chat_ambiental";
    private static final String User = "root";
    private static final String Pass = "32643553";

    public UsuarioDAOSql(){
        inicializarTabela();
    }

    private void inicializarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "login VARCHAR(255) NOT NULL UNIQUE," +
                "senha VARCHAR(255) NOT NULL" + ");";

        try (Connection conn = DriverManager.getConnection(URL, User, Pass);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela 'usuarios' verificada cpm sucesso no MySQL!");
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar tabela: " + e.getMessage());
        }
    }

        @Override
    public boolean cadastrar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (Login, Senha) VALUES (?,?)";
        try (Connection conn = DriverManager.getConnection(URL, User, Pass);
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, usuario.getLogin());
            String senhaCriptografada = Seguranca.criptografasSenha(usuario.getSenha());
            pstmt.setString(2, senhaCriptografada);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error ao cadastras usuário: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean autenticar(String Nome, String Senha) {
        String sql = "SELECT senha FROM usuarios WHERE login = ?";
        try (Connection conn = DriverManager.getConnection(URL, User, Pass);
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, Nome);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                String senhaNoBanco = rs.getString("senha");
                String senhaDigitadaCripto = Seguranca.criptografasSenha(Senha);
                return senhaNoBanco.equals(senhaDigitadaCripto);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
