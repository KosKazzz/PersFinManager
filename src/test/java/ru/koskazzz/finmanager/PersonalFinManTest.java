package ru.koskazzz.finmanager;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PersonalFinManTest {

    PersonalFinMan personalFinMan = new PersonalFinMan();
    String jsonString = "{\"date\":\"2022.10.30\",\"sum\":\"1200\",\"title\":\"шапка\"}";

    @Test
    public void maxCategoryTest(){
        JSONObject expectedJsonObjectDetails = new JSONObject();
        expectedJsonObjectDetails.put("category","еда");
        expectedJsonObjectDetails.put("sum",2000.0);
        JSONObject expectedJsonObject = new JSONObject();
        expectedJsonObject.put("maxCategory",expectedJsonObjectDetails);
        personalFinMan.addPurchaseList(jsonString);
        personalFinMan.addPurchaseList("{\"date\":\"2022.10.30\",\"sum\":\"1700\",\"title\":\"булка\"}");
        personalFinMan.addPurchaseList("{\"date\":\"2022.10.30\",\"sum\":\"200\",\"title\":\"булка\"}");
        personalFinMan.addPurchaseList("{\"date\":\"2022.10.30\",\"sum\":\"100\",\"title\":\"булка\"}");
        Assertions.assertEquals(expectedJsonObject,personalFinMan.maxCategory());

    }
    @Test
    public void fieldOfPurchaseTest(){
        String actual = personalFinMan.fieldOfPurchase(jsonString,"sum");
        String actual1 = personalFinMan.fieldOfPurchase(jsonString,"title");
        Assertions.assertEquals("1200",actual);
        Assertions.assertEquals("шапка",actual1);
    }
}
