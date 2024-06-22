package com.behrad.miscellaneous.mockito_test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShoppingTest {

    @InjectMocks
    @Spy
    Shopping.Store store;

    @InjectMocks
    Shopping.Cart cart;

    @BeforeAll
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("makeProducts")
    void addToProductsTest(Shopping.Product product, int count) {
        int previousSize = store.getProducts().size();
        store.addToProducts(product, count);
        Assertions.assertEquals(previousSize + 1, store.getProducts().size());
    }

    @ParameterizedTest
    @MethodSource("makeReserveList")
    void reserveTest(Shopping.Product product) {
        when(store.checkAvailability(product)).thenReturn(1);
        Assertions.assertTrue(store.reserve(cart, product));
    }

    @Test
    void singleReserveTest() {
        Shopping.Store mock = Mockito.spy(Shopping.Store.class);
        Shopping.Product p = new Shopping.Product("n/a", 0d);
        when(mock.checkAvailability(p)).thenReturn(10);
        mock.reserve(cart, p);
        Assertions.assertEquals(1, cart.getBasket().get(p));
    }

    Stream<Arguments> makeProducts() {
        return Stream.of(Arguments.of(new Shopping.Product("ToiletPaper", 1d), 10),
                Arguments.of(new Shopping.Product("Chocolate", 0.2), 100),
                Arguments.of(new Shopping.Product("Soccer Ball", 5d), 15));
    }

    Stream<Arguments> makeReserveList() {
        return Stream.of(Arguments.of(new Shopping.Product("ToiletPaper", 1d)),
                Arguments.of(new Shopping.Product("Chocolate", 0.2)));
    }
}