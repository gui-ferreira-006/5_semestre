package gui;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import core.ConexaoCliente;

public class TelaCliente extends JFrame {

    // Paleta de Cores baseada no novo design sugerido pelo grupo
    private final Color corFundo = new Color(223, 245, 224); // Verde Claro (Fundo)
    private final Color corTituloEsquerdo = new Color(22, 101, 52); // Verde Escuro Floresta
    private final Color corBotaoLaudo = new Color(30, 58, 138); // Azul Escuro / Marinho
    private final Color corBotaoAlerta = new Color(21, 128, 61); // Verde Escuro (Ação)

    // Cores das Barras de Progresso
    private final Color corBarraAr = new Color(34, 197, 94); // Verde
    private final Color corBarraFogo = new Color(234, 179, 8); // Amarelo
    private final Color corBarraAgua = new Color(59, 130, 246); // Azul

    // Componentes que o Back-end vai interagir
    private JProgressBar progressoAr;
    private JProgressBar progressoFogo;
    private JProgressBar progressoAgua;
    private JTextArea areaChat;
    private JTextField campoMensagem;
    private JButton btnEnviarAlerta;
    private JButton btnSubmeterLaudo;
    private ConexaoCliente conexao;

    public TelaCliente(ConexaoCliente conexao) {
        this.conexao = conexao;
        setTitle("Base de Monitoramento - Terminal de Campo");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fundo geral da janela
        getContentPane().setBackground(corFundo);
        setLayout(new BorderLayout());

        // Criando a divisão em dois painéis (Esquerdo e Direito)
        add(criarPainelEsquerdo(), BorderLayout.WEST);
        add(criarPainelDireito(), BorderLayout.CENTER);

        //Inicia a Thread de recebimento de mensagens
        iniciarRecepcao();

    }

    // ==================================================================
    // PAINEL ESQUERDO (Leituras Locais)
    // ==================================================================
    private JPanel criarPainelEsquerdo() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setPreferredSize(new Dimension(300, 0));
        painel.setBackground(corFundo);
        painel.setBorder(new EmptyBorder(30, 20, 20, 20));

        // Conteúdo Superior (Barras e Botão)
        JPanel painelConteudo = new JPanel();
        painelConteudo.setLayout(new BoxLayout(painelConteudo, BoxLayout.Y_AXIS));
        painelConteudo.setBackground(corFundo);

        JLabel lblTitulo = new JLabel("Leituras Locais");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(corTituloEsquerdo);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        painelConteudo.add(lblTitulo);
        painelConteudo.add(Box.createVerticalStrut(40));

        // 1. Qualidade do Ar
        progressoAr = adicionarBarraLeitura(painelConteudo, "Qualidade do Ar", 85, corBarraAr);

        // 2. Risco de Incêndio
        progressoFogo = adicionarBarraLeitura(painelConteudo, "Risco de Incêndio", 40, corBarraFogo);

        // 3. Nível do Rio
        progressoAgua = adicionarBarraLeitura(painelConteudo, "Nível do Rio", 60, corBarraAgua);

        // Botão de Simular Leituras
        JButton btnSimular = new JButton("Simular Leituras");
        btnSimular.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnSimular.setBackground(new Color(220, 225, 220));
        btnSimular.setFocusPainted(false);
        btnSimular.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Ação para animar as barras (apenas visual para a apresentação)
        btnSimular.addActionListener(e -> simularMudancaDeSensores());

        painelConteudo.add(Box.createVerticalStrut(20));
        painelConteudo.add(btnSimular);

        // Status no Rodapé Esquerdo
        JLabel lblStatus = new JLabel("Status: Conectado ao Servidor Central");
        lblStatus.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        lblStatus.setForeground(Color.BLACK);
        lblStatus.setHorizontalAlignment(SwingConstants.LEFT);

        painel.add(painelConteudo, BorderLayout.CENTER);
        painel.add(lblStatus, BorderLayout.SOUTH);

        return painel;
    }

    // Método auxiliar para criar as barras de progresso do mockup
    private JProgressBar adicionarBarraLeitura(JPanel painel, String texto, int valor, Color cor) {

        // O Wrapper é a "caixa" que vai guardar o texto e a barra juntos
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(corFundo);
        // Centralizando a caixa inteira em relação ao painel principal
        wrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        wrapper.setMaximumSize(new Dimension(250, 40));


        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JProgressBar barra = new JProgressBar(0, 100);
        barra.setValue(valor);
        barra.setForeground(cor);
        barra.setBackground(new Color(230, 230, 230)); // Fundo cinza claro da barra
        barra.setPreferredSize(new Dimension(250, 8)); // Altura fininha
        barra.setMaximumSize(new Dimension(250, 8));
        barra.setBorderPainted(false); // Remove borda 3D
        barra.setAlignmentX(Component.CENTER_ALIGNMENT);

        painel.add(label);
        painel.add(Box.createVerticalStrut(5));
        painel.add(barra);
        painel.add(Box.createVerticalStrut(25));

        return barra;
    }

    // ====================================================================
    // PAINEL DIREITO (Chat e Contato com a Central)
    // ====================================================================
    private JPanel criarPainelDireito() {
        JPanel painel = new JPanel(new BorderLayout(0, 15));
        painel.setBackground(corFundo);
        painel.setBorder(new EmptyBorder(30, 10, 30, 30)); // Margens para desgrudar da borda

        // Título Superior
        JLabel lblContato = new JLabel("Contato com a Central");
        lblContato.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblContato.setForeground(Color.BLACK);

        // Área do Chat (JTextArea branco com borda)
        areaChat = new JTextArea();
        areaChat.setEditable(false);
        areaChat.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        areaChat.setLineWrap(true);
        areaChat.setWrapStyleWord(true);
        areaChat.setMargin(new Insets(10, 10, 10, 10));
        areaChat.setText("📄 [Sistema]: Conexão estabelecida com o Servidor Central.\n" +
                         "📄 [Sistema]: Monitoramento M2M ativado. Aguardando leituras...\n\n");
        JScrollPane scrollChat = new JScrollPane(areaChat);
        scrollChat.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 180), 1));

        // --- Painel Inferior (Input e Botões) ---
        JPanel painelInput = new JPanel(new BorderLayout(10, 0));
        painelInput.setBackground(corFundo);

        // Campo de Mensagem (Usando funcionalidade de placeholder do FlatLaf)
        campoMensagem = new JTextField();
        campoMensagem.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        campoMensagem.putClientProperty("JTextField.placeholderText", "Escreva uma mensagem para a Central...");
        campoMensagem.setPreferredSize(new Dimension(0, 40));

        // Área dos Botões (Direita do Input)
        JPanel painelBotoes = new JPanel(new GridLayout(1, 2, 10, 0));
        painelBotoes.setBackground(corFundo);

        btnSubmeterLaudo = new JButton("Submeter Laudo");
        btnSubmeterLaudo.setBackground(corBotaoLaudo);
        btnSubmeterLaudo.setForeground(Color.WHITE);
        btnSubmeterLaudo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSubmeterLaudo.setFocusPainted(false);
        btnSubmeterLaudo.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnEnviarAlerta = new JButton("Enviar Alerta");
        btnEnviarAlerta.setBackground(corBotaoAlerta);
        btnEnviarAlerta.setForeground(Color.WHITE);
        btnEnviarAlerta.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnEnviarAlerta.setFocusPainted(false);
        btnEnviarAlerta.setCursor(new Cursor(Cursor.HAND_CURSOR));

        painelBotoes.add(btnSubmeterLaudo);
        painelBotoes.add(btnEnviarAlerta);

        painelInput.add(campoMensagem, BorderLayout.CENTER);
        painelInput.add(painelBotoes, BorderLayout.EAST);

        // Eventos
        configurarEventos();

        painel.add(lblContato, BorderLayout.NORTH);
        painel.add(scrollChat, BorderLayout.CENTER);
        painel.add(painelInput, BorderLayout.SOUTH);

        return painel;
    }

    //Thread que fica recebendo mensagens do servidor
    private void iniciarRecepcao () {
        new Thread(() -> {
            try {
                String mensagem;
                while ((mensagem = conexao.receberMensagem()) != null) {
                    final String msg = mensagem;

                    if (msg.startsWith("ARQUIVO:")) {
                        receberArquivo(msg);
                        
                    }

                    else {
                        SwingUtilities.invokeLater(() ->
                            areaChat.append(msg + "\n")
                        );
                    }
                }
            }

            catch (IOException e) {
                SwingUtilities.invokeLater(() ->
                    areaChat.append("[Sistema] Conexão encerrada.\n")
                );
            }
        }).start();
    }

    //Recebe arquivo do Servidor
    private void receberArquivo (String cabecalho) {
        try {
            
            String[] partes = cabecalho.split(":");
            String nomeArquivo = partes[1];
            long tamanho = Long.parseLong(partes[2]);

            SwingUtilities.invokeLater(() ->
                areaChat.append("[Sistema] Recebendo arquivo: " + nomeArquivo + "\n")
            );
            
            File pasta = new File ("recebidos");
            if (!pasta.exists()) pasta.mkdirs();

            File arquivo = new File ("recebidos/" + nomeArquivo);
            FileOutputStream fos = new FileOutputStream(arquivo);
            byte[] buffer = new byte [4096];
            long totalLido = 0;
            int lido;

            while (totalLido < tamanho &&
                (lido = conexao.getEntradaBytes().read(buffer, 0,
                (int) Math.min (buffer.length, tamanho - totalLido))) != -1) {
                    fos.write (buffer, 0, lido);
                    totalLido += lido;        
                }

                fos.close();
                SwingUtilities.invokeLater(() ->
                    areaChat.append ("[Servidor] Arquivo salvo em: recebidos/" + nomeArquivo + "\n")
                );

        } 
        
        catch (IOException e) {
            
            SwingUtilities.invokeLater(() ->
                areaChat.append("[Sistema] Erro ao receber arquivo.\n")
            );
        }
    }
        
    

    private void configurarEventos() {

        // Envio de Mensagem (Para o Mei conectar nos Sockets)
        Action acaoEnviar = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = campoMensagem.getText().trim();
                if (!msg.isEmpty()) {
                    conexao.enviarMensagem(msg);
                    campoMensagem.setText("");

                    // AQUI O MEI COLOCA O CÓDIGO DE ENVIO DO SOCKET (out.println(msg))
                }
            }
        };

        btnEnviarAlerta.addActionListener(acaoEnviar);
        campoMensagem.addActionListener(acaoEnviar); // Envia ao apertar Enter

        // Evento de Submeter Laudo
        btnSubmeterLaudo.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File arquivo = fileChooser.getSelectedFile();
                areaChat.append("📎 [Arquivo]: Enviando laudo '" + arquivo.getName() + "' para a central...\n");
                new Thread (() -> {
                    try {
                        conexao.enviarArquivo(arquivo);
                        SwingUtilities.invokeLater(() ->
                            areaChat.append("@ [Arquivo]: Arquivo enviado com sucesso!\n")
                        );
                    } 

                    catch (IOException ex) {
                        SwingUtilities.invokeLater(() -> 
                            areaChat.append("@ [Arquivo]: Erro ao enviar arquivo. \n")
                        );
                    }
                }).start();

                // AQUI O MEI COLOCA O CÓDIGO DE ENVIO DE ARQUIVO (File Transfer / GZIP)
            }
        });
    }

    // Efeito visual aleatório para a apresentação do trabalho
    private void simularMudancaDeSensores() {
        progressoAr.setValue((int) (Math.random() * 100));
        progressoFogo.setValue((int) (Math.random() * 100));
        progressoAgua.setValue((int) (Math.random() * 100));
    }

    //public static void main(String[] args) {
        // Aplica o tema Light do FlatLaf para ficar moderno igual à imagem
        //FlatLightLaf.setup();
        //SwingUtilities.invokeLater(() -> new TelaCliente().setVisible(true));
    //}
}