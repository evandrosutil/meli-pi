package br.com.mercadolivre.projetointegrador.marketplace.dtos;

import br.com.mercadolivre.projetointegrador.marketplace.model.AdPurchase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SalesDTO {
  private String adName;
  private Integer totalQuantity;
  private BigDecimal totalSale;
  private LocalDate date;

  private static SalesDTO adPurchaseToSale(AdPurchase adPurchase) {
    SalesDTO sale = new SalesDTO();
    sale.adName = adPurchase.getAd().getName();
    sale.totalQuantity = adPurchase.getQuantity();
    sale.totalSale = adPurchase.getPrice();
    sale.date = adPurchase.getDate();

    return sale;
  }

  public static List<SalesDTO> adPurchasesToSales(List<AdPurchase> adPurchases) {
    List<SalesDTO> sales = new ArrayList<>();
    for (AdPurchase adPurchase:
      adPurchases) {
      sales.add(adPurchaseToSale(adPurchase));
    }
    return sales;
  }
}
