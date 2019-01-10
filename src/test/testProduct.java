package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class testProduct {
    @BeforeEach
    public void setUp(){
        Product testAvailableProduct = new Product(1, "testAvailable", 14, 3);
        Product testUnavailableProduct = new Product(2, "testUnavailable", 12.01, 0);
    }

    @Test
    public void testIsAvailable() {
        assertFalse(testUnavailableProduct.isAvailable());
        assertTrue(testAvailableProduct.isAvailable());
    }

    @Test
    public void testPurchase() {
        assertTrue(testAvailableProduct.purchase);
        assertFalse(testUnavailableProduct.purchase);


    }
}
