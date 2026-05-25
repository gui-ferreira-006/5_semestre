# 🌿 EcoMonitor IA — Monitoramento Ambiental via Rede M2M

> Sistema distribuído de monitoramento ambiental com comunicação **máquina-a-máquina (M2M)**, utilizando arquitetura **cliente-servidor** com chat operacional em tempo real, transferência de arquivos e autenticação via banco de dados MySQL.

![Java](https://img.shields.io/badge/Java-17+-orange?logo=openjdk)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql&logoColor=white)
![Sockets](https://img.shields.io/badge/Protocolo-TCP%2FIP%20Sockets-green)
![FlatLaf](https://img.shields.io/badge/UI-FlatLaf%203.4.1-blueviolet)
![SHA-256](https://img.shields.io/badge/Criptografia-SHA--256-red)
![UNIP](https://img.shields.io/badge/Faculdade-UNIP-blue)
![2026](https://img.shields.io/badge/Ano-2026-purple)
![Status](https://img.shields.io/badge/Status-Em%20desenvolvimento-yellow)

---

## 📋 Sobre o Projeto

Este projeto foi desenvolvido como **Atividades Práticas Supervisionadas (APS)** da disciplina de **Arquitetura de Redes de Computadores**, do curso de **Ciência da Computação — UNIP**.

O **EcoMonitor IA** simula uma rede de monitoramento ambiental distribuída, onde **terminais de campo** (clientes) se conectam a uma **central de operações** (servidor) para:

- 💬 Trocar mensagens em tempo real (chat operacional)
- 📁 Enviar e receber arquivos/laudos ambientais
- 🔐 Autenticar inspetores via banco de dados MySQL com criptografia SHA-256
- 📊 Monitorar indicadores ambientais simulados (qualidade do ar, risco de incêndio, nível do rio)
- 👥 Gerenciar múltiplos terminais conectados simultaneamente via **multithreading**

---

## 🏫 Informações Acadêmicas

| Campo       | Informação                                 |
|-------------|-------------------------------------------|
| Disciplina  | Arquitetura de Redes de Computadores       |
| Curso       | Ciência da Computação                      |
| Instituição | UNIP — Universidade Paulista               |
| Professor   | Arthur                                     |
| Semestre    | 5° Semestre — 2026                         |

---

## 🏗️ Arquitetura do Sistema

O sistema segue a arquitetura **cliente-servidor TCP/IP** com suporte a múltiplas conexões simultâneas via **Threads**:

```
┌─────────────────────┐         TCP/IP (Porta 65173)        ┌──────────────────────────┐
│   TERMINAL DE CAMPO │ ◄──────────────────────────────────► │   CENTRAL DE OPERAÇÕES   │
│   (TelaCliente)     │         Conexão Socket               │   (TelaServidor)         │
│                     │                                      │                          │
│  • Chat operacional │                                      │  • Log de eventos        │
│  • Envio de laudos  │                                      │  • Tabela de terminais   │
│  • Leituras locais  │                                      │  • Gerenciamento M2M     │
└──────┬──────────────┘                                      └────────────┬─────────────┘
       │                                                                  │
       │  Autenticação                                                    │
       ▼                                                                  │
┌─────────────────────┐                                                   │
│   TELA DE LOGIN     │                                                   │
│   (TelaLogin)       │                                                   │
│                     │                                                   │
│  • IP do Servidor   │         JDBC (porta 3306)                         │
│  • Credenciais      │ ─────────────────────────────────►  ┌─────────────┴──────┐
│  • Chave de Segur.  │                                     │   Banco de Dados   │
└─────────────────────┘                                     │   MySQL            │
                                                            │   chat_ambiental   │
                                                            └────────────────────┘
```

### Fluxo de Comunicação

1. **Login**: O inspetor abre a `TelaLogin`, insere o IP do servidor, credenciais e senha
2. **Autenticação**: O sistema valida as credenciais no banco de dados MySQL (senhas criptografadas com SHA-256)
3. **Conexão Socket**: Após autenticação bem-sucedida, uma conexão TCP é estabelecida com o servidor na porta `65173`
4. **Operação**: O inspetor pode enviar mensagens, receber alertas e submeter laudos (arquivos) pela rede
5. **Multithreading**: Cada cliente conectado é gerenciado por um `ClienteHandler` em sua própria Thread

---

## ✨ Funcionalidades

| Funcionalidade                         | Descrição                                                                  |
|----------------------------------------|----------------------------------------------------------------------------|
| 💬 **Chat em tempo real**              | Mensagens em broadcast para todos os clientes conectados                   |
| 🔒 **Mensagens privadas**             | Envio direto para um cliente específico com `/p <nome> <mensagem>`         |
| 📁 **Transferência de arquivos**       | Envio e recebimento de laudos/documentos via protocolo binário customizado |
| 🔐 **Autenticação MySQL + SHA-256**   | Login seguro com senhas criptografadas armazenadas em banco de dados       |
| 📊 **Simulação de sensores**          | Barras de progresso para qualidade do ar, risco de incêndio e nível do rio |
| 🖥️ **Interface gráfica moderna**      | GUI com FlatLaf (tema claro para cliente, tema escuro para servidor)       |
| 👥 **Múltiplos clientes simultâneos** | Suporte a N terminais conectados via multithreading                        |
| 📋 **Log de eventos em tempo real**   | Central de operações exibe logs coloridos com carimbos de data/hora        |
| 📡 **Painel de terminais ativos**     | Tabela dinâmica mostrando IP, usuário e identificador de cada terminal     |

### Comandos Disponíveis no Chat

| Comando                         | Ação                                           |
|---------------------------------|------------------------------------------------|
| `/lista`                        | Lista todos os clientes conectados              |
| `/p <nome> <mensagem>`         | Envia mensagem privada para um cliente          |
| `/arquivo <caminho>`           | Envia um arquivo para todos os conectados       |
| `/sair`                         | Desconecta do servidor                          |
| `<mensagem>`                    | Envia mensagem para todos (broadcast)           |

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia          | Uso no Projeto                                                            |
|---------------------|---------------------------------------------------------------------------|
| **Java 17+**        | Linguagem principal do projeto                                            |
| **Java Sockets**    | Comunicação TCP/IP entre cliente e servidor (porta 65173)                 |
| **Java Threads**    | Gerenciamento de múltiplos clientes simultâneos                           |
| **Java Swing**      | Interface gráfica (JFrame, JPanel, JTable, JTextPane, etc.)              |
| **FlatLaf 3.4.1**   | Tema moderno para Swing (FlatLightLaf + FlatDarkLaf)                      |
| **MySQL 8.0**       | Banco de dados relacional para autenticação de usuários                   |
| **JDBC**            | Conexão Java ↔ MySQL                                                     |
| **SHA-256**         | Criptografia de senhas (`java.security.MessageDigest`)                    |
| **DataStream**      | Transferência binária de arquivos (`DataInputStream/OutputStream`)        |

---

## 📁 Estrutura de Pastas

```
5_semestre-main/
│
├── 📄 README.md                          # Este arquivo
├── 📄 LICENSE                            # Licença MIT
│
├── 📂 APS_Redes_IA/                      # Projeto principal
│   ├── 📂 src/                           # Código-fonte
│   │   ├── 📄 App.java                   # Ponto de entrada padrão
│   │   │
│   │   ├── 📂 core/                      # Camada de rede (Sockets e Threads)
│   │   │   ├── 📄 Servidor.java          # Servidor TCP (aceita conexões na porta 65173)
│   │   │   ├── 📄 ClienteHandler.java    # Gerenciador multithreaded para cada cliente
│   │   │   ├── 📄 Cliente.java           # Cliente TCP via terminal (modo texto)
│   │   │   ├── 📄 ConexaoCliente.java    # Encapsulador de conexão para a GUI
│   │   │   ├── 📄 LogServidor.java       # Interface de registro de logs do servidor
│   │   │   ├── 📄 GerenciadorDeLogin.java# Autenticação via arquivo (desativado)
│   │   │   └── 📄 usuarios.txt           # Arquivo de usuários legado
│   │   │
│   │   ├── 📂 gui/                       # Camada de interface gráfica (Swing)
│   │   │   ├── 📄 App.java              # Ponto de entrada da GUI (FlatLaf)
│   │   │   ├── 📄 TelaLogin.java         # Tela de autenticação (login + IP)
│   │   │   ├── 📄 TelaCliente.java       # Terminal de campo (chat + sensores)
│   │   │   └── 📄 TelaServidor.java      # Central de operações (logs + terminais)
│   │   │
│   │   └── 📂 database/                  # Camada de persistência (MySQL)
│   │       ├── 📄 TesteSistema.java      # Script de teste e alimentação do banco
│   │       ├── 📂 Modelo/
│   │       │   └── 📄 Usuario.java       # Entidade de usuário (id, login, senha)
│   │       └── 📂 Persistencia/
│   │           ├── 📄 UsuarioDAO.java    # Interface DAO (cadastrar, autenticar)
│   │           ├── 📄 UsuarioDAOSql.java # Implementação JDBC/MySQL do DAO
│   │           └── 📄 Seguranca.java     # Criptografia SHA-256 de senhas
│   │
│   ├── 📂 lib/                           # Dependências externas
│   │   └── 📄 flatlaf-3.4.1.jar          # Biblioteca FlatLaf para interface moderna
│   │
│   └── 📂 bin/                           # Classes compiladas (gerado automaticamente)
│
└── 📂 recebidos/                         # Pasta onde arquivos recebidos são salvos
    └── 📄 Arquivoteste.txt               # Arquivo de teste
```

---

## 🚀 Como Executar

### Pré-requisitos

- **Java JDK 17** ou superior
- **MySQL 8.0** instalado e rodando
- **VS Code** com extensão Java (recomendado) ou qualquer IDE Java

### 1. Configurar o Banco de Dados

```sql
-- Crie o banco de dados no MySQL
CREATE DATABASE chat_ambiental;
```

> ⚠️ **Importante:** Atualize as credenciais de conexão no arquivo `UsuarioDAOSql.java` (linhas 12-14) com o seu usuário e senha do MySQL.

### 2. Popular o Banco (Opcional)

Execute a classe `TesteSistema.java` para criar a tabela de usuários e inserir dados de teste:

```bash
# Na pasta src/
javac -cp "../lib/*" database/TesteSistema.java database/Modelo/Usuario.java database/Persistencia/*.java
java -cp "../lib/*;." TesteSistema
```

### 3. Iniciar o Servidor (Central de Operações)

```bash
# Executar a TelaServidor
javac -cp "../lib/*" gui/TelaServidor.java core/*.java
java -cp "../lib/*;." gui.TelaServidor
```

A janela da **Central de Operações** será aberta com tema escuro, exibindo logs em tempo real e a tabela de terminais ativos.

### 4. Conectar um Cliente (Terminal de Campo)

```bash
# Executar a TelaLogin
javac -cp "../lib/*" gui/TelaLogin.java gui/TelaCliente.java core/*.java database/Modelo/*.java database/Persistencia/*.java
java -cp "../lib/*;." gui.TelaLogin
```

Na **Tela de Login**:
1. Insira o **IP do servidor** (use `localhost` se estiver na mesma máquina)
2. Insira sua **credencial** (ex: `Guilherme`)
3. Insira sua **chave de segurança** (senha cadastrada)
4. Clique em **"Autenticar e Conectar"**

### 5. Modo Terminal (Alternativo)

Para rodar o cliente em modo texto (sem interface gráfica):

```bash
javac core/Cliente.java
java core.Cliente
```

---

## 🖼️ Telas do Sistema

### Tela de Login
- Layout dividido: painel de marca verde esmeralda à esquerda + formulário de autenticação à direita
- Campos: IP do Servidor, Credencial do Inspetor, Chave de Segurança
- Retorno de status em tempo real (autenticando, conectando, erro)

### Terminal de Campo (Cliente)
- **Painel esquerdo**: Leituras locais com barras de progresso (Qualidade do Ar, Risco de Incêndio, Nível do Rio)
- **Painel direito**: Chat operacional com a Central + botões "Submeter Laudo" e "Enviar Alerta"
- Tema claro com paleta verde ecológica

### Central de Operações (Servidor)
- **Cabeçalho**: Título "CENTRAL DE OPERAÇÕES M2M" + IP do servidor
- **Painel central**: Log de eventos em tempo real com texto laranja/dourado sobre fundo escuro
- **Painel lateral**: Tabela de terminais ativos (Terminal, IP, Usuário)
- Tema escuro com estética operacional

---

## 👥 Integrantes

| Nome                              | Função no Projeto                                        |
|-----------------------------------|----------------------------------------------------------|
| Danielle Almeida Ignacio          | Back-end — Banco de Dados MySQL (DAO, Autenticação)      |
| Guilherme Ferreira de Sousa       | Back-end — Banco de Dados e Integração JDBC              |
| Júlia Fernandes Costa Da Cruz     | Front-end — Design e Estética das Interfaces Swing       |
| Paulo Mei                         | Back-end — Redes, Sockets TCP/IP e Multithreading        |
| Thomas Anderson                   | Integração e Testes do Sistema                           |

---

## 📄 Licença

Este projeto está licenciado sob a **Licença MIT** — veja o arquivo [LICENSE](LICENSE) para mais detalhes.

Este projeto é de uso acadêmico e não possui fins comerciais.
