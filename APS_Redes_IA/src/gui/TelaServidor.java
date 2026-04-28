package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import com.formdev.flatlaf.FlatDarkLaf;
public class TelaServidor extends JFrame {

    // Componentes da Interface
    private JTextArea areaLogEventos;
    private JTextField campoMensagem;
    private JButton botaoEmitirAlerta;
    private JButton botaoAnexarDocumento;
    private JList<String> listaInspetores;

    // Paleta de Cores Moderna (Dark/Tech Theme)
    private Color corBarraLateral = new Color(15, 20, 25);
    private Color corFundoLogSolido = new Color(25, 30, 35); // Agora a cor é sólida para legibilidade
    private Color corTextoVerde = new Color(74, 255, 126);
    private Color corBotaoAcao = new Color(41, 128, 185);
    private Color corBotaoArquivo = new Color(192, 57, 43);
    private String placeholderText = " Escreva uma mensagem de alerta...";

    public TelaServidor() {
        // 1. Configurações Base do JFrame
        setTitle("Dashboard - Monitoramento Ambiental via IA");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Em vez de um fundo padrão, usamos um painel customizado para a imagem de fundo
        PainelComFundo painelPrincipal = new PainelComFundo();
        painelPrincipal.setLayout(new BorderLayout(15, 15));
        painelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(painelPrincipal);

        // 2. Montando a Barra Lateral (Sidebar)
        configurarBarraLateral(painelPrincipal);

        // 3. Montando a Área Central (O Log e os Controles)
        configurarAreaCentral(painelPrincipal);

        // 4. Configurar eventos (Cliques falsos por enquanto)
        configurarAcoes();

        // Simulando a inicialização do sistema
        imprimirLogSistemico("Inicializando protocolo de comunicação M2M...");
        imprimirLogSistemico("Conectado ao Servidor Central. Criptografia ativa.");
    }

    private void configurarBarraLateral(JPanel painelPrincipal) {
        JPanel barraLateral = new JPanel(new BorderLayout());
        barraLateral.setBackground(corBarraLateral);
        barraLateral.setPreferredSize(new Dimension(220, 0));

        // Título da Sidebar
        JLabel labelStatus = new JLabel("ESTAÇÕES ATIVAS", SwingConstants.CENTER);
        labelStatus.setForeground(Color.WHITE);
        labelStatus.setFont(new Font("SansSerif", Font.BOLD, 14));
        labelStatus.setBorder(new EmptyBorder(15, 0, 15, 0));
        barraLateral.add(labelStatus, BorderLayout.NORTH);

        // Lista de Usuários Online (Fictício por enquanto)
        String[]usuarios = {">>>[Você] Base Sul", ">>>Drone de Inspeção 01", ">>>Central de IA", ">>>Fiscalização Móvel"};
        listaInspetores = new JList<>(usuarios);
        listaInspetores.setBackground(corBarraLateral);
        listaInspetores.setForeground(new Color(150, 160, 170));

        // Bordas e scroll para a lista
        JScrollPane scrollLista = new JScrollPane(listaInspetores);
        scrollLista.setBorder(new EmptyBorder(0, 10, 10 ,10));
        scrollLista.getViewport().setBackground(corBarraLateral);
        barraLateral.add(scrollLista, BorderLayout.CENTER);

        painelPrincipal.add(barraLateral, BorderLayout.WEST);
    }

    private void configurarAreaCentral(JPanel painelPrincipal) {
        JPanel painelCentral = new JPanel(new BorderLayout(10, 10));
        painelCentral.setOpaque(false); // Transparente para ver o fundo

        // A. O "Rádio" (Log de Eventos)
        areaLogEventos = new JTextArea();
        areaLogEventos.setEditable(false);
        areaLogEventos.setBackground(corFundoLogSolido);
        areaLogEventos.setForeground(corTextoVerde);
        areaLogEventos.setFont(new Font("Consolas", Font.PLAIN, 14));
        areaLogEventos.setMargin(new Insets(15, 15, 15, 15));

        JScrollPane scrollChat = new JScrollPane(areaLogEventos);
        scrollChat.setBorder(BorderFactory.createLineBorder(new Color(60, 70, 80), 1));
        painelCentral.add(scrollChat, BorderLayout.CENTER);

        // B. Os Controles (Rodapé)
        JPanel painelRodape = new JPanel(new BorderLayout(10, 0));
        painelRodape.setOpaque(false);
        painelRodape.setBorder(new EmptyBorder(10, 0, 10, 0));

        campoMensagem = new JTextField(placeholderText);
        campoMensagem.setBackground(new Color(45, 50, 60));
        campoMensagem.setForeground(Color.GRAY);
        campoMensagem.setFont(new Font("SansSerif", Font.ITALIC, 14));
        campoMensagem.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 90, 100)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        // Lógica de Placeholder (O texto que some ao clicar)
        campoMensagem.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campoMensagem.getText().equals(placeholderText)) {
                    campoMensagem.setText("");
                    campoMensagem.setForeground(Color.WHITE);
                    campoMensagem.setFont(new Font("SansSerif", Font.PLAIN, 14));
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (campoMensagem.getText().isEmpty()) {
                    campoMensagem.setForeground(Color.GRAY);
                    campoMensagem.setFont(new Font("SansSerif", Font.ITALIC, 14));
                    campoMensagem.setText(placeholderText);
                }
            }
        });

        painelRodape.add(campoMensagem, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new GridLayout(1, 2, 10, 0));
        painelBotoes.setOpaque(false);

        botaoEmitirAlerta = criarBotaoEstilizado("📡 Emitir Alerta (Chat)", corBotaoAcao);
        botaoAnexarDocumento = criarBotaoEstilizado("📎 Anexar Laudo (PDF/XLS)", corBotaoArquivo);

        painelBotoes.add(botaoEmitirAlerta);
        painelBotoes.add(botaoAnexarDocumento);
        painelRodape.add(painelBotoes, BorderLayout.EAST);

        painelCentral.add(painelRodape, BorderLayout.SOUTH);
        painelPrincipal.add(painelCentral, BorderLayout.CENTER);
    }

    // Método auxiliar para criação de botões mais bonitos
    private JButton criarBotaoEstilizado(String texto, Color corFundo) {
        JButton botao = new JButton(texto);
        botao.setBackground(corFundo);
        botao.setForeground(Color.BLACK);
        botao.setFont(new Font("SansSerif", Font.BOLD, 12));
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return botao;
    }

    private void configurarAcoes() {
        // Ação do Rádio/Chat
        botaoEmitirAlerta.addActionListener(e -> enviarMensagem());
        campoMensagem.addActionListener(e -> enviarMensagem()); // Envia com ENTER

        // Ação da Transferência de Arquivo
        botaoAnexarDocumento.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                String nome = fileChooser.getSelectedFile().getName();
                imprimirLogSistemico("Comprimindo e enviando: " + nome);
            }
        });
    }

    private void enviarMensagem() {
        String texto = campoMensagem.getText();
        if (!texto.trim().isEmpty() && !texto.equals(placeholderText)) {
            areaLogEventos.append("[VOCÊ] " + texto + "\n");
            campoMensagem.setText("");
        }
    }

    // Formata avisos do sistema em cor diferente do chat comum
    private void imprimirLogSistemico(String mensagem) {
        areaLogEventos.append(">>> SISTEMA: " + mensagem + "\n");
    }

    // --- CLASSE INTERNA PARA O FUNDO EXIGIDO PELO MANUAL ---
    // Este painel permite que você coloque uma foto (ex: mapa.png) de fundo.
    class PainelComFundo extends JPanel {
        private Image imagemFundo;

        public PainelComFundo() {
            // Tenta carregar uma imagem. Se você baixar um mapa no futuro, coloque o caminho correto aqui.
            // Exemplo: new ImageIcon("src/gui/img/mapa_satelite.jpg").getImage();
            try {
                imagemFundo = new ImageIcon("caminho_da_sua_imagem.png").getImage();
            } catch (Exception e) {
                imagemFundo = null;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagemFundo != null && imagemFundo.getWidth(this) > -1) {
                // Desenha a imagem esticada para preencher a tela
                g.drawImage(imagemFundo, 0, 0, getWidth(), getHeight(), this);
            } else {
                // Se não achar a imagem, desenha um degradê tecnológico de fundo
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint degradê = new GradientPaint(0, 0, new Color(15, 32, 39),
                                                          getWidth(), getHeight(), new Color(32, 58, 67));
                g2d.setPaint(degradê);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Desenha uma "grade" simulando um radar
                g2d.setColor(new Color(255, 255, 255, 10)); // Branco quase transparente
                for(int i = 0; i < getWidth(); i += 40) {
                    g2d.drawLine(i, 0, i, getHeight());
                }
                for(int i = 0; i < getHeight(); i += 40) {
                    g2d.drawLine(0, i, getWidth(), i);
                }
            }
        }
    }

    public static void main(String[] args) {
        // FlatLaf aplicado para um visual mais moderno (Dark Mode)
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            System.err.println("Falha ao carregar o tema moderno. " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> new TelaServidor().setVisible(true));
    }
}
