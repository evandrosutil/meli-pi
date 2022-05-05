package br.com.mercadolivre.projetointegrador.marketplace.dtos;

import br.com.mercadolivre.projetointegrador.marketplace.model.AdPurchase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class SalesDTO {
  private String adName;
  private Integer totalQuantity;
  private BigDecimal totalSale;
  private Date date;

  public static SalesDTO adPurchaseToSales(AdPurchase adPurchase) {
    SalesDTO sale = new SalesDTO();
    sale.adName = adPurchase.getAd().getName();
    sale.totalQuantity = adPurchase.getQuantity();
    sale.totalSale = adPurchase.getPrice();
    sale.date = adPurchase.getDate();


    return sale;
  }
}
