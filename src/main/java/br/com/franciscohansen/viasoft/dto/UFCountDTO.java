package br.com.franciscohansen.viasoft.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UFCountDTO implements Serializable {
    private String autorizador;
    private Long count;
}
