package br.com.mercadolivre.projetointegrador.warehouse.service;

import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.model.InboundOrder;
import br.com.mercadolivre.projetointegrador.warehouse.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public List<Batch> findProductOnWarehouse(Long managerId, Long productId, String sortType) {

        // TODO:
        //  - Procurar manager
        //      - Se não encontrar ele lança um erro
        //  - Procurar a Section do manager (Section managerSection)
        //  - Procurar os lotes na Section (List<Batch> foundedBatches)
        //      - Se não encontrar levantar uma RuntimeException

        return new ArrayList<>();
    }

    public List<Object> saveBatchInSection(InboundOrder inboundOrder) {

        //TODO: validar inboundOrder
        //TODO: salvar inboundOrder
        //TODO: retornar lotes salvos

        //TODO: Mudar para list<Batch>
        List<Object> addedBatches = new ArrayList<>();

        for (Object batch : inboundOrder.getBatches()) {
            //TODO: setar id da section
            addedBatches.add(batch);
            //TODO: addedBatches.add(batchService.add(batch))
        }


        return addedBatches;
    }
}
