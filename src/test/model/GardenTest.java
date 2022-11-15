package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

// Tests for methods in the Garden class

class GardenTest {
    private Garden testGarden; // for non JSON-related tests
    private Vegetable testVegetableKale;
    private Vegetable testVegetableTomato;

    // For tests that do NOT deal with the JSON array
    @BeforeEach
    void runBefore(){
        testGarden = new Garden(10);
        testVegetableKale = new Vegetable("Kale", 4);
        testVegetableTomato = new Vegetable( "Tomato", 2);
    }

    @Test
    void testGardenConstructorVegetablesMade() {
        assertTrue(testGarden.isEmpty());
        assertEquals(10, testGarden.gardenList.size());
    }

    @Test
    void testAddVegetableAtPos1() {
        testGarden.addVegetable(testVegetableKale, 1);
        assertTrue(testGarden.contains(testVegetableKale));
        assertEquals(testVegetableKale, testGarden.gardenList.get(0));
    }

    @Test
    void testAddVegetableAtPos5() {
        testGarden.addVegetable(testVegetableKale, 5);
        assertTrue(testGarden.contains(testVegetableKale));
        assertEquals(testVegetableKale, testGarden.gardenList.get(4));
    }

    @Test
    void testAddVegetablePosBiggerThanGardenSize() {
        testGarden.addVegetable(testVegetableTomato, 20);
        assertFalse(testGarden.contains(testVegetableTomato));
    }

    @Test
    void testAddVegetablePosSameSizeAsGarden() {
        testGarden.addVegetable(testVegetableKale, testGarden.getSizeOfGarden());
        assertTrue(testGarden.contains(testVegetableKale));
        assertEquals(testVegetableKale, testGarden.gardenList.get(9));
    }

    @Test
    void testAddTwoVegetables() {
        testGarden.addVegetable(testVegetableKale,2);
        assertTrue(testGarden.contains(testVegetableKale));
        assertEquals(testVegetableKale, testGarden.gardenList.get(1)); // checks to see if kale is in right spot

        testGarden.addVegetable(testVegetableTomato,8);
        assertTrue(testGarden.contains(testVegetableTomato));
        assertEquals(testVegetableTomato, testGarden.gardenList.get(7)); // checks to see if tomato is in right spot
    }

    @Test
    void testRemoveVegetableWhenVegetableInGarden() {
        testGarden.addVegetable(testVegetableKale,6);
        testGarden.removeVegetable(testVegetableKale);
        assertFalse(testGarden.contains(testVegetableKale));
        assertTrue(testGarden.isEmpty());
    }

    @Test
    void testRemoveVegetableWhenVegetableInGardenMultipleVeg() {
        testGarden.addVegetable(testVegetableKale,2);
        testGarden.addVegetable(testVegetableTomato,6);
        testGarden.removeVegetable(testVegetableKale);
        assertTrue(testGarden.contains(testVegetableTomato));
        assertEquals(testVegetableTomato, testGarden.gardenList.get(5)); // checks to see if tomato is in right spot
        assertFalse(testGarden.contains(testVegetableKale));
    }

    @Test
    void testRemoveVegetableWhenVegetableInGardenMultipleTimes() {
        testGarden.addVegetable(testVegetableKale,2);
        testGarden.addVegetable(testVegetableKale,5);
        testGarden.addVegetable(testVegetableKale,9);
        testGarden.removeVegetable(testVegetableKale);
        assertNotEquals(testVegetableKale, testGarden.gardenList.get(1)); // checks to see is 1st kale was removed
        assertEquals(testVegetableKale, testGarden.gardenList.get(4));    // checks to see if 2nd kale is in right spot
        assertEquals(testVegetableKale, testGarden.gardenList.get(8));    // checks to see if 3rd kale is in right spot
    }

    @Test
    void testRemoveVegetableWhenGardenEmpty() {
        testGarden.removeVegetable(testVegetableKale);
        assertTrue(testGarden.isEmpty());
    }

    @Test
    void testRemoveVegetableWhenNotInGarden() {
        testGarden.addVegetable(testVegetableKale,7);
        testGarden.removeVegetable(testVegetableTomato);
        assertTrue(testGarden.contains(testVegetableKale));
        assertEquals(testVegetableKale, testGarden.gardenList.get(6));    // checks to see if kale is in right spot
        assertFalse(testGarden.contains(testVegetableTomato));
    }

    @Test
    void testRemoveVegetableAtPositions() {
        testGarden.addVegetable(testVegetableKale, 9);
        testGarden.addVegetable(testVegetableTomato,3);
        testGarden.addVegetable(testVegetableTomato, 2);
        testGarden.removeVegetableAtPosition(3);
        assertEquals(testVegetableKale, testGarden.gardenList.get(8));   // checks to see if kale is in right spot
        assertEquals(testVegetableTomato, testGarden.gardenList.get(1)); // checks to see if 1st tomato is in right spot
    }

    @Test
    void testGetVegetables() {
        ArrayList<Vegetable> empty = new ArrayList<>();
        assertEquals(empty, testGarden.getVegetables());

        testGarden.addVegetable(testVegetableKale, 5);
        ArrayList<Vegetable> veggiesInGarden = new ArrayList<>();
        veggiesInGarden.add(testVegetableKale);
        assertEquals(veggiesInGarden, testGarden.getVegetables());
    }

    @Test
    void testListVegetablesEmptyGarden() {
        ArrayList<String> empty = new ArrayList<>();
        assertEquals(empty, testGarden.listVegetableTypes());
    }

    @Test
    void testListVegetablesFullGarden() {
        testGarden.addVegetable(testVegetableKale,3);
        testGarden.addVegetable(testVegetableTomato,4);
        ArrayList<String> veggiesInGarden = new ArrayList<>();
        veggiesInGarden.add("Kale");
        veggiesInGarden.add("Tomato");
        assertEquals(veggiesInGarden, testGarden.listVegetableTypes());

        testGarden.removeVegetable(testVegetableKale);
        veggiesInGarden.remove("Kale");
        assertEquals(veggiesInGarden, testGarden.listVegetableTypes());
    }

    @Test
    void testIsEmptyEmptyGarden() {
        assertTrue(testGarden.isEmpty());
    }

    @Test
    void testIsEmptyFullGarden() {
        testGarden.addVegetable(testVegetableKale,3);
        testGarden.addVegetable(testVegetableTomato,9);
        assertFalse(testGarden.isEmpty());
    }

    @Test
    void testContainsVegInGarden() {
        testGarden.addVegetable(testVegetableKale,7);
        assertTrue(testGarden.contains(testVegetableKale));
    }

    @Test
    void testContainsVegNotInGarden() {
        testGarden.addVegetable(testVegetableKale,6);
        assertFalse(testGarden.contains(testVegetableTomato));
    }

    @Test
    void testGetGardenSize() {
        assertEquals(10, testGarden.getSizeOfGarden());
        testGarden.removeVegetableAtPosition(5);
        assertEquals(10, testGarden.getSizeOfGarden());
    }

    @Test
    void testGetGarden () {
        testGarden.addVegetable(testVegetableKale,2);
        assertEquals(testVegetableKale, testGarden.getGardenList().get(1));
        testGarden.addVegetable(testVegetableTomato, 8);
        assertEquals(testVegetableTomato, testGarden.getGardenList().get(7));
        assertNotSame(testVegetableKale, testGarden.getGardenList().get(3));
    }

    //JSON-related tests below

    @Test
    void testGardenConstructorFromJSONArrayEmpty() {
        ArrayList<Vegetable> emptyList = new ArrayList<>();
        JSONArray list = new JSONArray();
        Garden testGardenJson = new Garden(list);

        assertEquals(0,testGardenJson.sizeOfGarden);
        assertEquals(emptyList, testGardenJson.gardenList);
    }

    @Test
    void testGardenConstructorFromJSONArrayNotEmpty() {
        Vegetable placeHolder = new Vegetable(" ", 1);
        // makes a JSONObject to represent placeholder
        JSONObject jsonObjectPlaceHolder = new JSONObject();
        jsonObjectPlaceHolder.put("type"," ");
        jsonObjectPlaceHolder.put("daysBetweenWatering", 1);
        jsonObjectPlaceHolder.put("daysSinceLastWatered", 1);

        // makes a JSONObject to represent testVegetableKale
        JSONObject jsonObjectTestVegKale = new JSONObject();
        jsonObjectTestVegKale.put("type",testVegetableKale.getType());
        jsonObjectTestVegKale.put("daysBetweenWatering", testVegetableKale.getDaysBetweenWatering());
        jsonObjectTestVegKale.put("daysSinceLastWatered", testVegetableKale.getDaysSinceLastWatered());

        // fill a JSONArray with the vegetables above
        JSONArray jsonArray = new JSONArray(2);
        jsonArray.put(jsonObjectPlaceHolder);
        jsonArray.put(jsonObjectTestVegKale);

        ArrayList<Vegetable> expectedVeg = new ArrayList<>();
        expectedVeg.add(placeHolder);
        expectedVeg.add(testVegetableKale);

        Garden testGardenJson = new Garden(jsonArray);

        assertEquals(2,testGardenJson.sizeOfGarden);
        assertEquals(expectedVeg,testGardenJson.getGardenList());
        assertEquals(expectedVeg.get(0),testGardenJson.getGardenList().get(0));
        assertEquals(expectedVeg.get(1),testGardenJson.getGardenList().get(1));
    }

    @Test
    void testAddVegetableIntoGarden() {
        Vegetable vegetable = new Vegetable("carrot", 3);
        // makes JSON object corresponding to vegetable
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type","carrot");
        jsonObject.put("daysBetweenWatering", 3);
        jsonObject.put("daysSinceLastWatered", 3);

        Garden testGardenJson = new Garden(0);
        Garden expectedGarden = new Garden(1);
        expectedGarden.addVegetable(vegetable, 1);

        testGardenJson.addVegetableIntoGarden(testGardenJson.gardenList, jsonObject);
        assertEquals(expectedGarden.getGardenList(), testGardenJson.getGardenList());
        assertEquals(expectedGarden.getSizeOfGarden(), testGardenJson.getSizeOfGarden());
        assertEquals(expectedGarden.getGardenList().get(0), testGardenJson.getGardenList().get(0));
    }

    @Test
    void testGardenToJson() {
        Garden testGardenJson = new Garden(2);
        testGardenJson.addVegetable(testVegetableKale,2);

        // makes a JSONObject to represent placeholder
        JSONObject jsonObjectPlaceHolder = new JSONObject();
        jsonObjectPlaceHolder.put("type"," ");
        jsonObjectPlaceHolder.put("daysBetweenWatering", 1);
        jsonObjectPlaceHolder.put("daysSinceLastWatered", 1);

        // makes a JSONObject to represent testVegetableKale
        JSONObject jsonObjectTestVegKale = new JSONObject();
        jsonObjectTestVegKale.put("type",testVegetableKale.getType());
        jsonObjectTestVegKale.put("daysBetweenWatering", testVegetableKale.getDaysBetweenWatering());
        jsonObjectTestVegKale.put("daysSinceLastWatered", testVegetableKale.getDaysSinceLastWatered());

        JSONArray jsonArray = new JSONArray(2);
        jsonArray.put(jsonObjectPlaceHolder);
        jsonArray.put(jsonObjectTestVegKale);
        assertEquals(jsonArray.toString(), testGardenJson.gardenToJson().toString());
    }
}