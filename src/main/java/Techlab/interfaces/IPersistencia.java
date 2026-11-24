
package Techlab.interfaces;

import java.util.List;

import Techlab.excecoes.EquipamentoNaoEncontradoException;
import Techlab.excecoes.FalhaConexaoBDException;

public interface IPersistencia<T> {

    void salvar(T objeto) throws FalhaConexaoBDException;

    T buscarPorId(int id)
            throws EquipamentoNaoEncontradoException,
                   FalhaConexaoBDException;

    List<T> listarTodos() throws FalhaConexaoBDException;

    void atualizar(T objeto)
            throws EquipamentoNaoEncontradoException,
                   FalhaConexaoBDException;

    void deletar(int id)
            throws EquipamentoNaoEncontradoException,
                   FalhaConexaoBDException;
}


