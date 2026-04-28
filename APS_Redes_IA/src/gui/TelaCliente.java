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
        JButton btnAtualizar = new JButton("↻ Simular Leituras");
    }
}
