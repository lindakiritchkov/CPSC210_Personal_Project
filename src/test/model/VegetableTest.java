package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Tests for methods in the Vegetable class

public class VegetableTest {

    private Vegetable testVegetable;

    @BeforeEach
    void runBefore() {
        testVegetable = new Vegetable("Carrot", 5);
    }

    @Test
    void testVegetableConstructor() {
        assertEquals("Carrot", testVegetable.getType());
        assertEquals(5, testVegetable.getDaysBetweenWatering());
    }

    @Test
    void testWater() {
        testVegetable.water();
        assertEquals(0, testVegetable.getDaysSinceLastWatered());
        assertEquals(testVegetable.getDaysBetweenWatering(), testVegetable.whenToWaterNext());
    }

    @Test
    void testWhenToWaterNext() {
        testVegetable.water();
        assertEquals(5, testVegetable.whenToWaterNext());
    }

    @Test
    void testEqualsVegEqual() {
        Vegetable vegToCompare = new Vegetable("Carrot", 5);
        assertTrue(vegToCompare.equals(testVegetable));
        assertTrue(testVegetable.equals(vegToCompare));
    }

    @Test
    void testEqualsSameTypeDiffDays() {
        Vegetable vegToCompare = new Vegetable("Carrot", 1);
        assertFalse(vegToCompare.equals(testVegetable));
        assertFalse(testVegetable.equals(vegToCompare));
    }

    @Test
    void testEqualsDiffTypeSameDays() {
        Vegetable vegToCompare = new Vegetable("Kale", 5);
        assertFalse(vegToCompare.equals(testVegetable));
        assertFalse(testVegetable.equals(vegToCompare));
    }

    @Test
    void testEqualsTotallyDiffVegetables() {
        Vegetable vegToCompare = new Vegetable("Kale", 3);
        assertFalse(vegToCompare.equals(testVegetable));
        assertFalse(testVegetable.equals(vegToCompare));

    }
}
