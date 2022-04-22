package br.com.mercadolivre.projetointegrador.unit;

import br.com.mercadolivre.projetointegrador.test_utils.SectionServiceTestUtils;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;
import br.com.mercadolivre.projetointegrador.warehouse.service.SectionService;
import org.hibernate.PropertyNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SectionServiceTests {

    @Mock
    private SectionRepository sectionRepository;

    @InjectMocks
    private SectionService service;


    @Test
    public void shouldReturnSectionById(){
        Section expected = SectionServiceTestUtils.getMockSection();
        Mockito.when(sectionRepository.findById(Mockito.any())).thenReturn(Optional.of(expected));

        Section result = service.findSectionById(1L);

        assertEquals(expected, result);

    }

    @Test
    public void shouldThrowErrorWhenNotFoundById(){
        Mockito.when(sectionRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        assertThrows(PropertyNotFoundException.class, () -> service.findSectionById(2L));
    }
}