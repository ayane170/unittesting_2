package week7.debug;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShopTest {

    @Test
    void getTotalPriceOfShoppingCart_happyFlow() {
        Shop shop = new Shop();
        Product product1 = shop.getProduct("Toetsenbor Mechanisch");
        Product product2 = shop.getProduct("Monitor 27-inch");
        Product product3 = shop.getProduct("Printer Laser");

        shop.addToShoppingCart(product1, 2);
        shop.addToShoppingCart(product2, 1);
        shop.addToShoppingCart(product3, 3);

        double totalPrice = shop.getTotalPriceOfShoppingCart();

        assertEquals(919.97, totalPrice);
    }

    @Test
    void getTotalPriceOfShoppingCart_negativeNumber_throwsException() {
        Shop shop = new Shop();
        Product product1 = shop.getProduct("Toetsenbord Mechanisch");
        Product product2 = shop.getProduct("Monitor 27-inch");

        shop.addToShoppingCart(product1, 2);
        assertThrows(IllegalArgumentException.class, () -> shop.addToShoppingCart(product2, -1));
    }

    @Test
    void getTotalPriceOfShoppingCart_productNotFound_throwsException() {
        Shop shop = new Shop();
        assertThrows(IllegalArgumentException.class, () -> shop.getProduct("Toetsenbor Mechanisch"));
    }
}