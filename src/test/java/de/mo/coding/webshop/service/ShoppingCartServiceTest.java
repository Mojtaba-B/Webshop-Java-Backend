package de.mo.coding.webshop.service;

import de.mo.coding.webshop.entity.ProductEntity;
import de.mo.coding.webshop.exceptions.IdNotFoundException;
import de.mo.coding.webshop.model.OrderPositionResponse;
import de.mo.coding.webshop.repository.OrderRepository;
import de.mo.coding.webshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ShoppingCartServiceTest {
    private ProductRepository productRepository;
    private ShoppingCartService service;

    @BeforeEach
    public void setUpTests() {
        productRepository = mock(ProductRepository.class);
        service = new ShoppingCartService(
                mock(OrderRepository.class),
                productRepository
        );

    }

    @Test
    public void testThat_calculateSumForEmptyCart_returnsDeliveryCost() {

        //when
        Long result = service.calculateSumForCart(
                new ArrayList<>(), 500);

        //then
        assertThat(result).isEqualTo(500);
    }

    @Test
    public void testThat_calculateSumForOneProduct_sumsPriceOfProduct() {

        //given
        ProductEntity savedProduct = new ProductEntity(
                UUID.randomUUID().toString(),
                "",
                "",
                1000,
                new ArrayList<>()
        );
        given(productRepository.findById(savedProduct.getId())).willReturn(Optional.of(savedProduct));

        List<OrderPositionResponse> orderPositions = new ArrayList<>();
        addOrderPosition(orderPositions, savedProduct, 1);

        // when
        Long result = service.calculateSumForCart(orderPositions, 500);

        //then
        assertThat(result).isEqualTo(1500);
    }

    @Test
    public void testThat_calculateSumForTwoProducts_sumsPricesOfProducts() {

        //given
        ProductEntity savedProduct1 = new ProductEntity(
                UUID.randomUUID().toString(),
                "",
                "",
                1000,
                new ArrayList<>()
        );
        given(productRepository.findById(savedProduct1.getId())).willReturn(Optional.of(savedProduct1));

        ProductEntity savedProduct2 = new ProductEntity(
                UUID.randomUUID().toString(),
                "",
                "",
                2000,
                new ArrayList<>()
        );
        given(productRepository.findById(savedProduct2.getId())).willReturn(Optional.of(savedProduct2));


        List<OrderPositionResponse> orderPositions = new ArrayList<>();
        addOrderPosition(orderPositions, savedProduct1, 1);
        addOrderPosition(orderPositions, savedProduct2, 4);

        // when
        Long result = service.calculateSumForCart(orderPositions, 500);

        //then
        assertThat(result).isEqualTo(9500);
    }


    @Test
    public void testThat_calculateSumForNotExistingProduct_throwsException() {

        //given
        ProductEntity notSavedProduct = new ProductEntity(
                "",
                "",
                "",
                1000,
                new ArrayList<>());
        List<OrderPositionResponse> orderPositions = new ArrayList<>();
        addOrderPosition(orderPositions, notSavedProduct, 1);

        //then
        assertThrows(IdNotFoundException.class, () -> {

            // when
            service.calculateSumForCart(orderPositions, 500);
        });
    }


    @Test
    public void testThat_calculateSumForNegativeQuantity_throwsException() {

        //given
        ProductEntity savedProduct1 = new ProductEntity(
                UUID.randomUUID().toString(),
                "",
                "",
                1000,
                new ArrayList<>()
        );
        given(productRepository.findById(savedProduct1.getId())).willReturn(Optional.of(savedProduct1));

        ProductEntity savedProduct2 = new ProductEntity(
                UUID.randomUUID().toString(),
                "",
                "",
                2000,
                new ArrayList<>()
        );
        given(productRepository.findById(savedProduct2.getId())).willReturn(Optional.of(savedProduct2));


        List<OrderPositionResponse> orderPositions = new ArrayList<>();
        addOrderPosition(orderPositions, savedProduct1, 1);
        addOrderPosition(orderPositions, savedProduct2, -4);

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            // when
            service.calculateSumForCart(orderPositions, 500);
        });
    }

    private void addOrderPosition(List<OrderPositionResponse> orderPositions, ProductEntity savedProduct, int quantity) {
        orderPositions.add(new OrderPositionResponse("1", savedProduct.getId(), quantity));
    }

}
