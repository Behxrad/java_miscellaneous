package com.behrad.miscellaneous.mockito_test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Shopping {

    public static class Store {

        private Map<Product, Integer> products;

        public Store() {
            this.products = new HashMap<>();
        }

        public boolean reserve(Cart cart, Product... list) {
            for (Product p : list) {
                if (checkAvailability(p) != 0) {
                    cart.addToCart(p);
                    deductFromStorage(p);
                    return true;
                }
            }
            return false;
        }

        public int checkAvailability(Product product) {
            if (products.containsKey(product))
                return products.get(product);
            return 0;
        }

        public void deductFromStorage(Product product) {
            Integer count = products.get(product);
            if (count != null)
                products.put(product, --count);
        }

        public void addToProducts(Product product, int count) {
            if (products == null) {
                products = new HashMap<>();
            }
            products.put(product, count);
        }

        public Map<Product, Integer> getProducts() {
            return products;
        }
    }

    public static class Cart {

        private Map<Product, Integer> basket;

        public Cart() {
            this.basket = new HashMap<>();
        }

        public void addToCart(Product product) {
            basket.compute(product, (k, v) -> {
                int c;
                if (v == null)
                    c = 1;
                else
                    c = v++;
                basket.put(product, c);
                return c;
            });
        }

        public Map<Product, Integer> getBasket() {
            return basket;
        }
    }

    public static class Product {

        private String name;
        private Double price;

        public Product(String name, Double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Product)) return false;

            Product product = (Product) o;

            if (!Objects.equals(name, product.name)) return false;
            return Objects.equals(price, product.price);
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (price != null ? price.hashCode() : 0);
            return result;
        }
    }
}
