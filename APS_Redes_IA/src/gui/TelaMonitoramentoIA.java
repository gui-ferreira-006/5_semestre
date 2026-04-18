package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TelaMonitoramentoIA extends JFrame {
    
    // Componentes da Interface
    private JTextArea areaChat;
    private JTextField campoMensagem;
    private JButton botaoEnviar;
    private JButton botaoExportarRelatorio;

    // Paleta de Cores (Estilo Centro de Operações)
    private Color corFundoEscuro = new Color(30, 34, 40); // Cinza chumbo escuro
    private Color corTextoConsole = new Color(74, 255, 126); // Verde néon
    private Color corAlerta = new Color(255, 87, 87); // Vermelho para alertas de IA
    private Color corFundoComponentes = new Color(45, 50, 58);

    public TelaMonitoramentoIA () {

        // 1. Configurações Básicas da Janela (JFrame)
        setTitle("Centro de Operações Ambientais - IA");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza na tela
        setLayout(new BorderLayout(10, 10)); // Margens entre as águas
        getContentPane().setBackground(corFundoEscuro); // Fundo da janela

        // 2. Título da Aplicação (Topo)
        JLabel labelTitulo = new JLabel(" SISTEMA DE MONITORAMENTO AMBIENTAL - REDE NEURAL ATIVA", JLabel.CENTER);
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setFont(new Font("Monospaced", Font.BOLD, 18));
        labelTitulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(labelTitulo, BorderLayout.NORTH);

        // 3. Área Central (O Chat / Log de Eventos)
        areaChat = new JTextArea();
        areaChat.setEditable(false);
        areaChat.setBackground(corFundoComponentes);
        areaChat.setForeground(corTextoConsole);
        areaChat.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaChat.setMargin(new Insets(10, 10, 10, 10));


        // Adiciona barra de rolagem ao chat
        JScrollPane scrollChat = new JScrollPane(areaChat);
        scrollChat.setBorder(BorderFactory.createLineBorder(corAlerta, 1)); // Borda vermelha pra dar um tom crítico
        add(scrollChat, BorderLayout.CENTER);

        // 4. Painel Inferior (Entrada de Dados e Botões)
        JPanel painelInferior = new JPanel();
        painelInferior.setLayout(new BorderLayout(10, 0));
        painelInferior.setBackground(corFundoEscuro);
        painelInferior.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        // Campo onde o usuário digita
        campoMensagem = new JTextField();
        campoMensagem.setBackground(corFundoComponentes);
        campoMensagem.setForeground(Color.WHITE);
        campoMensagem.setCaretColor(Color.WHITE); // Cor do cursor piscando
        campoMensagem.setFont(new Font("SansSerif", Font.PLAIN, 14));
        painelInferior.add(campoMensagem, BorderLayout.CENTER);

        // Painel para organizar os botões lado a lado
        JPanel painelBotoes = new JPanel(new GridLayout(1, 2, 10, 0));
        painelBotoes.setBackground(corFundoEscuro);

        // Botão de Enviar Mensagem
        botaoEnviar = new JButton("Transmitir Alerta");
        botaoEnviar.setBackground(corTextoConsole);
        botaoEnviar.setForeground(corFundoEscuro);
        botaoEnviar.setFocusPainted(false);
        painelBotoes.add(botaoEnviar);

        // Botão de Transferir Arquivo (O que terá compressão GZIP depois)
        botaoExportarRelatorio = new JButton("Exportar Relatório (PDF");
        botaoExportarRelatorio.setBackground(corAlerta);
        botaoExportarRelatorio.setForeground(Color.blue);
        botaoExportarRelatorio.setFocusPainted(false);
        painelBotoes.add(botaoExportarRelatorio);

        painelInferior.add(painelBotoes, BorderLayout.EAST);
        add(painelInferior, BorderLayout.SOUTH);

        // 5. Ações do Botões (Dados Mocados para Teste)
        configurarAcoes();

        // Mensagem de boas-vindas falsa
        areaChat.append("[SISTEMA] Conexão estabelecida com a base de dados.\n");
        areaChat.append("[IA-CORE] Monitoramento ambiental iniciado. Aguardando dados...\n\n");
    }

    private void configurarAcoes() {
        // Ação do botão de enviar (Quando clicar ou apertar ENTER)
        ActionListener acaoEnviar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = campoMensagem.getText();
                if (!texto.trim().isEmpty()) {
                    // Mocado: Imprime na própria tela ao invés de enviar para o servidor por enquanto
                    areaChat.append("[INSPETOR LOCAL] " + texto + "\n");
                    campoMensagem.setText(""); // Limpa o campo
                }
            }
        };

        botaoEnviar.addActionListener(acaoEnviar);
        campoMensagem.addActionListener(acaoEnviar);

        // Ação do botão de arquivo
        botaoExportarRelatorio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mocado: Simula a abertura de um seletor de arquivos
                JFileChooser fileChooser = new JFileChooser();
                int retorno = fileChooser.showOpenDialog(TelaMonitoramentoIA.this);

                if(retorno == JFileChooser.APPROVE_OPTION) {
                    String nomeArquivo = fileChooser.getSelectedFile().getName();
                    areaChat.append("[SISTEMA] Preparando arquivo '" + nomeArquivo + "' para compressão GZIP e envio...\n");
                    // No futuro, aqui você chamará o metódo do Mei para enviar os bytes!
                }
            }
        });
    }

    // Método principal apenas para você testar a tela rodando sozinha
    public static void main(String[] args) {
        // Alerta o visual padrão do Java para algo mais moderno do sistema operacional
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Garante que a tela será montada na Thread correta de interface gráfica
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TelaMonitoramentoIA tela = new TelaMonitoramentoIA();
                tela.setVisible(true);
            }
        });
    }
}
