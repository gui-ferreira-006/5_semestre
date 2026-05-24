package gui;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.io.IOException;

import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class TelaServidor extends JFrame implements core.LogServidor{

    // Nova paleta de cores (Estética sugerida pela Julia)
    private final Color corFundoLateral = new Color(26, 26, 38); // Fundo do painel direito
    private final Color corFundoCentral = new Color(26, 26, 38); // Fundo principal escuro
    private final Color corBorda = new Color(75, 55, 105); // Linhas divisórias arroxeadas
    private final Color corTextoLaranja = new Color(245, 176, 65); // Textos do log (Laranja/Dourado)
    private final Color corStatusCiano = new Color(0, 229, 255); //Ciano brilhante para IP/Status
    private final Color corBotaoRoxo = new Color(165, 85, 160); // Roxo/Magenta do botão
    private final Color corCabecalhoTabela = new Color(199, 120, 214); // Roxo claro das colunas

    private JTextPane logEventos;
    private DefaultTableModel modelTabela; // Agora usando uma tabela em vez de Lista
    private JTable tabelaTerminal;

    @Override 
    public void log (String mensagem) {
        SwingUtilities.invokeLater(() -> adicionarLog(mensagem));
    }

    @Override
    public void clienteLogado (String ip, String nome) {
        SwingUtilities.invokeLater(() -> {

            adicionarLog(obterAgora() + " [LOGIN] " + nome + " conectado de " + ip);
            adicionarTerminal (ip, nome);
        });
    }

    public TelaServidor() {
        configurarJanela();

        // Layout Principal
        add(criarCabecalho(), BorderLayout.NORTH);
        add(criarPainelCentralLog(), BorderLayout.CENTER);
        add(criarPainelLateralDireita(), BorderLayout.EAST);

        // Log Inicial de Sistema
        adicionarLog(obterAgora() + " [SISTEMA] Servidor aguardando início...");
    }

    private String obterIpLocal() {
        try {
            java.net.InetAddress ip = java.net.InetAddress.getLocalHost();
            return ip.getHostAddress();
        } catch (Exception e) {
            return "IP não encontrado";
        }
    }

    private void configurarJanela() {
        setTitle("EcoMonitor - Central de Operações e Auditoria");
        setSize(1050, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private JPanel criarCabecalho() {
        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setBackground(corFundoCentral);
        cabecalho.setPreferredSize(new Dimension(0, 70));
        cabecalho.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, corBorda));

        JLabel titulo = new JLabel(" CENTRAL DE OPERAÇÕES M2M");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);

        JLabel info = new JLabel("Status: Servidor Online | IP: " + obterIpLocal() + " ");
        info.setForeground(corStatusCiano);
        info.setFont(new Font("Segoe UI", Font.BOLD, 14));

        cabecalho.add(titulo, BorderLayout.WEST);
        cabecalho.add(info, BorderLayout.EAST);
        return cabecalho;
    }

    private JPanel criarPainelCentralLog() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(corFundoCentral);
        painel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblTitulo = new JLabel("MONITORAMENTO DE LOGS");
        lblTitulo.setForeground(new Color(138, 123, 163)); // Roxo acinzentado do título
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTitulo.setBorder(new EmptyBorder(0, 0, 10, 0));

        logEventos = new JTextPane();
        logEventos.setEditable(false);
        logEventos.setBackground(corFundoCentral);
        logEventos.setFont(new Font("Consolas", Font.PLAIN, 15));

        JScrollPane scroll = new JScrollPane(logEventos);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(corFundoCentral);

        painel.add(lblTitulo, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarPainelLateralDireita() {
        JPanel lateral = new JPanel(new BorderLayout());
        lateral.setPreferredSize(new Dimension(320, 0));
        lateral.setBackground(corFundoLateral);
        lateral.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, corBorda));

        // Título Terminais
        JLabel lblTerminais = new JLabel(" TERMINAIS ATIVOS ", SwingConstants.CENTER);
        lblTerminais.setPreferredSize(new Dimension(0, 50));
        lblTerminais.setForeground(Color.WHITE);
        lblTerminais.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // MUDANÇA: Usando JTable em vez JList ---
        String[] colunas = {"Terminal", "IP", "Usuário"};
        modelTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Impede que o usuário edite o texto clicando
            }
        };

        tabelaTerminal = new JTable(modelTabela);
        tabelaTerminal.setBackground(corFundoLateral);
        tabelaTerminal.setForeground(Color.WHITE);
        tabelaTerminal.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabelaTerminal.setRowHeight(40);
        tabelaTerminal.setShowGrid(false); // Remove as linhas de grade para ficar limpo
        tabelaTerminal.setSelectionBackground(new Color(45, 45, 68)); // Cor ao clicar na linha

        // Estilizando o Cabeçalho da Tabela (Cor Roxa do print)
        JTableHeader cabecalhoTabela = tabelaTerminal.getTableHeader();
        cabecalhoTabela.setBackground(corFundoLateral);
        cabecalhoTabela.setForeground(corCabecalhoTabela);
        cabecalhoTabela.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cabecalhoTabela.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, corBorda));

        // Alinhando o texto da tabela à esquerda com um pequeno padding
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setBorder(new EmptyBorder(0,5, 0, 0));
        for (int i = 0; i < tabelaTerminal.getColumnCount(); i++) {
            tabelaTerminal.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        JScrollPane scrollTabela = new JScrollPane(tabelaTerminal);
        scrollTabela.setBorder(BorderFactory.createEmptyBorder());
        scrollTabela.getViewport().setBackground(corFundoLateral);

        // Botão de Relatório
        JButton btnRelatorio = new JButton("Gerar Relatório Geral");
        btnRelatorio.setBackground(corBotaoRoxo);
        btnRelatorio.setForeground(Color.WHITE);
        btnRelatorio.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRelatorio.setFocusPainted(false);
        btnRelatorio.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRelatorio.setPreferredSize(new Dimension(0, 60));
        btnRelatorio.setBorder(BorderFactory.createEmptyBorder());

        lateral.add(lblTerminais, BorderLayout.NORTH);
        lateral.add(scrollTabela, BorderLayout.CENTER);
        lateral.add(btnRelatorio, BorderLayout.SOUTH);

        return lateral;
    }

    public void iniciarServidor () {

        //Registra a tela como logger do ClienteHandler
        core.ClienteHandler.setLogger (this);

        new Thread(() -> {
            try {

                java.net.ServerSocket serverSocket = new java.net.ServerSocket (65173);
                adicionarLog (obterAgora() + " [SISTEMA] Servidor iniciando na porta 65173!");
                
                while (true) { 
                    
                    java.net.Socket socket = serverSocket.accept();
                    String ip = socket.getInetAddress().toString();

                    //O logger cuida de tudo agora, não precisa passar 'tela' no construtor
                    core.ClienteHandler handler = new core.ClienteHandler(socket);
                    new Thread (() -> {
                        handler.run();
                        removerTerminal (ip);

                    }).start();
                    
                }
                
            } catch (IOException e) {

                adicionarLog ("[ERRO] " + e.getMessage());
            }
        }).start();
    }

    public void adicionarTerminal (String ip, String nome) {
        SwingUtilities.invokeLater(() -> {
            int linha = modelTabela.getRowCount () + 1;
            modelTabela.addRow (new Object [] {
                "TERM-0" + linha,
                ip,
                nome // <- nome real!

            });
        });
    }

    public void removerTerminal (String ip) {
        SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < modelTabela.getRowCount(); i++) {
                if (modelTabela.getValueAt (i, 1).equals (ip)) {
                    modelTabela.removeRow(i);
                    adicionarLog (obterAgora() + " [SAÍDA] Terminal " + ip + " desconectado.");
                    break;
                }
            }
        });
    }

    private String obterAgora () {
        return "[" + java.time.LocalDateTime.now()
            .format (java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")) + "]";
    
    }

    // Método para adicionar logs coloridos (O que o Mei vai usar para mostrar os eventos)
    public void adicionarLog(String mensagemCompleta) {
        StyledDocument doc = logEventos.getStyledDocument();
        Style style = logEventos.addStyle("estiloLaranja", null);

        try {
            StyleConstants.setForeground(style, corTextoLaranja);
            doc.insertString(doc.getLength(), mensagemCompleta + "\n", style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        SwingUtilities.invokeLater (() -> {
            TelaServidor tela = new TelaServidor();
            tela.setVisible(true);
            tela.iniciarServidor();
        });
    }
}
