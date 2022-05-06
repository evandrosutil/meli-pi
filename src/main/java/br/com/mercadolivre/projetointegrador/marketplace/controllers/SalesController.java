package br.com.mercadolivre.projetointegrador.marketplace.controllers;

import br.com.mercadolivre.projetointegrador.marketplace.dtos.SalesDTO;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.UnauthorizedException;
import br.com.mercadolivre.projetointegrador.marketplace.services.PurchaseService;
import br.com.mercadolivre.projetointegrador.security.model.AppUser;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/marketplace/sales")
@Tag(name = "[Marketplace] - Sales")
public class SalesController {

  @Autowired private PurchaseService purchaseService;

  @GetMapping("/{sellerId}")
  public ResponseEntity<List<SalesDTO>> listSellerSales(
    @PathVariable Long sellerId,
    @RequestParam(required = false) Integer last,
    Authentication authentication) throws UnauthorizedException {
    AppUser seller = (AppUser) authentication.getPrincipal();
    if (!sellerId.equals(seller.getId())) {
      throw new UnauthorizedException("Não autorizado a realizar essa consulta!");
    }
    List<SalesDTO> sales = purchaseService.listSales(sellerId, last);

    return ResponseEntity.ok(sales);
  }

  @GetMapping("/{sellerId}/{adId}")
  public ResponseEntity<List<SalesDTO>> getProductSales(
    @PathVariable Long sellerId,
    @PathVariable(required = false) Long adId,
    @RequestParam(required = false) Integer last,
    Authentication authentication) throws NotFoundException, UnauthorizedException {

    AppUser seller = (AppUser) authentication.getPrincipal();
    if (!sellerId.equals(seller.getId())) {
      throw new UnauthorizedException("Não autorizado a realizar essa consulta!");
    }

    List<SalesDTO> adSales = purchaseService.getProductSales(sellerId, adId, last);

    return ResponseEntity.ok(adSales);
  }
}
