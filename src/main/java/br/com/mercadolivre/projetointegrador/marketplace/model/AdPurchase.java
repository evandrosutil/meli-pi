package br.com.mercadolivre.projetointegrador.marketplace.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "ad_purchase")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class AdPurchase {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "ad_id", referencedColumnName = "id", nullable = false)
  private Ad ad;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "purchase_id", referencedColumnName = "id", nullable = false)
  private Purchase purchase;

  private Integer quantity;

  private BigDecimal price;

  private Integer discount;

  @Column(name = "seller_id")
  private Long sellerId;

  @CreatedDate
  private LocalDate date;
}
