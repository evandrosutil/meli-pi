package br.com.mercadolivre.projetointegrador.warehouse.dto.response;

import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "batchStock")
public class SectionBatchesDTO {

    private String warehouse_code;
    private Long section_code;
    private Long productId;
    private List<BatchDTO> batchStock;
}
