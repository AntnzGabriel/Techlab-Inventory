
package Techlab.repositorio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import Techlab.excecoes.EquipamentoNaoEncontradoException;
import Techlab.interfaces.IPersistencia;
import Techlab.negocio.ChamadoSuporte;

public class ChamadoRepository
        implements IPersistencia<ChamadoSuporte> {

    private final Map<Integer, ChamadoSuporte> chamados =
            new HashMap<>();

    @Override
    public void salvar(ChamadoSuporte objeto) {

        if (objeto == null) {
            throw new IllegalArgumentException(
                "O chamado não pode ser nulo."
            );
        }

        if (chamados.containsKey(objeto.getId())) {
            throw new IllegalArgumentException(
                "Já existe um chamado com esse ID."
            );
        }

        chamados.put(objeto.getId(), objeto);
    }

    @Override
    public ChamadoSuporte buscarPorId(int id)
            throws EquipamentoNaoEncontradoException {

        ChamadoSuporte chamado = chamados.get(id);

        if (chamado == null) {
            throw new EquipamentoNaoEncontradoException(
                "Chamado com ID " + id + " não encontrado."
            );
        }

        return chamado;
    }

    @Override
    public List<ChamadoSuporte> listarTodos() {
        return new ArrayList<>(chamados.values());
    }

    @Override
    public void atualizar(ChamadoSuporte objeto)
            throws EquipamentoNaoEncontradoException {

        if (objeto == null) {
            throw new IllegalArgumentException(
                "O chamado não pode ser nulo."
            );
        }

        if (!chamados.containsKey(objeto.getId())) {
            throw new EquipamentoNaoEncontradoException(
                "Não é possível atualizar: chamado não encontrado."
            );
        }

        chamados.put(objeto.getId(), objeto);
    }

    @Override
    public void deletar(int id)
            throws EquipamentoNaoEncontradoException {

        if (!chamados.containsKey(id)) {
            throw new EquipamentoNaoEncontradoException(
                "Não é possível excluir: chamado não encontrado."
            );
        }

        chamados.remove(id);
    }

    public List<ChamadoSuporte> filtrarPorPrioridade(
            String prioridade) {

        List<ChamadoSuporte> resultado = new ArrayList<>();

        Iterator<ChamadoSuporte> iterator =
                chamados.values().iterator();

        while (iterator.hasNext()) {
            ChamadoSuporte chamado = iterator.next();

            if (chamado.getPrioridade().equals(prioridade)) {
                resultado.add(chamado);
            }
        }

        return resultado;
    }

    public List<ChamadoSuporte> filtrarPorStatus(String status) {

        List<ChamadoSuporte> resultado = new ArrayList<>();

        Iterator<ChamadoSuporte> iterator =
                chamados.values().iterator();

        while (iterator.hasNext()) {
            ChamadoSuporte chamado = iterator.next();

            if (chamado.getStatus().equals(status)) {
                resultado.add(chamado);
            }
        }

        return resultado;
    }

}
