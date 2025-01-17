package br.com.mercadolivre.projetointegrador.unit.service;

import br.com.mercadolivre.projetointegrador.marketplace.dtos.CreateOrUpdateAdDTO;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.UnauthorizedException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Ad;
import br.com.mercadolivre.projetointegrador.marketplace.repository.AdBatchesRepository;
import br.com.mercadolivre.projetointegrador.marketplace.repository.AdRepository;
import br.com.mercadolivre.projetointegrador.marketplace.services.AdService;
import br.com.mercadolivre.projetointegrador.warehouse.enums.CategoryEnum;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.NotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.repository.BatchRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AdServiceTests {

  @Mock AdRepository adRepository;

  @Mock AdBatchesRepository adBatchesRepository;

  @Mock BatchRepository batchRepository;

  @InjectMocks AdService adService;

  @Test
  @DisplayName(
      "Given valid values with three batches_ids, when call adService.createAd, then should call"
          + " adRepository one time and adBatchesRespository three times.")
  public void testCreateAd() {
    Integer[] batchesIds = new Integer[] {1, 2, 3};
    CreateOrUpdateAdDTO createAdDTO = new CreateOrUpdateAdDTO();
    createAdDTO.setBatchesId(List.of(batchesIds));
    createAdDTO.setCategory(CategoryEnum.FS);
    createAdDTO.setPrice(BigDecimal.valueOf(9.90));
    createAdDTO.setDiscount(5);

    adService.createAd(1L, createAdDTO);

    Ad ad = new Ad();
    ad.setSellerId(1L);
    ad.setName(createAdDTO.getName());
    ad.setPrice(createAdDTO.getPrice());
    ad.setDiscount(createAdDTO.getDiscount());
    ad.setCategory(createAdDTO.getCategory());

    Mockito.verify(adRepository, Mockito.times(1)).save(ad);
    Mockito.verify(adBatchesRepository, Mockito.times(3)).save(Mockito.any());
  }

  @Test
  @DisplayName(
      "Given a string as \"toddy\", when call listAds without and with these string, should call on"
          + " repository findAll and findAdsByLikeName methods.")
  public void listAdsWithNoFilter() {
    adService.listAds();

    Mockito.verify(adRepository, Mockito.times(1)).findAll();
  }

  @Test
  @DisplayName(
      "Given a string as \"toddy\", should call on" + " repository findAdsByLikeName method.")
  public void listAdsWithNameFilter() {

    adService.listAds("toddy", null);

    Mockito.verify(adRepository, Mockito.times(1)).findAdsByLikeName("toddy");
  }

  @Test
  @DisplayName("Given only a Product category, should call on repository findAllByCategory method.")
  public void listAdsWithCategoryFilter() {
    adService.listAds(null, CategoryEnum.RF);

    Mockito.verify(adRepository, Mockito.times(1)).findAllByCategory(CategoryEnum.RF);
  }

  @Test
  @DisplayName(
      "Given a product category and name, should call on repository findAllByCategoryAndNameLike"
          + " method")
  public void listAdsWithCategoryAndNameFilter() {
    adService.listAds("toddy", CategoryEnum.RF);

    Mockito.verify(adRepository, Mockito.times(1))
        .findAllByCategoryAndNameLike(CategoryEnum.RF, "toddy");
  }

  @Test
  @DisplayName(
      "Given a seller id, when call listAdsByCustomerId, then should call on repository"
          + " findAllBySellerId method.")
  public void listAdsBySellerId() {
    adService.listAdsByCustomerId(1L);

    Mockito.verify(adRepository, Mockito.times(1)).findAllBySellerId(1L);
  }

  @Test
  @DisplayName("Given an existing ad id, when call findAdById, then should return this one.")
  public void listAdById() {
    Mockito.when(adRepository.findById(1L)).thenReturn(Optional.of(new Ad()));
    adService.findAdById(1L);

    Mockito.verify(adRepository, Mockito.times(1)).findById(1L);
  }

  @Test
  @DisplayName(
      "Given a non-existing ad id, when call findAdById, then should throw an error containing"
          + " \"Anúncio não localizado.\"")
  public void throwsWhenAdDoesNotExists() {
    Mockito.when(adRepository.findById(1L)).thenReturn(Optional.empty());
    Exception thrown =
        Assertions.assertThrows(NotFoundException.class, () -> adService.findAdById(1L));

    Assertions.assertEquals("Anúncio não localizado.", thrown.getMessage());
  }

  @Test
  @DisplayName(
      "Given an existing ad id, when call deleteAd, then delete method from repository should be"
          + " called once")
  public void deleteAd() throws UnauthorizedException {
    Ad ad = new Ad();
    ad.setSellerId(1L);
    Mockito.when(adRepository.findById(10L)).thenReturn(Optional.of(ad));

    adService.deleteAd(1L, 10L);

    Mockito.verify(adRepository, Mockito.times(1)).delete(ad);
  }

  @Test
  @DisplayName(
      "Given a non-existing ad id, when call deleteAd, then should throw an error containing \"Não"
          + " é permitido excluir o anúncio de outro usuário.\"")
  public void throwsWhenDeleteAnotherCustomerAd() {
    Ad ad = new Ad();
    ad.setSellerId(10L);
    Mockito.when(adRepository.findById(10L)).thenReturn(Optional.of(ad));

    Exception thrown =
        Assertions.assertThrows(UnauthorizedException.class, () -> adService.deleteAd(1L, 10L));

    Assertions.assertEquals(
        "Não é permitido excluir o anúncio de outro usuário.", thrown.getMessage());
  }
}
