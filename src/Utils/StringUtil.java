/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import SQliteUtils.SQLIteHelper;
import com.google.api.client.util.DateTime;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.util.StringConverter;
import org.apache.commons.lang3.RandomStringUtils;
import ssc.reup.api.Utils.Property;
import static ssc.reup.api.Utils.FFMPEGUtils.getDurationInString;
import static Utils.MyFileUtils.getStringPixelWidth;
import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import ssc.video.YoutubeApiVideo;

/**
 * @author inet
 */
public class StringUtil {

    public static String getRandomPass(int numberPass) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789#";
        return RandomStringUtils.random(numberPass, characters);
    }

    public static String getRandomInt(int numberPass) {
        String characters = "0123456789";
        return RandomStringUtils.random(numberPass, characters);
    }

    public static String upperKeyFirstChar(String str) {
        StringBuffer stringbf = new StringBuffer();
        Matcher m = Pattern.compile("([a-z])([a-z]*)",
                Pattern.CASE_INSENSITIVE).matcher(str);
        while (m.find()) {
            m.appendReplacement(stringbf,
                    m.group(1).toUpperCase() + m.group(2).toLowerCase());
        }
        return m.appendTail(stringbf).toString();
    }

    public static JSONObject getJsonFromFile(String filePath) {
        try {
            JSONParser pasParser = new JSONParser();
            return (JSONObject) pasParser.parse(new FileReader(filePath));
        } catch (Exception e) {
        }
        return null;
    }

    public static JSONArray getJsonArrFromFile(String filePath) {
        try {
            JSONParser pasParser = new JSONParser();
            return (JSONArray) pasParser.parse(new FileReader(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getPathOfUrl(String url) {
        try {
            URL _URL = new URL(url);
            if (url.contains("?")) {
                return url.substring(url.lastIndexOf("/") + 1, url.length());
            } else {
                return url.substring((_URL.getProtocol() + "://" + _URL.getHost() + "/").length(), url.length());
            }
        } catch (Exception e) {
            return "";
        }
    }

    public static String getMD5FromString(String input) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashInBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            result = sb.toString();
        } catch (Exception e) {
        }
        return result;
    }

    public static String getStringFromRS(String indexString, ResultSet rs) {
        String result = "";
        try {
            if (rs.getString(indexString) != null) {
                result = SQLIteHelper.deCodeSQLString(rs.getString(indexString));
            }
        } catch (Exception e) {

        }
        return result;
    }

    public static int getIntegerFromRS(String indexString, ResultSet rs, int _default) {
        int result = _default;
        try {
            result = rs.getInt(indexString);
        } catch (Exception e) {

        }
        return result;
    }

    public static long getLongFromRS(String indexString, ResultSet rs) {
        long result = -1;
        try {
            result = rs.getLong(indexString);
        } catch (Exception e) {

        }
        return result;
    }

    public static int getIntegerFromRS(String indexString, ResultSet rs) {
        int result = -1;
        try {
            result = rs.getInt(indexString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static long getLongFromDateString(String dateFormat, String strValue) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            Date date = formatter.parse(strValue);
            return date.getTime();
        } catch (Exception e) {
            return 0;
        }

    }

    public static String upperKeyFirstCharOfString(String str) {
        String result = str;
        try {
            String first = str.split(" ")[0];
            String _firstUp = upperKeyFirstChar(first);
            result = result.replaceAll(first, _firstUp);
        } catch (Exception e) {
        }
        return result;

    }

    public static List<String> getlistPathFromFolder(String folderPath) {
        List<String> listFile = new ArrayList<>();
        if (!(new File(folderPath)).exists()) {
            return listFile;
        }
        if ((new File(folderPath)).listFiles().length == 0) {
            return listFile;
        }
        File list[] = new File(folderPath).listFiles();
        for (File file : list) {
            listFile.add(file.getAbsolutePath());
        }
        return listFile;
    }

    public static List<String> getListStringBySplit(String inString, String charSplit) {
        List<String> results = new ArrayList<>();
        if (inString.length() == 0) {
            return results;
        }
        if (charSplit.length() == 0) {
            results.add(inString);
            return results;
        }
        String arr[] = inString.split(charSplit);
        for (String string : arr) {
            if (string.length() != 0) {
                results.add(string);
            }
        }
        return results;
    }

    public static List<String> getRandomVideoListString(int number, List<YoutubeApiVideo> list) {
        List<String> results = new ArrayList<>();
        List<YoutubeApiVideo> listIn = new ArrayList<>();
        listIn.addAll(list);
        while (results.size() < number) {
            YoutubeApiVideo video = listIn.get(new Random().nextInt(listIn.size()));
            results.add(video.getTitle());
            listIn.remove(video);
        }
        return results;
    }

    public static List<String> getRandomListString(int number, List<String> list) {
        List<String> results = new ArrayList<>();
        List<String> listIn = new ArrayList<>();
        listIn.addAll(list);
        while (results.size() < number) {
            String item = listIn.get(new Random().nextInt(listIn.size()));
            results.add(item);
            listIn.remove(item);
        }
        return results;
    }

    public static List<String> getRandomListStringWithTime(long time, List<String> list) {
        List<String> results = new ArrayList<>();
        List<String> listIn = new ArrayList<>();
        listIn.addAll(list);
        long total_time = 0;
        while (total_time < time) {
            if (listIn.size() == 0) {
                listIn.addAll(list);
            }
            String item = listIn.get(new Random().nextInt(listIn.size()));
            total_time = total_time + getDurationInString(item);
            results.add(item);
            listIn.remove(item);
        }
        return results;
    }

    public static List<File> getRandomListFile(int number, List<File> list) {
        List<File> results = new ArrayList<>();
        List<File> listIn = new ArrayList<>();
        listIn.addAll(list);
        while (results.size() < number) {
            File item = listIn.get(new Random().nextInt(listIn.size()));
            results.add(item);
            listIn.remove(item);
        }
        return results;
    }

    public static String generateRandomNumberString(int length) {
        Random rd = new Random();
        String candidateChars = "0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }
        return sb.toString();
    }

    public static String generateRandomCharsDevice(int length) {
        Random rd = new Random();
        String candidateChars = "0123456789abcdef";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }
        return sb.toString();
    }

    public static List<String> getRandomInList(List<String> inList) {
        List<String> results = new ArrayList<>();
        for (String item : inList) {
            if ((new Random()).nextInt(2) == 0) {
                results.add(item);
            }
        }
        return results;
    }

    public static Collection<String> getListFromString(String in_put) {
        Collection<String> results = new ArrayList<>();
        if (in_put.length() == 0) {
            return results;
        }
        String list[] = in_put.split("\n");
        for (String item : list) {
            results.add(item);
        }
        return results;
    }

    public static String getDomainFromURL(String url) {
        String domain = "";
        try {
            domain = (new URL(url)).getHost();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return domain;
    }

    public static String getRandomItemInList(List<String> inList) {
        return inList.get((new Random().nextInt(inList.size())));
    }
    public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static StringConverter converter = new StringConverter<LocalDate>() {
        @Override
        public String toString(LocalDate date) {
            if (date != null) {
                return dateFormatter.format(date);
            } else {
                return "";
            }
        }

        @Override
        public LocalDate fromString(String string) {
            if (string != null && !string.isEmpty()) {
                return LocalDate.parse(string, dateFormatter);
            } else {
                return null;
            }
        }
    };

    public static long getLongFromHour(String hour) {
        long result = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            sdf.setTimeZone(TimeZone.getDefault());
            int hour_get = 0;
            long get_time = 0;
            int _hour = 0;
            Date dt = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("h:m MM/dd/yyyy");
            dateFormat.setTimeZone(TimeZone.getDefault());

            hour_get = dt.getHours();
            _hour = Integer.parseInt(hour);
            if (hour_get > 12) {
                _hour = _hour + 12;
            }
            get_time = dateFormat.parse(_hour + ":0" + sdf.format(dt)).getTime();

            if (_hour <= dt.getHours()) {
                result = get_time + 12 * 60 * 60 * 1000;
            } else {
                result = get_time;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean checkOut() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setTimeZone(TimeZone.getDefault());
            if (dateFormat.parse("12/18/2017").getTime() > (new java.util.Date().getTime())) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public static String[] subString(String in_row, int fromPosition, String splitChar) {
        String[] array = in_row.split(splitChar);
        String[] results = new String[2];
        if (fromPosition == -1) {
            results[0] = in_row;
            results[1] = "";
            return results;
        }
        String row1 = "";
        for (int i = 0; i <= fromPosition; i++) {
            row1 = row1 + array[i];
        }
        String row2 = "";
        for (int i = fromPosition + 1; i < array.length; i++) {
            row2 = row2 + array[i];
        }
        results[0] = row1;
        results[1] = row2;
        return results;
    }

    public static int findPositionSplitPara(String in_row, int mode, int max_char_per_slide, int char_per_row) {
        if (in_row.indexOf(",") == -1) {
            return -1;
        }
        if (mode == 0 && in_row.substring(0, in_row.lastIndexOf(",")).length() < char_per_row) {
            return -1;
        }
        if (mode == 1 && in_row.substring(0, in_row.lastIndexOf(",")).split(" ").length < char_per_row) {
            return -1;
        }
        int position = 0;
        String[] pc_string = in_row.split(",");
        if (mode == 0) {
            for (int i = pc_string.length - 1; i >= 0; i--) {
                String row = "";
                for (int j = 0; j < i; j++) {
                    row = row + (row.length() == 0 ? "" : ",") + pc_string[j];
                }
                if (row.length() < max_char_per_slide) {
                    return (i - 1);
                }
            }
        } else {
            for (int i = pc_string.length - 1; i >= 0; i--) {
                String row = "";
                for (int j = 0; j < i; j++) {
                    row = row + pc_string[j];
                }
                if (row.split(" ").length < max_char_per_slide) {
                    return (i - 1);
                }
            }
        }
        return position;
    }

    public static String[] getRowOfSlide(String in_row, int word_per_row, int row_per_slide, int mode, int min_char_per_row) {
        String[] result = new String[2];
        if (mode == 0) {
            if (in_row.length() / word_per_row + (in_row.length() % word_per_row == 0 ? 0 : 1) <= row_per_slide) {
                result[0] = in_row;
                result[1] = "";
            } else {
                int position = findPositionSplitPara(in_row, mode, word_per_row * row_per_slide, word_per_row);
                result = subString(in_row, position, ",");
            }
        } else {
            if (in_row.trim().split(" ").length / word_per_row + (in_row.trim().split(" ").length % word_per_row == 0 ? 0 : 1) <= row_per_slide) {
                result[0] = in_row;
                result[1] = "";
            } else {
                int position = findPositionSplitPara(in_row, mode, word_per_row * row_per_slide, word_per_row);
                result = subString(in_row, position, ",");
            }
        }
        return result;
    }

    public static List<String> getRowInSlide(String content, int charperline, int mode) {
        //System.err.println(content + " \n*******************");
        List<String> listContent = new ArrayList<>();
        if (mode == 0) {
            String listChar = content.trim().replaceAll("!\\s+!", " ");

            int row = listChar.length() / charperline;
            int endcol = listChar.length() % charperline;
            for (int i = 0; i <= row; i++) {
                String roi_content = "";
                for (int j = 0; j < charperline; j++) {
                    if (endcol == 0 && i == row) {
                        break;
                    }
                    try {
                        roi_content = roi_content + "" + listChar.charAt(charperline * i + j);
                        if (i == row && j == endcol - 1) {
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                //System.err.println(roi_content);
                listContent.add(roi_content.replaceAll("%", ""));
            }
        } else {
            String[] listChar = content.trim().split(" ");
            //System.err.println(listChar.length);
            int row = listChar.length / charperline;
            int endcol = listChar.length % charperline;
            //System.err.println("endcol "+endcol);
            for (int i = 0; i <= row; i++) {
                String roi_content = "";
                for (int j = 0; j < charperline; j++) {
                    if (endcol == 0 && i == row) {
                        break;
                    }
                    try {
                        roi_content = roi_content + " " + listChar[charperline * i + j];
                        if (i == row && j == endcol - 1) {
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                //System.err.println(roi_content);
                listContent.add(roi_content.replaceAll("%", ""));
            }
        }
        return listContent;
    }

    public static List<String> getRowInSlideByWidthRowPerSlide(String content, int charperline, int mode) {
        //content= content.trim().replaceAll("!\\s+!", " ");
        //content= content.trim().replaceAll("\\s{2,}", " ");

        List<String> listContent = new ArrayList<>();
        if (mode == 0) {
            String listChar = content.trim();
            int row = listChar.length() / charperline;
            int endcol = listChar.length() % charperline;
            for (int i = 0; i <= row; i++) {
                String roi_content = "";
                for (int j = 0; j < charperline; j++) {
                    if (endcol == 0 && i == row) {
                        break;
                    }
                    try {
                        roi_content = roi_content + "" + listChar.charAt(charperline * i + j);
                        if (i == row && j == endcol - 1) {
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                listContent.add(roi_content.replaceAll("%", ""));
            }
        } else {
            String[] listChar = content.trim().split(" ");
            //System.err.println(listChar.length);
            int row = listChar.length / charperline;
            int endcol = listChar.length % charperline;
            //System.err.println("endcol "+endcol);
            for (int i = 0; i <= row; i++) {
                String roi_content = "";
                for (int j = 0; j < charperline; j++) {
                    if (endcol == 0 && i == row) {
                        break;
                    }
                    try {
                        roi_content = roi_content + " " + listChar[charperline * i + j];
                        if (i == row && j == endcol - 1) {
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                //System.err.println(roi_content);
                listContent.add(roi_content.replaceAll("%", ""));
            }
        }
        return listContent;
    }

    public static List<String> getRowInSlideByWidth(String content, int textSize, int mode) {
        //System.err.println(content + " \n*******************");
        List<String> listContent = new ArrayList<>();
        if (mode == 0) {
            List<String> listWord = new ArrayList<>();
            listWord.addAll(Arrays.asList(content.trim().split("")));
            String row = "";
            Boolean latinCheck = false;
            do {
                row = row + listWord.get(0);
                latinCheck = listWord.get(0).matches("^[a-zA-Z0-9.]+$");
                listWord.remove(0);
                if (getStringPixelWidth(row, textSize) > 1024 && !latinCheck) {
                    listContent.add(row);
                    row = "";
                }
            } while (listWord.size() != 0);
            if (row.length() != 0) {
                listContent.add(row);
            }

        } else {
            List<String> listWord = new ArrayList<>();
            listWord.addAll(Arrays.asList(content.trim().split(" ")));
            String row = "";
            do {
                row = row + listWord.get(0) + " ";
                listWord.remove(0);
                if (getStringPixelWidth(row, textSize) > 1024) {
                    listContent.add(row);
                    row = "";
                }
            } while (listWord.size() != 0);
            if (row.length() != 0) {
                listContent.add(row);
            }
        }
        return listContent;
    }

    public static int countLatinInString(String content) {
        int result = 0;
        try {
            int len = content.length();
            for (int i = 0; i < len; i++) {
                if (content.substring(i, i + 1).matches("^[a-zA-Z0-9.]+$")) {
                    result++;
                }
            }
        } catch (Exception e) {
        }
        return result;
    }

    public static int getLengthOfString(String content) {
        int result = 0;
        try {
            int countLatin = 0;
            int len = content.length();
            for (int i = 0; i < len; i++) {
                if (content.substring(i, i + 1).matches("^[a-zA-Z0-9.]+$")) {
                    countLatin++;
                }
            }
            return (content.length() - countLatin) * 2 + countLatin;
        } catch (Exception e) {
        }
        return result;
    }

    public static int findPositionSplitParaLatinh(String content, int textSize) {
        List<String> listPara = new ArrayList<>();
        String[] arrPara = content.split("\\!|,|\\?|、|！|・|？|，|\\:|:");
        for (String string : arrPara) {
            try {
                listPara.add(content.substring(content.indexOf(string), content.indexOf(string) + string.length() + 1));
            } catch (Exception e) {
                try {
                    listPara.add(content.substring(content.indexOf(string), content.indexOf(string) + string.length()));
                } catch (Exception ex) {
                }
            }
        }
        String slide = "";
        do {
            slide = slide + listPara.get(0);
            listPara.remove(0);
            if (listPara.size() == 1) {
                break;
            }
        } while (getStringPixelWidth(slide, textSize) < (1024 * 2) && listPara.size() != 0);
        if (listPara.size() == 0) {
            return 0;
        }
        return slide.length();
    }

    public static List<String> getArrPara2(String content, int min_char_per_row, int mode, int _textSize, int row_per_slide) {
        // news.getNews_split_para_char().replaceAll("[\\<\\(\\[\\{\\\\\\^\\-\\=\\$\\!\\|\\]\\}\\)\\?\\*\\+\\.\\>]", "\\\\$0")
        int textSize = _textSize;
        List<String> arr = new ArrayList<>();
        List<String> listPara = new ArrayList<>();
        String[] arrPara = content.split("\\.|。|！|、|？");
        for (String string : arrPara) {
            try {
                listPara.add(content.substring(content.indexOf(string), content.indexOf(string) + string.length() + 1));
            } catch (Exception e) {
                try {
                    listPara.add(content.substring(content.indexOf(string), content.indexOf(string) + string.length()));
                } catch (Exception ex) {
                }
            }
        }
        String slide = "";
        int countMaxWidth = 1024 * (row_per_slide + 1);
        while (listPara.size() != 0) {
            slide = "";
            do {
                slide = slide + listPara.get(0).trim().replaceAll("\n", "");
                listPara.remove(0);
            } while (listPara.size() != 0 && ((getStringPixelWidth(slide, textSize) + getStringPixelWidth(listPara.get(0).trim().replaceAll("\n", ""), textSize)) < countMaxWidth));
            if (listPara.size() != 0 && getStringPixelWidth(slide, textSize) <= 1024) {
                listPara.set(0, slide + " " + listPara.get(0));
                continue;
            }
            if (getStringPixelWidth(slide, textSize) > countMaxWidth) {
                int i = findPositionSplitParaLatinh(slide, _textSize);
                String row1 = slide.substring(0, i);
                String row2 = slide.substring(i, slide.length());
                arr.add(row1);
                arr.add(row2);
            } else {
                arr.add(slide);
            }
        }
        return arr;
    }

    public static long getCurrentDateTimeToLong() {
        return (new java.util.Date().getTime());
    }

    public static String getCurrentDateTime(String format_string) {
        SimpleDateFormat sdf = new SimpleDateFormat(format_string);
        sdf.setTimeZone(TimeZone.getDefault());
        Date dt = new Date();
        return sdf.format(dt);
    }

    public static String getCurrentDateTimeFromLong(String format_string, long time) {
        SimpleDateFormat sdf = new SimpleDateFormat(format_string);
        sdf.setTimeZone(TimeZone.getDefault());
        Date dt = new Date(time);
        return sdf.format(dt);
    }

    public static long getCurrentLongTimeFromDate(String format_string, String datetime) {
        try {

            SimpleDateFormat sdf = new SimpleDateFormat(format_string);
            DateTime dt = new DateTime(sdf.parse(datetime));
            System.out.println(datetime + " dt.getValue() " + dt.getValue());
            return dt.getValue();
        } catch (ParseException ex) {

        }
        return 0;
    }

//
//    public static String getContentOfNews(WebDriver driver, String xpath) {
//        String content = "";
//        int count = SeleniumJSUtils.getLengthJS(xpath + "//p", driver);
//        for (int i = 0; i < count; i++) {
//            try {
//                if (!Jsoup.parse(SeleniumJSUtils.getContentText(xpath + "//p", driver, i)).text().equals(" ") && Jsoup.parse(SeleniumJSUtils.getContentText(xpath + "//p", driver, i)).text().length() != 0 && !Jsoup.parse(SeleniumJSUtils.getContentText(xpath + "//p", driver, i)).text().equals("\u00A0")) {
//                    //System.out.println(SeleniumJSUtils.getContentText(xpath + "//p", driver, i).replaceAll("\u00A0","")+"count :"+SeleniumJSUtils.getContentText(xpath + "//p", driver, i).replaceAll("\u00A0","").length()+"html :"+SeleniumJSUtils.getText(xpath + "//p", driver, i));
//                    content = content + Jsoup.parse(SeleniumJSUtils.getContentText(xpath + "//p", driver, i)).text() + "\n";
//                }
//            } catch (Exception e) {
//                //e.printStackTrace();
//            }
//        }
//        return content;
//    }
    public static String getSameString(String str1, String str2, String str3) {
        try {
            String _str1 = (new URL(str1)).getPath();
            String _str2 = (new URL(str2)).getPath();
            String _str3 = (new URL(str3)).getPath();
            String s = "";
            for (int i = 0; i < _str1.length(); i++) {
                if (_str2.contains(_str1.substring(0, i))) {
                    s = _str2.substring(0, i);
                }
            }
            if (str3.contains(s)) {
                return s;
            }
            return "";
        } catch (Exception ex) {
            return "";
        }
    }

    public static String getDurationStringFromLong(long duration) {
        long hour = duration / 3600;
        long minute = duration % 3600 / 60;
        long second = duration % 3600 % 60;

//        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(duration),
//                TimeUnit.MILLISECONDS.toMinutes(duration) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration)),
//                TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
//        System.out.println("hms "+hms);
        String hms = hour + ":" + minute + ":" + second;

        return hms;
    }

    public static List<String> getListTagsFromString(String _tags) {
        List<String> tags = new ArrayList<>();
        String[] __tags = _tags.trim().split(",");
        for (String __tag : __tags) {
            tags.add(__tag);
        }
        return tags;
    }

    public static String getStringTagsFromList(List<String> listTags) {
        String tags = "";
        for (String tag : listTags) {
            if (listTags.indexOf(tag) != listTags.size() - 1) {
                tags = tags + tag + ",";
            } else {
                tags = tags + tag;
            }
        }
        return tags;
    }

    public static String getIDSQLFromList(List<String> listTags) {
        String tags = "";
        for (String tag : listTags) {
            if (listTags.indexOf(tag) != listTags.size() - 1) {
                tags = tags + "'" + tag + "',";
            } else {
                tags = tags + "'" + tag + "'";
            }
        }
        return tags;
    }

    public static String getArrayNotInList(List<String> listTags) {
        String tags = "";
        for (String tag : listTags) {
            if (listTags.indexOf(tag) != listTags.size() - 1) {
                tags = tags + "'" + tag + "',";
            } else {
                tags = tags + "'" + tag + "'";
            }
        }
        return tags;
    }

    public static int getPathLengthFromUrl(String url) {
        int path_length = 0;
        try {
            path_length = new URL(url).getPath().split("/").length;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path_length;
    }

    public static String getPathFromUrl(String url) {
        String path = "";
        try {
            path = new URL(url).getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    public static Long convertStringToLong(String _date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            DateTime dt = new DateTime(sdf.parse(_date));
            return dt.getValue();
        } catch (ParseException ex) {

        }
        return null;
    }

    public static DateTime convertLongToDateTime(long _date) {
        DateTime dt = new DateTime(_date);
        return dt;
    }

    public static String convertLongDateToString(long _date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date dt = new Date(_date);
        return sdf.format(dt);
    }

    public static String getPath() {
        String path = "";
        try {
            String _string = Property.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            path = _string.split("/")[1] + "/" + _string.split("/")[2] + "/" + _string.split("/")[3] + "/" + _string.split("/")[4] + "/" + _string.split("/")[5];
        } catch (URISyntaxException ex) {

        }
        return path;
    }

}
