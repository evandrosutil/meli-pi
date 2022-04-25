package br.com.mercadolivre.projetointegrador.unit.service;

import br.com.mercadolivre.projetointegrador.marketplace.exception.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Product;
import br.com.mercadolivre.projetointegrador.marketplace.repository.ProductRepository;
import br.com.mercadolivre.projetointegrador.marketplace.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product newTestProduct;

    @BeforeEach
    public void createMockProduct() {
        newTestProduct = new Product();
        newTestProduct.setId(1L);
        newTestProduct.setName("new product");
        newTestProduct.setPrice(BigDecimal.valueOf(10.0));
        newTestProduct.setCategory("new category");
    }

    @Test
    @DisplayName("Given a valid product, when call createProduct, then createProduct must be called once.")
    public void createProductMustBeCalledOnce() throws NotFoundException {

        Mockito.when(productRepository.save(Mockito.any())).thenReturn(newTestProduct);

        this.productService.createProduct(newTestProduct);

        Mockito.verify(productRepository, Mockito.times(1)).save(newTestProduct);
    }

    @Test
    @DisplayName("Given an existing product, when call find method with id from that product, then return this one")
    public void shouldReturnProductById() throws NotFoundException {

        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(newTestProduct));

        Product result = productService.findById(1L);

        assertEquals(newTestProduct, result);
    }

    @Test
    @DisplayName("Given an id from a non existing product, when call find by id method, then throw an error")
    public void shouldThrownNotFoundExceptionWhenIdDoesNotExists() {

        Exception thrown = Assertions.assertThrows(
                NotFoundException.class,
                () ->  productService.findById(10L)
        );

        Assertions.assertEquals("Produto não encontrado.", thrown.getMessage());
    }

    @Test
    @DisplayName("Given an valid id and valid updated information, when call updateProduct, then updateProduct must be called once.")
    public void shouldUpdateProduct() throws NotFoundException {

        Product updatedProduct = new Product();
        updatedProduct.setName("novo nome");
        updatedProduct.setPrice(BigDecimal.valueOf(50.0));
        updatedProduct.setCategory("nova categoria");

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.ofNullable(newTestProduct));

        newTestProduct.setName("novo nome");
        newTestProduct.setPrice(BigDecimal.valueOf(50.0));
        newTestProduct.setCategory("nova categoria");

        Mockito.when(productRepository.save(newTestProduct)).thenReturn(newTestProduct);
        productService.updateProduct(1L, updatedProduct);

        Mockito.verify(productRepository, Mockito.times(1)).save(newTestProduct);
    }

    @Test
    @DisplayName("Given a valid id, when call delete, the delete must be called once")
    public void shouldDeleteProduct() throws NotFoundException {
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.ofNullable(newTestProduct));
        productService.delete(1L);
        Mockito.verify(productRepository, Mockito.times(1)).delete(newTestProduct);
    }

    @Test
    @DisplayName("Given an invalid id, when call delete, then throw an Exception.")
    public void shouldNotDeleteInexistingProduct() throws NotFoundException {

        Exception thrown = Assertions.assertThrows(
                NotFoundException.class,
                () ->  productService.delete(2L)
        );

        Assertions.assertEquals("Produto não encontrado.", thrown.getMessage());

        Mockito.verify(productRepository, Mockito.times(0)).delete(Mockito.any());
    }
}
