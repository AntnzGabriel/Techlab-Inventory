
package Techlab.conversores;

import Techlab.dto.AtivoDTO;
import Techlab.negocio.AtivoTI;
import Techlab.negocio.Servidor;
import Techlab.negocio.EstacaoTrabalho;
import Techlab.negocio.DispositivoRede;

public class AtivoConversor {

    // Converte objeto de negócio em DTO
    public AtivoDTO paraDTO(AtivoTI ativo) {

        if (ativo == null) {
            throw new IllegalArgumentException(
                "O ativo não pode ser nulo."
            );
        }

        AtivoDTO dto = new AtivoDTO();

        dto.setId(ativo.getId());
        dto.setCodigoPatrimonio(ativo.getCodigoPatrimonio());
        dto.setModelo(ativo.getModelo());
        dto.setDataAquisicao(ativo.getDataAquisicao());
        dto.setStatus(ativo.getStatus());

        if (ativo instanceof Servidor servidor) {

            dto.setTipo("SERVIDOR");
            dto.setIpEstatico(servidor.getIpEstatico());
            dto.setSistemaOperacional(
                servidor.getSistemaOperacional()
            );
            dto.setCapacidadeRAM(servidor.getCapacidadeRAM());

        } else if (ativo instanceof EstacaoTrabalho estacao) {

            dto.setTipo("ESTACAO_TRABALHO");
            dto.setDepartamento(estacao.getDepartamento());
            dto.setUsuarioAlocado(estacao.getUsuarioAlocado());

        } else if (ativo instanceof DispositivoRede dispositivo) {

            dto.setTipo("DISPOSITIVO_REDE");
            dto.setQuantidadePortas(
                dispositivo.getQuantidadePortas()
            );

        } else {
            throw new IllegalArgumentException(
                "Tipo de ativo não reconhecido."
            );
        }

        return dto;
    }

    // Converte DTO em objeto de negócio
    public AtivoTI paraEntidade(AtivoDTO dto) {

        if (dto == null) {
            throw new IllegalArgumentException(
                "O DTO não pode ser nulo."
            );
        }

        if (dto.getTipo() == null || dto.getTipo().isBlank()) {
            throw new IllegalArgumentException(
                "Tipo de ativo não informado."
            );
        }

        switch (dto.getTipo()) {

            case "SERVIDOR":
                if (dto.getCapacidadeRAM() == null) {
                    throw new IllegalArgumentException(
                        "Capacidade RAM não informada."
                    );
                }

                return new Servidor(
                    dto.getId(),
                    dto.getCodigoPatrimonio(),
                    dto.getModelo(),
                    dto.getDataAquisicao(),
                    dto.getStatus(),
                    dto.getIpEstatico(),
                    dto.getSistemaOperacional(),
                    dto.getCapacidadeRAM()
                );

            case "ESTACAO_TRABALHO":
                return new EstacaoTrabalho(
                    dto.getId(),
                    dto.getCodigoPatrimonio(),
                    dto.getModelo(),
                    dto.getDataAquisicao(),
                    dto.getStatus(),
                    dto.getDepartamento(),
                    dto.getUsuarioAlocado()
                );

            case "DISPOSITIVO_REDE":
                if (dto.getQuantidadePortas() == null) {
                    throw new IllegalArgumentException(
                        "Quantidade de portas não informada."
                    );
                }

                return new DispositivoRede(
                    dto.getId(),
                    dto.getCodigoPatrimonio(),
                    dto.getModelo(),
                    dto.getDataAquisicao(),
                    dto.getStatus(),
                    dto.getQuantidadePortas()
                );

            default:
                throw new IllegalArgumentException(
                    "Tipo de ativo inválido: " + dto.getTipo()
                );
        }
    }
}
