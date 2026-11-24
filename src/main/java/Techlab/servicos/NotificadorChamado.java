
package Techlab.servicos;

import Techlab.interfaces.INotificavel;
import Techlab.negocio.ChamadoSuporte;

public class NotificadorChamado implements INotificavel {

    @Override
    public void emitirAlerta(ChamadoSuporte chamado) {

        if (chamado == null) {
            throw new IllegalArgumentException(
                "O chamado não pode ser nulo."
            );
        }

        System.out.println("===== ALERTA DE CHAMADO =====");
        System.out.println("ID: " + chamado.getId());
        System.out.println("Título: " + chamado.getTitulo());
        System.out.println("Prioridade: " + chamado.getPrioridade());
        System.out.println("Ativo: " +
            chamado.getAtivoRelacionado().getModelo());
        System.out.println("=============================");
    }
}
