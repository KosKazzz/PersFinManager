package ru.koskazzz.parser;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MultiParser {

    public static JSONObject ClientStringToJson(String msg) {
        JSONObject clientJson = new JSONObject();
        Calendar calendar = new GregorianCalendar();
        calendar.roll(Calendar.MONTH, 1);
        String date = calendar.get(Calendar.YEAR) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.DAY_OF_MONTH);
        String[] splitMsg = msg.split(" ");
        clientJson.put("title", splitMsg[0]);
        clientJson.put("date", date);
        clientJson.put("sum", splitMsg[1]);
        return clientJson;
    }

    public static JSONObject PurchaseStringToJson(String str) {
        JSONObject purchaseJson = new JSONObject();
        JSONParser parser = new JSONParser();
        try {
            purchaseJson = (JSONObject) parser.parse(str);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return purchaseJson;
    }

    public static JSONObject MaxCatStringToJson(String maxCat, double maxSum, String period) {
        JSONObject maxCatByPeriodDetails = new JSONObject();
        maxCatByPeriodDetails.put("category", maxCat);
        maxCatByPeriodDetails.put("sum", maxSum);
        JSONObject maxCatByPeriod = new JSONObject();
        maxCatByPeriod.put(period, maxCatByPeriodDetails);
        return maxCatByPeriod;
    }
}

