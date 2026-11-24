
package Techlab.repositorio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Techlab.excecoes.EquipamentoNaoEncontradoException;
import Techlab.interfaces.IPersistencia;
import Techlab.negocio.AtivoTI;

public class AtivoRepository implements IPersistencia<AtivoTI> {

    private final Map<Integer, AtivoTI> ativos = new HashMap<>();

    @Override
    public void salvar(AtivoTI objeto) {
        if (objeto == null) {
            throw new IllegalArgumentException(
                "O ativo não pode ser nulo."
            );
        }

        if (ativos.containsKey(objeto.getId())) {
            throw new IllegalArgumentException(
                "Já existe um ativo com esse ID."
            );
        }

        ativos.put(objeto.getId(), objeto);
    }

    @Override
    public AtivoTI buscarPorId(int id)
            throws EquipamentoNaoEncontradoException {

        AtivoTI ativo = ativos.get(id);

        if (ativo == null) {
            throw new EquipamentoNaoEncontradoException(
                "Ativo com ID " + id + " não encontrado."
            );
        }

        return ativo;
    }

    @Override
    public List<AtivoTI> listarTodos() {
        return new ArrayList<>(ativos.values());
    }

    @Override
    public void atualizar(AtivoTI objeto)
            throws EquipamentoNaoEncontradoException {

        if (objeto == null) {
            throw new IllegalArgumentException(
                "O ativo não pode ser nulo."
            );
        }

        if (!ativos.containsKey(objeto.getId())) {
            throw new EquipamentoNaoEncontradoException(
                "Não é possível atualizar: ativo não encontrado."
            );
        }

        ativos.put(objeto.getId(), objeto);
    }

    @Override
    public void deletar(int id)
            throws EquipamentoNaoEncontradoException {

        if (!ativos.containsKey(id)) {
            throw new EquipamentoNaoEncontradoException(
                "Não é possível excluir: ativo não encontrado."
            );
        }

        ativos.remove(id);
    }
}
