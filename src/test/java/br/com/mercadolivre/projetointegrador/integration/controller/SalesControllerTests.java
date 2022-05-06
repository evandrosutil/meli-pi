package br.com.mercadolivre.projetointegrador.integration.controller;

import br.com.mercadolivre.projetointegrador.marketplace.model.Ad;
import br.com.mercadolivre.projetointegrador.marketplace.model.AdPurchase;
import br.com.mercadolivre.projetointegrador.marketplace.model.Purchase;
import br.com.mercadolivre.projetointegrador.marketplace.repository.AdPurchaseRepository;
import br.com.mercadolivre.projetointegrador.marketplace.repository.AdRepository;
import br.com.mercadolivre.projetointegrador.marketplace.repository.PurchaseRepository;
import br.com.mercadolivre.projetointegrador.test_utils.IntegrationTestUtils;
import br.com.mercadolivre.projetointegrador.test_utils.WithMockCustomerUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@WithMockCustomerUser
public class SalesControllerTests {

  private final String SALES_URL = "/api/v1/marketplace/sales";
  @Autowired private MockMvc mockMvc;
  @Autowired private AdPurchaseRepository adPurchaseRepository;
  @Autowired private PurchaseRepository purchaseRepository;
  @Autowired private AdRepository adRepository;
  @Autowired private IntegrationTestUtils integrationTestUtils;

  private Ad createAd(String name, Long seller) {
    Ad ad = integrationTestUtils.createAdDTO().DTOtoModel();
    ad.setName(name);
    ad.setSellerId(seller);
    adRepository.save(ad);

    return ad;
  }

  private AdPurchase createAdPurchase(Long id, Ad ad, LocalDate date, Purchase purchase) {
    AdPurchase adPurchase = new AdPurchase();
    adPurchase.setId(id);
    adPurchase.setAd(ad);
    adPurchase.setSellerId(ad.getSellerId());
    adPurchase.setQuantity(10);
    adPurchase.setDate(date);
    adPurchase.setPurchase(purchase);

    return adPurchase;
  }

  @BeforeEach
  public void createPurchase() {

    Purchase purchase = new Purchase();
    purchase.setBuyerId(1L);
    purchaseRepository.save(purchase);

    Ad adOne = createAd("Ad One", 1L);
    Ad adTwo = createAd("Ad Two", 2L);
    Ad adThree = createAd("Ad Three", 1L);

    List<AdPurchase> adPurchase = List.of(
      createAdPurchase(1L, adOne, LocalDate.now(), purchase),
      createAdPurchase(2L, adTwo, LocalDate.now(), purchase),
      createAdPurchase(3L, adThree, LocalDate.now().minusDays(45), purchase),
      createAdPurchase(4L, adOne, LocalDate.now().minusDays(45), purchase));

    adPurchaseRepository.saveAll(adPurchase);
  }

  @Test
  @DisplayName("SalesController - GET - /api/v1/marketplace/sales/{sellerId}")
  public void testListSellerSales() throws Exception {

    mockMvc
      .perform(MockMvcRequestBuilders.get(SALES_URL + "/1"))
      .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));
  }

  @Test
  @DisplayName("SalesController - GET - /api/v1/marketplace/sales/{sellerId}?last=")
  public void testListSellerSalesL10D() throws Exception {

    mockMvc
      .perform(MockMvcRequestBuilders.get(SALES_URL + "/1?last=10"))
      .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
  }

  @Test
  @DisplayName("SalesController - GET - /api/v1/marketplace/sales/{sellerId}/{adId}")
  public void testgetProductSales() throws Exception {

    mockMvc
      .perform(MockMvcRequestBuilders.get(SALES_URL + "/1/1"))
      .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
  }

  @Test
  @DisplayName("SalesController - GET - /api/v1/marketplace/sales/{sellerId}/{adId}?last=")
  public void testgetProductSalesL10D() throws Exception {

    mockMvc
      .perform(MockMvcRequestBuilders.get(SALES_URL + "/1/1?last=10"))
      .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
  }
}
