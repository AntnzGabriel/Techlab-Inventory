

package Techlab.db;

import Techlab.excecoes.FalhaConexaoBDException;

public class CriadorTabelas {

    private final ConexaoSQLite banco;

    public CriadorTabelas(ConexaoSQLite banco) {
        if (banco == null) {
            throw new IllegalArgumentException(
                "Conexão com banco inválida."
            );
        }

        this.banco = banco;
    }

    public void criarTabelaAtivos()
            throws FalhaConexaoBDException {

        String sql = """
            CREATE TABLE IF NOT EXISTS ativos (
                id INTEGER PRIMARY KEY,
                codigo_patrimonio TEXT NOT NULL UNIQUE,
                modelo TEXT NOT NULL,
                data_aquisicao TEXT NOT NULL,
                status TEXT NOT NULL,
                tipo TEXT NOT NULL CHECK (
                    tipo IN (
                        'SERVIDOR',
                        'ESTACAO_TRABALHO',
                        'DISPOSITIVO_REDE'
                    )
                )
            );
            """;

        banco.executarQuery(sql);
        System.out.println("Tabela ativos criada com sucesso!");
    }

    public void criarTabelaServidores()
            throws FalhaConexaoBDException {

        String sql = """
            CREATE TABLE IF NOT EXISTS servidores (
                ativo_id INTEGER PRIMARY KEY,
                ip_estatico TEXT NOT NULL,
                sistema_operacional TEXT NOT NULL,
                capacidade_ram INTEGER NOT NULL,
                FOREIGN KEY (ativo_id)
                    REFERENCES ativos(id)
                    ON DELETE CASCADE
            );
            """;

        banco.executarQuery(sql);
        System.out.println("Tabela servidores criada com sucesso!");
    }

    public void criarTabelaEstacoesTrabalho()
            throws FalhaConexaoBDException {

        String sql = """
            CREATE TABLE IF NOT EXISTS estacoes_trabalho (
                ativo_id INTEGER PRIMARY KEY,
                departamento TEXT NOT NULL,
                usuario_alocado TEXT NOT NULL,
                FOREIGN KEY (ativo_id)
                    REFERENCES ativos(id)
                    ON DELETE CASCADE
            );
            """;

        banco.executarQuery(sql);

        System.out.println(
            "Tabela estacoes_trabalho criada com sucesso!"
        );
    }

    public void criarTabelaDispositivosRede()
            throws FalhaConexaoBDException {

        String sql = """
            CREATE TABLE IF NOT EXISTS dispositivos_rede (
                ativo_id INTEGER PRIMARY KEY,
                quantidade_portas INTEGER NOT NULL,
                FOREIGN KEY (ativo_id)
                    REFERENCES ativos(id)
                    ON DELETE CASCADE
            );
            """;

        banco.executarQuery(sql);

        System.out.println(
            "Tabela dispositivos_rede criada com sucesso!"
        );
    }

public void criarTabelaChamados()
        throws FalhaConexaoBDException {

    String sql = """
        CREATE TABLE IF NOT EXISTS chamados (
            id INTEGER PRIMARY KEY,
            titulo TEXT NOT NULL,
            descricao TEXT NOT NULL,
            prioridade TEXT NOT NULL CHECK (
                prioridade IN ('Baixa', 'Media', 'Alta')
            ),
            status TEXT NOT NULL CHECK (
                status IN ('Aberto', 'EmAndamento', 'Fechado')
            ),
            data_abertura TEXT NOT NULL,
            ativo_id INTEGER NOT NULL,
            FOREIGN KEY (ativo_id)
                REFERENCES ativos(id)
        );
        """;

    banco.executarQuery(sql);

    System.out.println("Tabela chamados criada com sucesso!");
}

    public void criarTodasTabelas()
            throws FalhaConexaoBDException {

        criarTabelaAtivos();
        criarTabelaServidores();
        criarTabelaEstacoesTrabalho();
        criarTabelaDispositivosRede();
        criarTabelaChamados();
        System.out.println(
            "Todas as tabelas foram criadas com sucesso!"
        );
    }
}


