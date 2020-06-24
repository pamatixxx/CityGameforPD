import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class privatDoc {

    public static void main(String[] args) {

        ArrayList<String> list = new ArrayList<>();//массив городов для записи из файла
        Map<String, ArrayList> mapCity = new HashMap<>();
        String maxLengStr = "";
        String str = ""; //строка для считывания из файла

        //    C:\Users\Судариков Сергей\Desktop
        try (FileInputStream fin = new FileInputStream("C:\\Users\\Судариков Сергей\\Desktop\\cities.txt");
             final InputStreamReader inputStreamReader = new InputStreamReader(fin, "windows-1251");) { //изменил кодировку

            //        System.out.printf("File size: %d bytes \n", fin.available())
            //считываю и записываю символ в строку , пока есть доступные в файле
            int i = -1;
            while ((i = inputStreamReader.read()) != -1) {
                str += (char) i;
            }
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }


        String[] subStr;//создал массив строк
        String delimeter = "\\n"; // Разделитель
        subStr = str.split(delimeter); // Разделения строки str с помощью метода split()

        // добавил все в массив строк  ArrayList<String> list через цикл
        for (int i = 0; i < subStr.length; i++) {
            list.add(subStr[i].trim());
        }

        //добавляем ключ-город,значение весь список в коллекцию мап
        for (int i = 0; i < list.size(); i++) {
            ArrayList<String> list1 = new ArrayList<>();
            String key = list.get(i);
            for (int j = 0; j < list.size(); j++) {
                if (key.toLowerCase().equals(list.get(j).toLowerCase())) {
                } else list1.add(list.get(j));
            }
            mapCity.put(key, list1);
        }

        boolean flag = true;
        while (flag) {
            needRepeat(mapCity);
            if (needRepeat(mapCity) == false)
                flag = false;
        }



        for (Map.Entry<String, ArrayList> entry : mapCity.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
            if (maxLengStr.length()<entry.getKey().length()){
                maxLengStr = entry.getKey();
            }
        }
        System.out.println("--------------------");
        System.out.print("Cамая длинная строка : ");
        System.out.print(maxLengStr);


    }

    public static boolean needRepeat(Map<String, ArrayList> mapCity) {
        boolean flafCheck = true;

        Map<String, ArrayList> mapReq = new HashMap<>();
        mapReq.putAll(mapCity);

        for (Map.Entry<String, ArrayList> entry : mapReq.entrySet()) {
            // System.out.println(entry.getKey() + " - " + entry.getValue());
            String key = entry.getKey();
            ArrayList<String> valueCity = entry.getValue();
            int cout = countRepetitions(valueCity, key.charAt(key.length() - 1));
            char lastLetter = key.charAt(key.length() - 1);
            //  System.out.println(addRepetitions(key, valueCity, lastLetter, cout));
            if (cout > 0) {
                removeAndAddNewKey(addRepetitions(key, valueCity, lastLetter, cout), key, valueCity, mapCity);
                flafCheck = false;
            }
        }
        return flafCheck;
    }
    // }

    //функция проверки необходимости разветвления
    public static int countRepetitions(ArrayList<String> cities, char lastLetter) {
        int count = 0;

        for (String city : cities
        ) {
            if (city.toLowerCase().charAt(0) == lastLetter) {
                count++;
            }
        }
        return count;
    }


    //вызов функции разветвления и наполнения
    public static ArrayList addRepetitions(String wordsCity, ArrayList<String> cities, char lastLetter, int count) {

        ArrayList<String> arrayList = new ArrayList<>();

        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).toLowerCase().charAt(0) == lastLetter) {
                arrayList.add(wordsCity + " " + cities.get(i));
                count--;
            }
        }

        return arrayList;
    }


    //ветвление
    public static Map removeAndAddNewKey(ArrayList<String> addRepetitionsList, String keys, ArrayList<String> cities, Map<String, ArrayList> map) {
        //   key = "Днепр";
        // cities [Одесса, Ровно, Черновцы, Ровч]
        //addRepetitionsList [Днепр Ровно, Днепр Ровч]
        //map    Днепр -  [Днепр, Одесса, Ровно, Черновцы, Ровч]
        // System.out.println(map);


        String key = keys;

        Map<String, ArrayList> arrToAdd = new HashMap<>();

        for (int i = 0; i < addRepetitionsList.size(); i++) {
            String[] citiesNeedDellMass = addRepetitionsList.get(i).split(" ");
            String string = key + " " + citiesNeedDellMass[citiesNeedDellMass.length - 1];
            ArrayList<String> list1 = new ArrayList<>();
            for (int j = 0; j < cities.size(); j++) {
                if (citiesNeedDellMass[citiesNeedDellMass.length - 1].toLowerCase().equals(cities.get(j).toLowerCase())) {
                } else list1.add(cities.get(j));
            }
            arrToAdd.put(string, list1);
        }
        map.remove(key);
        map.putAll(arrToAdd);
        return map;
    }
}

