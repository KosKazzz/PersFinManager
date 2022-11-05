package ru.koskazzz.finmanager;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.koskazzz.parser.MultiParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class PersonalFinMan {
    HashMap<String, String> purchasesCategory;
    private List<String> purchaseList = new ArrayList<>();

    //To Do : сохранять состояние листа в файл, а при запуске сервера вынимать значения от туда
    public PersonalFinMan() {
        File categoriesFile = new File(".\\categories.tsv");
        this.purchasesCategory = ParseTsvFile(categoriesFile);
    }

    public PersonalFinMan(List<String> purchaseListFromFile) {
        File categoriesFile = new File(".\\categories.tsv");
        this.purchasesCategory = ParseTsvFile(categoriesFile);
        this.purchaseList = purchaseListFromFile;
    }

    public void addPurchaseList(String pur) {
        purchaseList.add(pur);
        SaveToFile(pur);
    }

    public JSONObject maxCategory() {

        HashMap<String, Double> sumByCat = new HashMap<>();
        String cat;
        double sum;
        for (String s : purchaseList) {
            cat = catOfPurchase(s);
            sum = Double.parseDouble(fieldOfPurchase(s, "sum"));
            if (sumByCat.containsKey(cat)) {
                sumByCat.put(cat, sumByCat.get(cat) + sum);
            } else {
                sumByCat.put(cat, sum);
            }
        }
        String maxCat = sumByCat.entrySet().stream()
                .max((value1, value2) -> (value1.getValue() > value2.getValue() ? 1 : -1))
                .get()
                .getKey();
        double maxSum = sumByCat.get(maxCat);

        return MultiParser.MaxCatStringToJson(maxCat, maxSum, "maxCategory");
    }


    HashMap<String, String> ParseTsvFile(File tsvFile) {
        StringBuilder stringFromFile = new StringBuilder();
        HashMap<String, String> purch = new HashMap<>();
        if (tsvFile.canRead()) {
            try (FileReader fileReader = new FileReader(tsvFile)) {
                int c;
                while ((c = fileReader.read()) != -1) {
                    stringFromFile.append((char) c);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                System.err.println("File categories.tsv cannot be read!");
            }
            StringBuilder stringForHashMap = new StringBuilder();
            String key = "key";
            String value = "value";
            boolean isValue = false;
            for (int i = 0; i < stringFromFile.length(); i++) {
                if ((stringFromFile.charAt(i) != '\t') && !isValue) {
                    stringForHashMap.append(stringFromFile.charAt(i));
                    continue;
                } else if (stringFromFile.charAt(i) == '\t') {
                    key = stringForHashMap.toString();
                    stringForHashMap.delete(0, key.length());
                    isValue = true;
                    continue;
                }
                if ((stringFromFile.charAt(i) != '\r') && isValue) {
                    stringForHashMap.append(stringFromFile.charAt(i));
                    continue;
                } else if (stringFromFile.charAt(i) == '\r') {
                    value = stringForHashMap.toString();
                    stringForHashMap.delete(0, value.length());
                    i++;
                    isValue = false;
                }
                purch.put(key, value);
                //for test
                //System.out.println(key + " " + value);
            }
        }
        return purch;
    }

    public String fieldOfPurchase(String jsonString, String fieldName) {
        JSONParser parser = new JSONParser();
        String result = "";
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
            result = (String) jsonObject.get(fieldName);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public String catOfPurchase(String jsonString) {
        return purchasesCategory.getOrDefault(fieldOfPurchase(jsonString, "title"), "другое");
    }

    public static PersonalFinMan LoadFromFile() {
        PersonalFinMan pfm = null;
        File file = new File(".\\data.bin");
        List<String> listFromFile = new ArrayList<>();
        StringBuilder stringBuilderFromFile = new StringBuilder();
        if (file.canRead()) {
            try (FileReader reader = new FileReader(file)) {
                int c;
                while ((c = reader.read()) != -1){
                    stringBuilderFromFile.append((char) c);
                }
                String[] strings = stringBuilderFromFile.toString().split("\n");
                listFromFile.addAll(Arrays.asList(strings));
                pfm = new PersonalFinMan(listFromFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            pfm = new PersonalFinMan();
        }
        return pfm;
    }

    public void SaveToFile(String pur) {
        File file = new File(".\\data.bin");
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(pur);
            writer.append("\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}



