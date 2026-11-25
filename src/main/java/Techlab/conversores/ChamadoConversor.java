
package Techlab.conversores;

import Techlab.dto.ChamadoDTO;
import Techlab.negocio.AtivoTI;
import Techlab.negocio.ChamadoSuporte;

public class ChamadoConversor {

    // Converte chamado em DTO
    public ChamadoDTO paraDTO(ChamadoSuporte chamado) {

        if (chamado == null) {
            throw new IllegalArgumentException(
                "O chamado não pode ser nulo."
            );
        }

        if (chamado.getAtivoRelacionado() == null) {
            throw new IllegalArgumentException(
                "O chamado não possui um ativo relacionado."
            );
        }

        ChamadoDTO dto = new ChamadoDTO();

        dto.setId(chamado.getId());
        dto.setTitulo(chamado.getTitulo());
        dto.setDescricao(chamado.getDescricao());
        dto.setPrioridade(chamado.getPrioridade());
        dto.setStatus(chamado.getStatus());
        dto.setDataAbertura(chamado.getDataAbertura());
        dto.setAtivoRelacionadoId(
            chamado.getAtivoRelacionado().getId()
        );

        return dto;
    }

    // Converte DTO em chamado
    public ChamadoSuporte paraEntidade(
            ChamadoDTO dto,
            AtivoTI ativoRelacionado) {

        if (dto == null) {
            throw new IllegalArgumentException(
                "O DTO não pode ser nulo."
            );
        }

        if (ativoRelacionado == null) {
            throw new IllegalArgumentException(
                "O ativo relacionado não pode ser nulo."
            );
        }

        if (dto.getAtivoRelacionadoId() != ativoRelacionado.getId()) {
            throw new IllegalArgumentException(
                "O ID do ativo não corresponde ao DTO."
            );
        }

        return new ChamadoSuporte(
            dto.getId(),
            dto.getTitulo(),
            dto.getDescricao(),
            dto.getPrioridade(),
            dto.getStatus(),
            dto.getDataAbertura(),
            ativoRelacionado
        );
    }
}

