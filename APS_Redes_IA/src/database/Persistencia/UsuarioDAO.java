package Persistencia;

import Modelo.Usuario;

public interface UsuarioDAO {
    boolean cadastrar(Usuario usuario);
    boolean autenticar(String Login, String Senha);
}
