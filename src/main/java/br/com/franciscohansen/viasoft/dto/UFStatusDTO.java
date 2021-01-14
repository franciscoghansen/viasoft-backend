package br.com.franciscohansen.viasoft.dto;

import br.com.franciscohansen.viasoft.enums.EEstado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UFStatusDTO implements Serializable {
    private EEstado status;
}
