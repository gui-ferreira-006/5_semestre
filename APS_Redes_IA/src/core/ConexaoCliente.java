package core;

import java.io.*;
import java.net.*;

public class ConexaoCliente {
    
    private Socket socket;
    private PrintWriter saida;
    private BufferedReader entrada;
    private DataOutputStream saidaBytes;
    private DataInputStream entradaBytes;

    //Tenta conectar ao servidor
    public boolean conectar (String ip, int porta) {
        try {
            socket = new Socket (ip, porta);
            saida = new PrintWriter (socket.getOutputStream(), true);
            entrada = new BufferedReader (new InputStreamReader (socket.getInputStream()));
            saidaBytes = new DataOutputStream (socket.getOutputStream());
            entradaBytes = new DataInputStream (socket.getInputStream());
            return true;
        }

        catch (IOException e) {
            return false;
        }
    }

    //Envia uma mensagem de texto
    public void enviarMensagem (String mensagem) {
        if (saida != null) saida.println(mensagem);
    }

    //L~E uma linha do servidor
    public String receberMensagem() throws IOException {
        return entrada.readLine();
    }

    //Envia um arquivo
    public void enviarArquivo (File arquivo) throws IOException {
        saida.println("ARQUIVO:" + arquivo.getName() + ":" + arquivo.length());
        FileInputStream fis = new FileInputStream(arquivo);
        byte[] buffer = new byte[4096];
        int lido;
        while ((lido = fis.read(buffer)) != -1) {
            saidaBytes.write(buffer, 0, lido);
            
        }

        saidaBytes.flush();
        fis.close();

    }

    //Fecha conexão
    public void desconectar () {
        try {
            if (socket != null) socket.close();

        }

        catch (IOException e) {}

    }

    //Getters

    public DataInputStream getEntradaBytes () { return entradaBytes;}
    public boolean estaConectado() { return socket != null && !socket.isClosed(); }
    
}


