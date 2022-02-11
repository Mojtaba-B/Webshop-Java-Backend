package de.mo.coding.webshop.service;

import de.mo.coding.webshop.exceptions.IdNotFoundException;
import de.mo.coding.webshop.model.OrderPositionResponse;
import de.mo.coding.webshop.model.ProductCreateRequest;
import de.mo.coding.webshop.model.ProductResponse;
import de.mo.coding.webshop.repository.CustomerRepository;
import de.mo.coding.webshop.repository.OrderPositionRepository;
import de.mo.coding.webshop.repository.OrderRepository;
import de.mo.coding.webshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShoppingCartServiceTest {
    private ProductRepository productRepository;
    private ShoppingCartService service;

    @BeforeEach
    public void setUpTests() {
        productRepository = new ProductRepository();
        service = new ShoppingCartService(
                new OrderRepository(new CustomerRepository()),
                new OrderPositionRepository(),
                productRepository
        );

    }

    @Test
    public void testThat_calculateSumForEmptyCart_returnsDeliveryCost() {

        //given

        //when
        Long result = service.calculateSumForCart(
                new ArrayList<OrderPositionResponse>(), 500);

        //then
        assertThat(result).isEqualTo(500);
    }

    @Test
    public void testThat_calculateSumForOneProduct_sumsPriceOfProduct() {

        //given
        ProductResponse savedProduct = savedProduct(1000);
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
        ProductResponse savedProduct1 = savedProduct(1000);
        ProductResponse savedProduct2 = savedProduct(2000);

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
        ProductResponse notSavedProduct = new ProductResponse("", "", "", 1000, new ArrayList<>());
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
        ProductResponse savedProduct1 = savedProduct(1000);
        ProductResponse savedProduct2 = savedProduct(2000);

        List<OrderPositionResponse> orderPositions = new ArrayList<>();
        addOrderPosition(orderPositions, savedProduct1, 1);
        addOrderPosition(orderPositions, savedProduct2, -4);

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            // when
            service.calculateSumForCart(orderPositions, 500);
        });
    }

    private void addOrderPosition(List<OrderPositionResponse> orderPositions, ProductResponse savedProduct, int quantity) {
        orderPositions.add(new OrderPositionResponse("1", "order-Id", savedProduct.getId(), quantity));
    }

    private ProductResponse savedProduct(int price) {
        return productRepository.save(new ProductCreateRequest(
                "", "", price, new ArrayList<>()
        ));
    }

}
