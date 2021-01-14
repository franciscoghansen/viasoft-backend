package br.com.franciscohansen.viasoft.model;

import br.com.franciscohansen.viasoft.enums.EEstado;
import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StatusUF extends AbstractModel {

    private String autorizador;
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @Enumerated(EnumType.STRING)
    private EEstado autorizacao;
    @Enumerated(EnumType.STRING)
    private EEstado retAutorizacao;
    @Enumerated(EnumType.STRING)
    private EEstado inutilizacao;
    @Enumerated(EnumType.STRING)
    private EEstado consultaProtocolo;
    @Enumerated(EnumType.STRING)
    private EEstado statusServico;
    @Enumerated(EnumType.STRING)
    private EEstado consultaCadastro;
    @Enumerated(EnumType.STRING)
    private EEstado recepcaoEvento;


}
