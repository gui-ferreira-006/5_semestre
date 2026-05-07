package gui;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TelaLogin extends JFrame {
    
    // Componentes que a Danielle e o Mei vão usar depois
    private JTextField campoIpServidor;
    private JTextField campoCredencial;
    private JPasswordField campoSenha;
    private JButton btnConectar;
    private JLabel lblStatus;

    // Cores do Tema
    private Color corEsquerda = new Color(16, 185, 129); // Verde Esmeralda (Tema Ecológico)
    private Color corDireita = Color.WHITE;
    private Color corTextoPrimario = new Color(40, 40, 40);

    public TelaLogin() {
        setTitle("Autenticação - Rede de Monitoramento Ambiental");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Cria os dois grandes blocos da tela
        JPanel painelEsquerdo = criarPainelEsquerdo();
        JPanel painelDireito = criarPainelDireito();

        add(painelEsquerdo, BorderLayout.WEST);
        add(painelDireito, BorderLayout.CENTER);
    }

    // --- LADO ESQUERDO: BRANDING E TEMA AMBIENTAL ---
    private JPanel criarPainelEsquerdo() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setPreferredSize(new Dimension(300, 0));
        painel.setBackground(corEsquerda);
        painel.setBorder(new EmptyBorder(40, 30, 40, 30));

        // Textos de impacto para o tema da APS
        JPanel painelTextos = new JPanel();
        painelTextos.setLayout(new BoxLayout(painelTextos, BoxLayout.Y_AXIS));
        painelTextos.setOpaque(false);

        JLabel titulo = new JLabel("EcoMonitor IA");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitulo = new JLabel("<html>Terminal de Campo M2M<br>Acesso Restrito</html>");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitulo.setForeground(new Color(230, 240, 235));
        subtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        painelTextos.add(titulo);
        painelTextos.add(Box.createVerticalStrut(10));
        painelTextos.add(subtitulo);

        // Rodapé do lado esquerdo (Versão do sistema)
        JLabel versao = new JLabel("v2.0 - Módulo de Prevenção");
        versao.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        versao.setForeground(new Color(200, 230, 210));

        painel.add(painelTextos, BorderLayout.NORTH);
        painel.add(versao, BorderLayout.SOUTH);

        return painel;
    }

    // --- LADO DIREITO: O FORMULÁRIO DE LOGIN (COM OS DIFERENCIAIS) ---
    private JPanel criarPainelDireito() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(corDireita);
        painel.setBorder(new EmptyBorder(50, 50, 50, 50));

        JLabel tituloForm = new JLabel("Estabelecer Conexão");
        tituloForm.setFont(new Font("Segoe UI", Font.BOLD, 22));
        tituloForm.setForeground(corTextoPrimario);
        tituloForm.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 1. Diferencial: IP do Servidor
        JLabel lblIp = new JLabel("IP do Servidor Central:");
        lblIp.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblIp.setForeground(Color.GRAY);
        campoIpServidor = new JTextField("192.168.1.100"); // Valor padrão para facilitar testes
        estilizarCampoTexto(campoIpServidor);

        // 2. Diferencial: ID da Estação/Inspetor
        JLabel lblUsuario = new JLabel("Credencial do Inspetor / ID Estação:");
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblUsuario.setForeground(Color.GRAY);
        campoCredencial = new JTextField();
        estilizarCampoTexto(campoCredencial);

        // Senha
        JLabel lblSenha = new JLabel("Chave de Segurança:");
        lblSenha.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblSenha.setForeground(Color.GRAY);
        campoSenha = new JPasswordField();
        estilizarCampoTexto(campoSenha);

        
        // Botão de Conectar
        btnConectar = new JButton("Autenticar e Conectar");
        btnConectar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnConectar.setForeground(Color.WHITE);
        btnConectar.setBackground(new Color(41, 128, 185)); // Azul para ação de rede
        btnConectar.setFocusPainted(false);
        btnConectar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConectar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnConectar.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 3. Diferencial: Status da Conexão
        lblStatus = new JLabel("Status: Aguardando credenciais...");
        lblStatus.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblStatus.setForeground(Color.GRAY);

        // Evento do Botão (Simulando a ação para a Danielle e Mei assumirem depois)
        btnConectar.addActionListener((ActionEvent e) -> {
            // Este é um feedback visual para o usuário, indicando que a autenticação está em andamento
            lblStatus.setForeground(new Color(200, 100, 0)); // Laranja para ação em andamento
            lblStatus.setText("Status: Autenticando com " + campoIpServidor.getText() + "...");

            /* ================================================================================
            Prestem atenção: aqui é aonde a lógica do Back-end entra em cena
            O código abaixo (Timer) é apenas para simular o tempo de loading.
            Quando forem integrar o Back-end, APAGUEM a simulação e descomentem
            a estrutura abaixo, adaptando para as classes de vocês.
            ===================================================================================
            
            // --- PASSO 1: Danielle (BANCO DE DADOS) ---
            // Dani, você precisa pegar o que o usuário digitou nestes dois campos:
            String credencialDigitada = campoCredencial.getText();
            String senhaDigitada = new String(campoSenha.getPassword());
            
            // Depois, você chama a sua classe DAO para validar lá no seu banco:
            AutenticacaoDAO dao = new AutenticacaoDAO();
            boolean loginValido = dao.validarCredenciais(credencialDigitada, senhaDigitada);
            
            if (loginValido) {
                // Se o banco aprovou, a gente avisa o usuário e passa a bola pro Mei!
                lblStatus.setForeground(new Color(16, 185, 129)); // Verde
                lblStatus.setText("Status: Conexão Estabelecida!");
                
                // --- PASSO 2: MEI (REDES / SOCKETS) ---
                // Mei, como o login deu certo, agora você precisa conectar o Socket 
                // do Cliente lá no Servidor Central. Pega o IP digitado assim:
                String ipDoServidor = campoIpServidor.getText();
                
                // Aqui você chama a sua classe que inicia a conexão TCP/Socket:
                // ClienteSocket.iniciarConexao(ipDoServidor, 5000); // Exemplo de porta
                
                // --- PASSO 3: TRANSIÇÃO DE TELAS (FRONT-END) ---
                // Depois que o banco validou e o socket conectou, nós fechamos
                // a tela de login e abrimos a tela principal do Cliente.
                dispose(); // Fecha esta janela de login
                new TelaCliente().setVisible(true); // Abre a base de monitoramento
                
            } else {
                // Se a Danielle retornar 'false' do banco (senha ou usuário incorretos):
                JOptionPane.showMessageDialog(TelaLogin.this, 
                    "Credenciais inválidas ou Estação não encontrada no Banco de Dados.", 
                    "Erro de Autenticação", JOptionPane.ERROR_MESSAGE);
                lblStatus.setText("Status: Falha na conexão.");
                lblStatus.setForeground(Color.RED);
            }
            */

            // Exemplo visual de resposta
            // (Apagar este bloco inteiro quando forem integrar o código real do banco e socket)
            Timer timer = new Timer(1500, evt -> {
                if(campoCredencial.getText().isEmpty() || new String(campoSenha.getPassword()).isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor, insira as credenciais da estação.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    lblStatus.setText("Status: Aguardando credenciais...");
                    lblStatus.setForeground(Color.GRAY);
                } else {
                    lblStatus.setForeground(corEsquerda); // Verde
                    lblStatus.setText("Status: Conexão Estabelecida!");
                    // Aqui a Danielle e o Mei vão abrir a próxima tela ou iniciar o processo de monitoramento
                }
            });
            timer.setRepeats(false);
            timer.start();
        });

        // Adiciona os componentes ao painel direito
        painel.add(tituloForm);
        painel.add(Box.createVerticalStrut(30));

        painel.add(lblIp);
        painel.add(Box.createVerticalStrut(5));
        painel.add(campoIpServidor);
        painel.add(Box.createVerticalStrut(15));

        painel.add(lblUsuario);
        painel.add(Box.createVerticalStrut(5));
        painel.add(campoCredencial);
        painel.add(Box.createVerticalStrut(15));

        painel.add(lblSenha);
        painel.add(Box.createVerticalStrut(5));
        painel.add(campoSenha);
        painel.add(Box.createVerticalStrut(25));

        painel.add(btnConectar);
        painel.add(Box.createVerticalStrut(15));
        painel.add(lblStatus);

        return painel;
    }

    // Método auxiliar para não repetir código de estilo
    private void estilizarCampoTexto(JTextField campo) {
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    public static void main(String[] args) {
        try {
            // Aplicando o tema moderno do FlatLaf
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            System.err.println("Erro ao carregar o FlatLaf");
        }

        SwingUtilities.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });
    }
}
