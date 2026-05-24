/*package core;

import java.io.*;

public class GerenciadorDeLogin {

     //Caminho do arquivo de usuários
    private static final String ARQUIVO = "core/usuarios.txt";
    
    //Verifica se o usuário e senha estão corretos
    public static boolean verificarLogin (String usuario, String senha) {
        try {

            BufferedReader leitor = new BufferedReader (new FileReader (ARQUIVO));
            String linha;

            //Lê o arquivo linha por linha, cada linha tem o formato "usuario:senha"
            while ((linha = leitor.readLine()) != null) {

                //Separa o nome e a senha usando ":" como delimitador
                String[] partes = linha.split (":");

                if (partes.length == 2) {
                    String nomeArquivo = partes[0].trim();
                    String senhaArquivo = partes[1].trim();

                    //Compara com o que o cliente digitou
                    if (nomeArquivo.equals (usuario) && senhaArquivo.equals (senha)) {
                        leitor.close();
                        return true; //Login bem-sucedido

                    }
                }
            }

            leitor.close();

        }

        catch (IOException e) {
            System.out.println ("[Servidor] Erro ao ler arquivo de usuários.");

        }

        return false; //Usuário ou senha incorretos

    }

}


    
    

*/