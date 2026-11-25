TechLab Inventory

**Sistema de Gerenciamento de Ativos de Tecnologia da Informação (TI).**

O TechLab Inventory é um projeto acadêmico desenvolvido em Java com o objetivo de facilitar o gerenciamento de ativos de TI e chamados de suporte, aplicando conceitos de Programação Orientada a Objetos (POO), persistência de dados e organização em camadas.

🎯 Objetivo

Desenvolver uma aplicação capaz de cadastrar, consultar, atualizar e excluir ativos de TI e chamados de suporte, mantendo seus dados organizados em memória e armazenados de forma persistente em arquivos locais e banco de dados SQLite.

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

Cada ativo possui informações gerais, como código de patrimônio, modelo, data de aquisição e status, além de atributos específicos de sua categoria.

Gerenciamento de chamados

O sistema permite gerenciar chamados de suporte técnico, contendo:

- Título e descrição.
- Prioridade: Baixa, Média e Alta.
- Status: Aberto, EmAndamento e Fechado.
- Data de abertura.
- Referência direta ao ativo relacionado.

Operações CRUD

O projeto implementa as quatro operações fundamentais de persistência:

- **Create:** cadastro de ativos e chamados.
- **Read:** consulta por ID e listagem de registros.
- **Update:** atualização de informações.
- **Delete:** exclusão de registros.

As operações são implementadas por meio das classes DAO e Repository.

Organização e gerenciamento em memória

- Utilização de coleções genéricas, como HashMap e List.
- Busca e filtragem de chamados por prioridade e status.
- Utilização de iteradores para percorrer coleções.
- Interfaces genéricas para padronização das operações de persistência.

DTOs e conversores

O projeto utiliza Data Transfer Objects (DTOs) para transportar dados entre as camadas da aplicação.

Os conversores são responsáveis por transformar objetos de negócio em DTOs e reconstruir entidades a partir dessas estruturas.

Persistência de dados

O sistema utiliza duas formas de persistência:

**Banco de dados SQLite**
- Criação automática das tabelas.
- Operações CRUD por meio de DAO.
- Utilização de PreparedStatement para consultas parametrizadas.
- Utilização de transações nas operações de escrita do AtivoDAO.
- Relacionamentos por meio de chaves estrangeiras.
- Exclusão em cascata nas tabelas específicas dos ativos.

**Arquivos TXT**
- Gravação e recuperação de ativos.
- Gravação e recuperação de chamados.
- Conversão dos dados para DTOs antes da gravação.
- Codificação Base64 dos campos textuais para preservar caracteres especiais e evitar conflitos com o delimitador.

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
                ├── conversores/
                │   ├── AtivoConversor.java
                │   └── ChamadoConversor.java
                │
                ├── db/
                │   ├── ConexaoSQLite.java
                │   └── CriadorTabelas.java
                │
                ├── dto/
                │   ├── AtivoDTO.java
                │   └── ChamadoDTO.java
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
                ├── persistencia/
                │   └── PersistenciaArquivo.java
                │
                ├── repositorio/
                │   ├── AtivoDAO.java
                │   ├── AtivoRepository.java
                │   ├── ChamadoDAO.java
                │   └── ChamadoRepository.java
                │
                └── servicos/
                    └── NotificadorChamado.java
```

🗄️ Estrutura do banco de dados

O banco SQLite utiliza cinco tabelas principais:

| Tabela | Descrição |
|---|---|
| `ativos` | Armazena os dados gerais dos equipamentos. |
| `servidores` | Armazena informações específicas dos servidores. |
| `estacoes_trabalho` | Armazena informações das estações de trabalho. |
| `dispositivos_rede` | Armazena informações dos dispositivos de rede. |
| `chamados` | Armazena os chamados de suporte e seus ativos relacionados. |

As tabelas específicas dos equipamentos possuem relacionamento com a tabela `ativos` por meio da coluna `ativo_id`.

A tabela `chamados` também utiliza uma chave estrangeira para relacionar cada chamado ao ativo correspondente.

O arquivo `techlab.db` é criado localmente na raiz do projeto durante a execução e não precisa ser versionado no GitHub.

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

Foram realizados testes de compilação e execução, incluindo:

- Compilação completa do projeto com Maven.
- Testes de cadastro, consulta, atualização e exclusão de ativos.
- Testes de cadastro, consulta, atualização e exclusão de chamados.
- Verificação dos relacionamentos entre ativos e chamados.
- Testes de gravação e recuperação de ativos em arquivos TXT.
- Testes de gravação e recuperação de chamados em arquivos TXT.
- Verificação da utilização de PreparedStatement nas operações SQL.

📚 Conceitos de POO aplicados

O desenvolvimento do projeto permitiu aplicar os seguintes conceitos:

- **Encapsulamento:** proteção dos atributos e controle de acesso por métodos.
- **Herança:** especialização da classe abstrata AtivoTI.
- **Abstração:** definição de estruturas e comportamentos comuns.
- **Polimorfismo:** utilização de referências da classe AtivoTI para diferentes subclasses.
- **Interfaces:** padronização de comportamentos e operações.
- **Tratamento de exceções:** criação de exceções personalizadas para situações específicas.
- **Coleções e genéricos:** gerenciamento de objetos em memória.

👨‍💻 Desenvolvedor

Projeto acadêmico desenvolvido por **Antônio Gabriel**, como parte da disciplina de Programação Orientada a Objetos e Persistência de Dados.

**Repositório:** https://github.com/AntnzGabriel/Techlab-Inventory