package br.com.mercadolivre.projetointegrador.integration.controller;

import br.com.mercadolivre.projetointegrador.test_utils.SectionServiceTestUtils;
import br.com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import br.com.mercadolivre.projetointegrador.warehouse.repository.ManagerRepository;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;
import br.com.mercadolivre.projetointegrador.warehouse.repository.WarehouseRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SectionIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private SectionServiceTestUtils sectionServiceTestUtils;

    private final String SECTION_URL = "/api/v1/section";

    @BeforeAll
    public void beforeAll(){
        sectionRepository.save(sectionServiceTestUtils.getIntegrationMockSection());
    }

    @Test
    public void shouldReturnObjectContainingSection() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                get(SECTION_URL.concat("/{id}"), 1L))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
