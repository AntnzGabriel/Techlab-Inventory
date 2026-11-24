TechLab Inventory

**Sistema de Gerenciamento de Ativos de Tecnologia da Informação (TI).**

O TechLab Inventory é um projeto acadêmico desenvolvido em Java com o objetivo de facilitar o gerenciamento e o controle de ativos de TI, aplicando conceitos de Programação Orientada a Objetos (POO), persistência de dados e organização em camadas.

🎯 Objetivo

Desenvolver uma aplicação capaz de cadastrar, consultar, atualizar e excluir ativos de TI, mantendo seus dados armazenados de forma persistente em um banco de dados SQLite.

💻 Tecnologias utilizadas

- **Java 25:** linguagem de programação.
- **Maven:** gerenciamento de dependências e construção do projeto.
- **SQLite:** banco de dados relacional.
- **SQLite JDBC:** integração entre Java e SQLite.
- **Git e GitHub:** controle de versão e hospedagem do código-fonte.

🖥️ Funcionalidades implementadas

Gerenciamento de ativos

O sistema contempla três categorias de equipamentos:

- Servidores.
- Estações de trabalho.
- Dispositivos de rede.

Cada ativo possui informações gerais, como código de patrimônio, modelo, data de aquisição e status.

Operações CRUD

O sistema implementa as quatro operações fundamentais de persistência:

- **Create:** cadastro de novos ativos.
- **Read:** consulta de ativos por ID e listagem de todos os registros.
- **Update:** atualização das informações dos ativos.
- **Delete:** exclusão de ativos cadastrados.

Persistência de dados

- Integração com banco de dados SQLite.
- Criação automática das tabelas.
- Utilização de PreparedStatement nas consultas parametrizadas.
- Utilização de transações nas operações de escrita do DAO.
- Relacionamento entre as tabelas por meio de chaves estrangeiras.
- Exclusão em cascata (ON DELETE CASCADE).

🏗️ Estrutura do projeto

```text
Techlab-Inventory/
│
├── pom.xml
├── README.md
├── .gitignore
│
└── src/
    └── main/
        └── java/
            └── Techlab/
                │
                ├── main.java
                │
                ├── db/
                │   ├── ConexaoSQLite.java
                │   └── CriadorTabelas.java
                │
                ├── excecoes/
                │   ├── EquipamentoNaoEncontradoException.java
                │   └── FalhaConexaoBDException.java
                │
                ├── interfaces/
                │   ├── IConexaoBD.java
                │   ├── INotificavel.java
                │   └── IPersistencia.java
                │
                ├── negocio/
                │   ├── AtivoTI.java
                │   ├── Servidor.java
                │   ├── EstacaoTrabalho.java
                │   ├── DispositivoRede.java
                │   └── ChamadoSuporte.java
                │
                ├── repositorio/
                │   ├── AtivoDAO.java
                │   └── AtivoRepository.java
                │
                └── servicos/
                    └── NotificadorChamado.java
```

🗄️ Estrutura do banco de dados

O banco SQLite utiliza quatro tabelas principais:

| Tabela | Descrição |
|---|---|
| `ativos` | Armazena os dados gerais dos equipamentos. |
| `servidores` | Armazena informações específicas dos servidores. |
| `estacoes_trabalho` | Armazena informações das estações de trabalho. |
| `dispositivos_rede` | Armazena informações dos dispositivos de rede. |

As tabelas específicas possuem relacionamento com a tabela `ativos` por meio da coluna `ativo_id`, utilizando chaves estrangeiras e exclusão em cascata.

O arquivo `techlab.db` é criado localmente na raiz do projeto durante a execução e não é versionado no GitHub.

⚙️ Como executar o projeto

Pré-requisitos

- Java JDK 25 ou compatível com a configuração do projeto.
- Maven instalado.
- Git instalado.

1. Clonar o repositório

```bash
git clone https://github.com/AntnzGabriel/Techlab-Inventory.git
```

2. Acessar a pasta do projeto

```bash
cd Techlab-Inventory
```

3. Compilar o projeto

```bash
mvn clean compile
```

4. Executar a aplicação

```bash
mvn exec:java -Dexec.mainClass="Techlab.main"
```

Na primeira execução, o sistema cria o banco de dados e suas tabelas automaticamente.

🧪 Validações realizadas

For
