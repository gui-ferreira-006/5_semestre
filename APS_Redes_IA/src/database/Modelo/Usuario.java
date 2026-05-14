package Modelo;

public class Usuario {
    private int id;
    private String login;
    private String senha;

    public Usuario() {
    }

    // Construtor usado no seu TesteSistema (2 parâmetros)
    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    // Construtor completo
    public Usuario(int id, String login, String senha) {
        this.id = id;
        this.login = login;
        this.senha = senha;
    }

    // --- GETTERS E SETTERS (Verifique se o 'return' e o 'this' estão assim) ---

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha; // Se aqui estiver retornando null, dá o erro!
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}