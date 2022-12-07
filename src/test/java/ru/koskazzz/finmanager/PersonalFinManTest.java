package ru.koskazzz.finmanager;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PersonalFinManTest {

    PersonalFinMan personalFinMan = new PersonalFinMan();
    // String jsonTestString = "{\"date\":\"2022.10.30\",\"sum\":\"1200\",\"title\":\"шапка\"}";
    String jsonTestString = "{\"date\":\"2022.10.30\",\"sum\":" + 1200 + ",\"title\":\"шапка\"}";

    @Test
    public void maxCategoryTest() {
        JSONObject expectedJsonObjectDetails = new JSONObject();
        expectedJsonObjectDetails.put("category", "еда");
        expectedJsonObjectDetails.put("sum", 2000.0);
        JSONObject expectedJsonObject = new JSONObject();
        expectedJsonObject.put("maxCategory", expectedJsonObjectDetails);
        personalFinMan.addPurchaseList(jsonTestString);
        personalFinMan.addPurchaseList("{\"date\":\"2022.10.30\",\"sum\":\"1700\",\"title\":\"булка\"}");
        personalFinMan.addPurchaseList("{\"date\":\"2022.10.30\",\"sum\":\"200\",\"title\":\"булка\"}");
        personalFinMan.addPurchaseList("{\"date\":\"2022.10.30\",\"sum\":\"100\",\"title\":\"булка\"}");
        String expected = expectedJsonObject.toString();
        String actual = personalFinMan.maxCategory(personalFinMan.getPurchaseList()).toString();
        Assertions.assertEquals(expected,actual);
        String actualSum = personalFinMan.fieldOfPurchase(actual, "sum");
        String expectedSum = personalFinMan.fieldOfPurchase(expected, "sum");
        Assertions.assertEquals(expectedSum,actualSum);
        Assertions.assertEquals(personalFinMan.fieldOfPurchase(expected, "sum"), personalFinMan.fieldOfPurchase(actual, "sum"));
    }

    @Test
    public void fieldOfPurchaseTest() {
        String actual = personalFinMan.fieldOfPurchase(jsonTestString, "sum");
        String actual1 = personalFinMan.fieldOfPurchase(jsonTestString, "title");
        Assertions.assertEquals("1200", actual);
        Assertions.assertEquals("шапка", actual1);
    }
}
