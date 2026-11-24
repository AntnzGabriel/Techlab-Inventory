package Techlab.interfaces;

import Techlab.excecoes.FalhaConexaoBDException;

public interface IConexaoBD {

    void conectar() throws FalhaConexaoBDException;

    void executarQuery(String sql) throws FalhaConexaoBDException;

}

