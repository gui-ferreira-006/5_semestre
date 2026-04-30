package gui;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Random; // Importação para gerar dados aleatórios

public class TelaCliente extends JFrame {
    
    private JTextArea areaChat;
    private JTextField campoMensagem;
    private JButton btnEnviar;
    private JButton btnAnexar;

    // As barras de progresso variáveis da classe para o botão poder atualizá-las
    private JProgressBar barraAr;
    private JProgressBar barraIncendio;
    private JProgressBar barraRio;

    // Paleta de Cores Ecológica
    private Color corFundo = new Color(245, 247, 240); // Verde claro suave
    private Color corPainel = Color.WHITE; // Branco para os painéis
    private Color corVerdePrincipal = new Color(16, 185, 129);
    private Color corTextoPadrao = new Color(50, 50, 50); // Cinza escuro para o texto

    public TelaCliente() {
        setTitle("Base de Monitoramento - Terminal de Campo");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(corFundo);

        ((JPanel) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        inicializarPainelSensores();
        inicializarPainelChat();
    }

    private void inicializarPainelSensores() {
        JPanel painelLateral = new JPanel();
        painelLateral.setLayout(new BoxLayout(painelLateral, BoxLayout.Y_AXIS));
        painelLateral.setPreferredSize(new Dimension(280, 0));
        painelLateral.setBackground(corPainel);
        painelLateral.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel tituloSensores = new JLabel("Leituras Locais");
        tituloSensores.setFont(new Font("Segoe UI", Font.BOLD, 18));
        tituloSensores.setForeground(corVerdePrincipal);
        tituloSensores.setAlignmentX(Component.CENTER_ALIGNMENT);

        painelLateral.add(tituloSensores);
        painelLateral.add(Box.createVerticalStrut(30));

        // Inicializando as barras antes de adicioná-las ao painel
        barraAr = criarBarraProgresso(85, new Color(16, 185, 129));
        barraIncendio = criarBarraProgresso(30, new Color(245, 158, 11));
        barraRio = criarBarraProgresso(65, new Color(59, 130, 246));

        painelLateral.add(montarBlocoSensor("Qualidade do Ar", barraAr));
        painelLateral.add(Box.createVerticalStrut(20));
        painelLateral.add(montarBlocoSensor("Risco de Incêndio", barraIncendio));
        painelLateral.add(Box.createVerticalStrut(20));
        painelLateral.add(montarBlocoSensor("Nível do Rio", barraRio));

        painelLateral.add(Box.createVerticalStrut(30));

        // Criando o botão de Atualizar Sensores
        JButton btnAtualizarSensores = new JButton("↻ Simular Leituras");
        btnAtualizarSensores.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAtualizarSensores.setBackground(new Color(240, 240, 240));
        btnAtualizarSensores.setForeground(corTextoPadrao);
        btnAtualizarSensores.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAtualizarSensores.setFocusPainted(false);
        btnAtualizarSensores.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Ação do botão: gera números aleatórios e atualiza as barras
        btnAtualizarSensores.addActionListener(e -> atualizarBarrasAleatoriamente());

        painelLateral.add(btnAtualizarSensores);

        painelLateral.add(Box.createVerticalGlue());

        JLabel statusConexao = new JLabel("Status: Conectado ao Serividor Central");
        statusConexao.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        statusConexao.setForeground(Color.GRAY);
        statusConexao.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelLateral.add(statusConexao);

        add(painelLateral, BorderLayout.WEST);
    }

    // Método auxiliar para criar uma barra de progresso personalizada
    private JProgressBar criarBarraProgresso(int valorInicial, Color cor) {
        JProgressBar barra = new JProgressBar(0, 100);
        barra.setValue(valorInicial);
        barra.setForeground(cor);
        barra.setBackground(new Color(230, 230, 230));
        barra.setString(valorInicial + "%");
        barra.setFont(new Font("Segoe UI", Font.BOLD, 11));
        barra.setBorderPainted(false);
        return barra;
    }

    // Método auxiliar para montar o texto e a barra juntos
    private JPanel montarBlocoSensor(String nome, JProgressBar barra) {
        JPanel painel = new JPanel(new BorderLayout(0, 5));
        painel.setBackground(corPainel);
        painel.setMaximumSize(new Dimension(250, 40));

        JLabel lblNome = new JLabel(nome);
        lblNome.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblNome.setForeground(corTextoPadrao);

        painel.add(lblNome, BorderLayout.NORTH);
        painel.add(barra, BorderLayout.CENTER);

        return painel;
    }

    // A ação do botão para atualizar as barras com valores aleatórios
    private void atualizarBarrasAleatoriamente() {
        Random gerador = new Random();


        // Gera valores entre 0 e 100
        int novoAr = gerador.nextInt(101);
        int novoIncendio = gerador.nextInt(101);
        int novoRio = gerador.nextInt(101);

        // Atualiza a barra de Qualidade do Ar
        barraAr.setValue(novoAr);
        barraAr.setString(novoAr + "%");

        // Atualiza a barra de Risco de Incêndio
        barraIncendio.setValue(novoIncendio);
        barraIncendio.setString(novoIncendio + "%");

        // Atualiza a barra de Nível do Rio
        barraRio.setValue(novoRio);
        barraRio.setString(novoRio + "%");

        // Adiciona um aviso no log para o usuário saber que a atualização ocorreu
        areaChat.append("🔄 [Sistema]: Leituras locais atualizadas (Simulação).\n");
    }

    private void inicializarPainelChat() {
        JPanel painelCentral = new JPanel(new BorderLayout(10, 10));
        painelCentral.setBackground(corFundo);

        JLabel tituloChat = new JLabel("Contato com a Central");
        tituloChat.setFont(new Font("Segoe UI", Font.BOLD, 18));
        tituloChat.setForeground(corTextoPadrao);
        painelCentral.add(tituloChat, BorderLayout.NORTH);

        areaChat = new JTextArea();
        areaChat.setEditable(false);
        areaChat.setBackground(corPainel);
        areaChat.setForeground(corTextoPadrao);
        areaChat.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        areaChat.setLineWrap(true);
        areaChat.setWrapStyleWord(true);
        areaChat.setBorder(new EmptyBorder(10, 10, 10, 10));

        areaChat.append("🟢 [Sistema]: Conexão estabelecida com o Servidor Central.\n");
        areaChat.append("🟢 [Sistema]: Monitoramento M2M ativado. Aguardando leituras...\n\n");

        JScrollPane scrollPane = new JScrollPane(areaChat);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true));
        painelCentral.add(scrollPane, BorderLayout.CENTER);

        JPanel painelAcoes = new JPanel(new BorderLayout(10, 0));
        painelAcoes.setBackground(corFundo);

        campoMensagem = new JTextField("Escreva uma mensagem para a Central...");
        campoMensagem.setForeground(Color.GRAY);
        campoMensagem.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        campoMensagem.setPreferredSize(new Dimension(0, 40));

        campoMensagem.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campoMensagem.getText().equals("Escreva uma mensagem para a Central...")) {
                    campoMensagem.setText("");
                    campoMensagem.setForeground(corTextoPadrao);
                    campoMensagem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (campoMensagem.getText().isEmpty()) {
                    campoMensagem.setForeground(Color.GRAY);
                    campoMensagem.setFont(new Font("Segoe UI", Font.ITALIC, 14));
                    campoMensagem.setText("Escreva uma mensagem para a Central...");
                }
            }
        });

        JPanel painelBotoes = new JPanel(new GridLayout(1, 2, 10, 0));
        painelBotoes.setBackground(corFundo);

        btnEnviar = new JButton("Enviar Alerta");
        btnEnviar.setBackground(corVerdePrincipal);
        btnEnviar.setForeground(Color.WHITE);
        btnEnviar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnEnviar.setFocusPainted(false);
        btnEnviar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnAnexar = new JButton("Submeter Laudo");
        btnAnexar.setBackground(new Color(59, 130, 246));
        btnAnexar.setForeground(Color.WHITE);
        btnAnexar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnAnexar.setFocusPainted(false);
        btnAnexar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnEnviar.addActionListener(e -> {
            String msg = campoMensagem.getText();
            if (!msg.isEmpty() && !msg.equals("Escreva uma mensagem para a Central...")) {
                areaChat.append("📤 [Você]: " + msg + "\n");
                campoMensagem.setText("");
            }
        });

        btnAnexar.addActionListener(e -> {
            areaChat.append("📎 [Sistema]: Preparando envio de arquivo PDF/Excel...\n");
        });

        painelBotoes.add(btnAnexar);
        painelBotoes.add(btnEnviar);

        painelAcoes.add(campoMensagem, BorderLayout.CENTER);
        painelAcoes.add(painelBotoes, BorderLayout.EAST);

        painelCentral.add(painelAcoes, BorderLayout.SOUTH);

        add(painelCentral, BorderLayout.CENTER);


    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            System.err.println("Falha ao carregar o FlatLightLaf. " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            TelaCliente tela = new TelaCliente();
            tela.setVisible(true);
        });
    }
}
