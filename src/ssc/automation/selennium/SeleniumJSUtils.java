/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.automation.selennium;

import Setting.ToolSetting;
import static Utils.Constants.ERROR_FOLDER;
import static Utils.Constants.SELENIUM.CHECK_CSS_CLASS;
import static Utils.Constants.SELENIUM.CHECK_HIDE_CSS_CLASS;
import Utils.StringUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import log.RunLog;
import static log.RunLog.insertRunLog;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ssc.reup.api.Utils.SeleniumUtils;

/**
 * @author inet
 */
public class SeleniumJSUtils {

    public static String getTextContentOfChild(String xpath, WebDriver driver, String childTagName, int positionParent, int positionChild) {
        String result = "";
        try {
            String query = "return document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(" + positionParent + ").getElementsByTagName(\"" + childTagName + "\")[" + positionChild + "].textContent";
            result = String.valueOf(((JavascriptExecutor) driver).executeScript(query, new Object[0]));
        } catch (Exception e) {
            //e.printStackTrace();
            return "";
        }
        return result;
    }
    
    
    public static Boolean scrollToBottom(WebDriver drivert) {
        try {
            ((JavascriptExecutor) drivert).executeScript("window.scrollTo(0,document.body.scrollHeight);", new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /* example
        document.evaluate("", document, null, 7, null).snapshotLength
     */
    public static Boolean scrollToElement(WebDriver driver, WebElement webElement) {
        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("window.scrollTo(0,arguments[0].getBoundingClientRect().bottom);", webElement);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean loadPage(WebDriver driver, String url, int timeOut) {
        try {
//            driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
//            driver.manage().timeouts().setScriptTimeout(timeOut, TimeUnit.SECONDS);
//            driver.manage().timeouts().pageLoadTimeout(timeOut, TimeUnit.SECONDS);
            driver.get(url);
            waitForPageLoad(driver, timeOut);
            if ((driver.getTitle().contains("Lỗi")) || (driver.getTitle().contains("Error")) || (driver.getTitle().contains("Problem loading page"))) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getXpathFromXpath(WebDriver driver, List<String> arrListXpath) {
        try {
            for (String xpath : arrListXpath) {
                if (getLengthJS(xpath, driver) != 0) {
                    return xpath;
                }
            }
        } catch (Exception e) {
        }
        return "";
    }

    public static void clickToPoint(WebDriver driver, int x, int y) {
        try {
            Actions builder = new Actions(driver);
            builder
                    .moveByOffset(x, y)
                    .click()
                    .build().perform();
        } catch (Exception e) {
        }
    }

    public static void hideBrowser(WebDriver driver) {
        try {
            Actions builder = new Actions(driver);
            builder
                    .moveByOffset(2, 2)
                    .click()
                    .build().perform();
        } catch (Exception e) {
        }
    }

    public static void showBrowserError(WebDriver driver) {
        try {
            driver.manage().window().setSize(new Dimension(1280, 720));
            driver.manage().window().setPosition(new Point(500, 500));
            Sleep(10000);
        } catch (Exception e) {
        }
    }

    public static void loadPage(WebDriver driver, String url) {
        try {
//            driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
//            driver.manage().timeouts().setScriptTimeout(timeOut, TimeUnit.SECONDS);
//            driver.manage().timeouts().pageLoadTimeout(timeOut, TimeUnit.SECONDS);
            driver.get(url);

        } catch (Exception e) {

        }
    }

    public static boolean loadPage(WebDriver driver, int timeOut) {
        try {
            waitForPageLoad(driver, timeOut);
            if ((driver.getTitle().contains("Lỗi")) || (driver.getTitle().contains("Error")) || (driver.getTitle().contains("Problem loading page"))) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean moveAndHoverToElement(WebDriver driver, By by, int wait_time) {
        try {
            WebElement element = driver.findElements(by).get(0);
            getJS("window.scrollTo(0," + (element.getLocation().y) + ");", driver);
            Sleep(wait_time * 1000);
            new Actions(driver).moveToElement(element).perform();
            Sleep(wait_time * 1000);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean moveAndHoverToAddWatchLater(WebDriver driver, By by, int wait_time) {
        try {
            WebElement element = driver.findElements(by).get(0);
            getJS("window.scrollTo(0," + (element.getLocation().y) + "-110);", driver);
            Sleep(wait_time * 1000);
            new Actions(driver).moveToElement(element).perform();
            Sleep(wait_time * 1000);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean moveAndHoverToAndClickElement(WebDriver driver, By by) {
        try {
            WebElement element = driver.findElements(by).get(0);
            new Actions(driver).moveToElement(element).click().perform();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean moveAndHoverToElement(WebDriver driver, WebElement element, int wait_time) {
        try {
            getJS("window.scrollTo(0," + (element.getLocation().y) + ");", driver);
            new Actions(driver).moveToElement(element).perform();
            Sleep(wait_time * 1000);
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public static boolean clickElement(WebDriver driver, By by, String error_title, int wait_time, ObservableList<RunLog> dataLog) {
        try {
            WebElement element = driver.findElements(by).get(0);
            getJS("window.scrollTo(0," + (element.getLocation().y - 100) + ");", driver);
            new Actions(driver).moveToElement(element).perform();
            element.click();
            Sleep(wait_time * 1000);
            return true;
        } catch (Exception e) {
            //System.err.println("*************************** Size element: " + driver.findElements(by).size());
            e.printStackTrace();
            insertRunLog("Cant click " + error_title, e, driver, dataLog);
            return false;
        }
    }

    public static boolean clickElementByAction(WebDriver driver, WebElement element) {
        try {
            getJS("window.scrollTo(0," + (element.getLocation().y - 10) + ");", driver);
            new Actions(driver).moveToElement(element).perform();
            element.click();
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public static boolean clickElementByAction(WebDriver driver, By by) {
        try {
            WebElement element = driver.findElement(by);
            getJS("window.scrollTo(0," + (element.getLocation().y - 10) + ");", driver);
            new Actions(driver).moveToElement(element).perform();
            element.click();
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public static boolean clickElementByAction(WebDriver driver, By by, int position) {
        try {
            WebElement element = driver.findElements(by).get(position);
            getJS("window.scrollTo(0," + (element.getLocation().y - 10) + ");", driver);
            new Actions(driver).moveToElement(element).perform();
            element.click();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean sendKeyandEnterElement(WebDriver driver, By by, String error_title, int wait_time, String key, ObservableList<RunLog> dataLog) {
        try {
            WebElement element = driver.findElement(by);
            new Actions(driver).moveToElement(element).perform();
            element.click();
            element.sendKeys(key);
            element.sendKeys(Keys.ENTER);
            Sleep(wait_time);
            return true;
        } catch (Exception e) {
            //System.err.println("*************************** Size element: " + driver.findElements(by).size());
            e.printStackTrace();
            insertRunLog("Cant send " + error_title, e, driver, dataLog);
            return false;
        }
    }

    public static boolean sendKeyElement(WebDriver driver, By by, String error_title, int wait_time, String key, ObservableList<RunLog> dataLog) {
        try {
            WebElement element = driver.findElement(by);
            new Actions(driver).moveToElement(element).perform();
            element.click();
            element.sendKeys(key);
            Sleep(wait_time);
            return true;
        } catch (Exception e) {
            //System.err.println("*************************** Size element: " + driver.findElements(by).size());
            e.printStackTrace();
            insertRunLog(error_title, e, driver, dataLog);
            return false;
        }
    }

    public static boolean clickRandomGPlusPost(WebDriver driver, By by, String error_title, int wait_time, int randomPost, String post_url, ObservableList<RunLog> dataLog) {
        try {
            WebElement btOpenPost = driver.findElements(by).get(randomPost);
            ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('href','" + post_url.replaceAll("https://plus.google.com", ".") + "')", btOpenPost);
            ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('jsname')", btOpenPost);
            return clickElement(driver, btOpenPost, error_title, wait_time, dataLog);
        } catch (Exception e) {
            e.printStackTrace();
            insertRunLog(error_title, e, driver, dataLog);
            return false;
        }
    }

    public static boolean performElement(WebDriver driver, By by, String error_title, int wait_time, int position, ObservableList<RunLog> dataLog) {
        try {
            WebElement element = driver.findElements(by).get(position);
            getJS("window.scrollTo(0," + (element.getLocation().y - 100) + ");", driver);
            new Actions(driver).moveToElement(element).perform();
            Sleep(wait_time * 1000);
            return true;
        } catch (Exception e) {
            //System.err.println("*************************** Size element: " + driver.findElements(by).size());
            e.printStackTrace();
            insertRunLog("Cant perform " + error_title, e, driver, dataLog);
            return false;
        }
    }

    public static boolean clickElement(WebDriver driver, By by, String error_title, int wait_time, int position, ObservableList<RunLog> dataLog) {
        try {
            WebElement element = driver.findElements(by).get(position);
            getJS("window.scrollTo(0," + (element.getLocation().y - 100) + ");", driver);
            new Actions(driver).moveToElement(element).perform();
            element.click();
            Sleep(wait_time * 1000);
            return true;
        } catch (Exception e) {
            //System.err.println("*************************** Size element: " + driver.findElements(by).size());
            e.printStackTrace();
            insertRunLog(error_title, e, driver, dataLog);
            return false;
        }
    }

    public static boolean scrollTo(WebDriver driver, int y, ObservableList<RunLog> dataLog) {
        try {
            getJS("window.scrollTo(0," + y + ");", driver);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean scrollTo(WebDriver driver, int fromy, int toy, ObservableList<RunLog> dataLog) {
        try {
            getJS("window.scrollTo(" + fromy + "," + toy + ");", driver);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean clickElement(WebDriver driver, WebElement element, String error_title, int wait_time, ObservableList<RunLog> dataLog) {
        try {
            getJS("window.scrollTo(0," + (element.getLocation().y - 100) + ");", driver);
            new Actions(driver).moveToElement(element).perform();
            element.click();
            Sleep(wait_time * 1000);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            insertRunLog(error_title, e, driver, dataLog);
            return false;
        }
    }

    public static boolean waitElementExist(WebDriver driver, By by, String error_title, int wait_time, int re_time) {
        for (int i = 0; i < re_time; i++) {
            try {
                (new WebDriverWait(driver, wait_time)).until(ExpectedConditions.presenceOfElementLocated(by));
                break;
            } catch (Exception e) {
                if (i == re_time) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean waitElementExist(WebDriver driver, By by, String error_title, int wait_time, int re_time, ObservableList<RunLog> dataLog) {
        for (int i = 0; i < re_time; i++) {
            try {
                (new WebDriverWait(driver, wait_time)).until(ExpectedConditions.presenceOfElementLocated(by));
                break;
            } catch (Exception e) {
                if (i == re_time) {
                    e.printStackTrace();
                    insertRunLog("Cant load " + error_title, e, driver, dataLog);
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean waitOpenNewWindow(WebDriver driver, String error_title, int wait_time, int re_time, ObservableList<RunLog> dataLog) {
        for (int i = 0; i < 3; i++) {
            try {
                (new WebDriverWait(driver, wait_time)).until(ExpectedConditions.numberOfWindowsToBe(2));
                break;
            } catch (Exception e) {
                if (i == re_time) {
                    e.printStackTrace();
                    insertRunLog("Cant open " + error_title, e, driver, dataLog);
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean waitElementOut(WebDriver driver, By by, String error_title, int wait_time, int re_time, ObservableList<RunLog> dataLog) {
        for (int i = 0; i < re_time; i++) {
            try {
                (new WebDriverWait(driver, wait_time)).until(ExpectedConditions.numberOfElementsToBeLessThan(by, 1));
                break;
            } catch (Exception e) {
                if (i == re_time) {
                    e.printStackTrace();
                    insertRunLog("Cant close " + error_title, e, driver, dataLog);
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean waitElementsMore(WebDriver driver, By by, String error_title, int wait_time, int re_time, int numbermore) {
        for (int i = 0; i < re_time; i++) {
            try {
                (new WebDriverWait(driver, wait_time)).until(ExpectedConditions.numberOfElementsToBeMoreThan(by, numbermore));
                Sleep(1000 * wait_time);
                break;
            } catch (Exception e) {
                if (i == re_time) {
                    e.printStackTrace();
                    //insertRunLog(error_title, e, driver);
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean waitElementAttrTo(WebDriver driver, WebElement by, String error_title, int wait_time, int re_time, String attr, String attr_v) {
        for (int i = 0; i < re_time; i++) {
            //System.err.println("waitElementAttrTo " + new java.util.Date().getTime() / 1000 / 60);
            try {
                (new WebDriverWait(driver, wait_time)).until(ExpectedConditions.attributeContains(by, attr, attr_v));
                break;
            } catch (Exception e) {
                if (i == re_time) {
                    e.printStackTrace();
                    //insertRunLog(error_title, e, driver);
                    return false;
                }
            }
        }
        return true;
    }

    public static void waitForPageLoad(WebDriver d, int timeout) {
        String s = "";
        try {
            int countTime = 0;
            while (!s.equals("complete") && countTime < timeout) {
                s = (String) ((JavascriptExecutor) d).executeScript("return document.readyState");
                Sleep(1000);
                countTime++;
            }
        } catch (Exception e) {

        }
        if (ToolSetting.getInstance().getPre().getBoolean("chLoginWithAppleTv", false)) {
            //fakingNavigator(d, "AppleTV6,2/11.1");
        } else {
            //fakingNavigator(d, usergent);
        }
    }

    public static boolean fakingNavigator(WebDriver driver, String useragent) {
        try {
            getJS("Object.defineProperty(Navigator.prototype, \"webdriver\", {\n"
                    + "        get() {\n"
                    + "            //console.log(\"tool selenium detecion\");\n"
                    + "            return false;\n"
                    + "        }\n"
                    + "    });"
                    + "    console.log('inject facking');"
                    + "", driver);
            getJS("Object.defineProperty(Document.prototype, \"userAgent\", {\n"
                    + "        get() {\n"
                    + "            //console.log(\"userAgent detecion\");\n"
                    + "            return '" + useragent + "';\n"
                    + "        }\n"
                    + "    });"
                    + "    console.log('inject facking');"
                    + "", driver);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean clickElementByXpathAndWait(String xpath, WebDriver driver, int time) {
        if (runJS("document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(0).click();", driver)) {
            Sleep(time);
            return true;
        }
        return false;
    }

    public static boolean clickElementByXpath(String xpath, WebDriver driver) {
        return runJS("document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(0).click();", driver);
    }

    public static String getJS(String cmd, WebDriver driver) {
        String result = "";
        try {
            result = String.valueOf(((JavascriptExecutor) driver).executeScript(cmd, new Object[0]));
        } catch (Exception e) {
            //e.printStackTrace();
            return "";
        }
        return result;
    }

    public static boolean runJS(String cmd, WebDriver driver) {
        try {
            ((JavascriptExecutor) driver).executeScript(cmd, new Object[0]);
        } catch (Exception e) {
            // e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean isExist(String cmd, WebDriver driver) {
        if (getLengthJS(cmd, driver) == 0) {
            return false;
        } else {
            return true;
        }
    }

    public static String getContentText(String xpath, WebDriver driver) {
        String result = "";
        try {
            String query = "return document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(0).textContent";
            //System.out.println(query);
            result = String.valueOf(((JavascriptExecutor) driver).executeScript(query, new Object[0]));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return result;
    }

    public static String getContentText(String xpath, WebDriver driver, int position) {
        String result = "";
        try {
            String query = "return document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(" + position + ").textContent";
            result = String.valueOf(((JavascriptExecutor) driver).executeScript(query, new Object[0]));
        } catch (Exception e) {
            //e.printStackTrace();
            return "";
        }
        return result;
    }

    public static String getAttibute(String xpath, WebDriver driver, String key) {
        String result = "";
        try {
            String query = "return document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(0).getAttribute('" + key + "')";
            //System.out.println(query);
            result = String.valueOf(((JavascriptExecutor) driver).executeScript(query, new Object[0]));
        } catch (Exception e) {
            //e.printStackTrace();
            return "";
        }
        return result;
    }

    public static String setAttibute(String xpath, WebDriver driver, String key, String value) {
        String result = "";
        try {
            String query = "return document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(0).setAttribute('" + key + "','" + value + "')";
            //System.out.println(query);
            result = String.valueOf(((JavascriptExecutor) driver).executeScript(query, new Object[0]));
        } catch (Exception e) {
            //e.printStackTrace();
            return "";
        }
        return result;
    }

    public static Boolean setAttibute(String xpath, WebDriver driver, int position, String key, String value) {
        try {
            String query = "return document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(" + position + ").setAttribute('" + key + "','" + value + "')";
            //System.out.println(query);
            String.valueOf(((JavascriptExecutor) driver).executeScript(query, new Object[0]));
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }

    }

    public static String removeClass(String xpath, WebDriver driver, String _class) {
        String result = "";
        try {
            String query = "return document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(0).classList.remove(\"" + _class + "\")";
            //System.out.println(query);
            result = String.valueOf(((JavascriptExecutor) driver).executeScript(query, new Object[0]));
        } catch (Exception e) {
            //e.printStackTrace();
            return "";
        }
        return result;
    }

    public static Boolean removeClass(String xpath, WebDriver driver, int position, String _class) {
        try {
            String query = "return document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(" + position + ").classList.remove(\"" + _class + "\")";
            //System.out.println(query);
            String.valueOf(((JavascriptExecutor) driver).executeScript(query, new Object[0]));
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    //Object aa=executor.executeScript("var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;", element);
    public static String getLLAttibuteOfElement(WebElement element, WebDriver driver, String key) {
        String result = "";
        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            Object aa = executor.executeScript("var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;", element);
            result = aa.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return result;
    }

    public static Boolean runCustomJS(String customQuery, WebDriver driver) {
        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript(customQuery, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Boolean removeAllElement(String xpath, WebDriver driver) {
        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            String scriptQuery = "var length = document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotLength;\n"
                    + "for(var i=0; i<length; i++){\n"
                    + "  var item=document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(0);\n"
                    + "  item.parentNode.removeChild(item);\n"
                    + "}";
            executor.executeScript(scriptQuery, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Boolean removeAllSiblingElement(String xpath, WebDriver driver) {
        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            String scriptQuery = "var removeElement = document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(0);\n"
                    + "while(removeElement.nextSibling){\n"
                    + "   removeElement.parentNode.removeChild(removeElement.nextSibling);\n"
                    + "}\n"
                    + "removeElement.parentNode.removeChild(removeElement);";

            //System.out.println("removeAllSiblingElement " + scriptQuery);
            executor.executeScript(scriptQuery, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Boolean removeAllClassOfElements(String xpath, WebDriver driver, String className) {

        String style = "";
        if (className.equals(CHECK_HIDE_CSS_CLASS)) {
            style = "item.style.display = \"block\"";
        } else {
            style = "item.style.border = \"\"";
        }

        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            String cmd = "var length = document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotLength;\n"
                    + "for(var i=0; i<length; i++){\n"
                    + "	var item=document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(0);\n"
                    + "	if(item!=null){\n"
                    + "		 item.classList.remove('" + className + "');\n"
                    + "                               " + style + ";\n"
                    + "\n"
                    + "	}\n"
                    + "}";
            //System.err.println("removeAllClassOfElements cmd " + cmd);
            executor.executeScript(cmd, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Boolean addAllClassOfElements(String xpath, WebDriver driver, String className) {
        String style = "";
        if (className.equals(CHECK_HIDE_CSS_CLASS)) {
            style = "item.style.display = \"none\"";
        } else {
            if (className.equals(CHECK_CSS_CLASS)) {
                style = "item.style.border = \"5px solid red\"";
            } else {
                style = "item.style.border = \"5px solid blue\"";
            }
        }
        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            String cmd = "var length = document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotLength;\n"
                    + "for(var i=0; i<length; i++){\n"
                    + "  var item=document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(i);\n"
                    + "  item.classList.add('" + className + "');\n"
                    + "  " + style + ";\n"
                    + "}";
            //System.err.println(cmd);

            executor.executeScript(cmd, new Object[0]);

        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Boolean addClassForElementByXpathPosition(String xpath, WebDriver driver, String className, int position) {
        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript(" var item=document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(" + position + ");\n"
                    + "  item.classList.add('" + className + "')", new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Boolean addClassForElementByWebElement(WebDriver driver, WebElement webElement, String className) {
        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].classList.add('" + className + "')", webElement);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getAttibute(String xpath, WebDriver driver, int position, String key) {
        String result = "";
        try {
            String query = "return document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(" + position + ").getAttribute('" + key + "')";
            result = String.valueOf(((JavascriptExecutor) driver).executeScript(query, new Object[0]));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return result;
    }

    public static String getAttibute(WebDriver driver, WebElement element, String key) {
        String result = "";
        try {
            String cmd = "return arguments[0].getAttribute('" + key + "')";
            result = String.valueOf(((JavascriptExecutor) driver).executeScript(cmd, element));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return result;
    }

    public static Boolean addClass(String xpath, WebDriver driver, int position, String _class) {
        try {
            String query = "document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(" + position + ").classList.add(\"" + _class + "\");";
            ((JavascriptExecutor) driver).executeScript(query, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Boolean sendValueToText(String xpath, WebDriver driver, String text) {
        try {
            String query = "document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(0).value='" + text + "';";
            //System.out.println("query " + query);
            ((JavascriptExecutor) driver).executeScript(query, new Object[0]);
        } catch (Exception e) {
            String datetime = StringUtil.getCurrentDateTime("ddMMyyhhmmss");
            SeleniumUtils.saveErrorImage(driver, ERROR_FOLDER + "\\" + datetime + ".jpg");
            e.printStackTrace();
            return false;
        }
        return true;
    }
//

    /**
     *
     * @param xpath
     * @param driver
     * @param position
     * @param waitLoading
     * @return position = -1 : random position
     */
    public static Boolean clickElement(String xpath, WebDriver driver, int position, boolean waitLoading) {
        try {
            String query = "";
            if (position == -1) {
                query = "document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(" + new Random().nextInt(getLengthJS(xpath, driver)) + ").click();";
            } else {
                query = "document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(" + position + ").click();";
            }
            //System.out.println("query "+query);
            ((JavascriptExecutor) driver).executeScript(query, new Object[0]);
            if (waitLoading) {
                waitForPageLoad(driver, 30);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Boolean clickElement(String xpath, WebDriver driver) {
        try {
            String query = "document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(0).click();";
            //System.err.println("query " + query);
            ((JavascriptExecutor) driver).executeScript(query, new Object[0]);
        } catch (Exception e) {
            //String datetime = StringUtil.getCurrentDateTime("ddMMyyhhmmss");
            //SeleniumUtils.saveErrorImage(driver, ERROR_FOLDER + "\\" + datetime + ".jpg");
            //e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Boolean clickElementWithPosition(String xpath, WebDriver driver, int position) {
        try {
            String query = "document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(" + position + ").click();";
            //System.err.println("query " + query);
            ((JavascriptExecutor) driver).executeScript(query, new Object[0]);
        } catch (Exception e) {
            //String datetime = StringUtil.getCurrentDateTime("ddMMyyhhmmss");
            //SeleniumUtils.saveErrorImage(driver, ERROR_FOLDER + "\\" + datetime + ".jpg");
            //e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getText(String xpath, WebDriver driver) {
        String result = "";
        try {
            result = String.valueOf(((JavascriptExecutor) driver).executeScript("return document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(0).innerHTML", new Object[0]));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return result;
    }

    public static String getText(String xpath, WebDriver driver, int position) {
        String result = "";
        try {
            result = String.valueOf(((JavascriptExecutor) driver).executeScript("return document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(" + position + ").innerHTML", new Object[0]));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return result;
    }

    public static String getText(WebDriver driver, WebElement webElement) {
        String result = "";
        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            Object aa = executor.executeScript("return arguments[0].innerHTML", webElement);
            result = aa.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return result;
    }

    public static String getValue(WebDriver driver, String xpath) {
        String result = "";
        try {
            String fullPath = "document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(0).value";
            result = String.valueOf(((JavascriptExecutor) driver).executeScript("return " + fullPath, new Object[0]));;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return result;
    }

    public static String getContentText(WebDriver driver, WebElement webElement) {
        String result = "";
        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            Object aa = executor.executeScript("return arguments[0].textContent", webElement);
            result = aa.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return result.trim();
    }

    public static int getLengthJS(String xpath, WebDriver driver) {
        int result = 0;
        try {
            String cmd = "return document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotLength";
            //System.out.println("getLengthJS " + cmd);
            result = Integer.parseInt(String.valueOf(((JavascriptExecutor) driver).executeScript(cmd, new Object[0])));
        } catch (Exception e) {
            //e.printStackTrace();
            return 0;
        }
        return result;
    }

    public static void Sleep(int longTime) {
        try {
            Thread.sleep(longTime);
        } catch (InterruptedException ex) {
            //ex.printStackTrace();
        }
    }

    public static String getTitleOfVideo(String videoID, String api) {
        String title = "";
        URLConnection yc = null;
        BufferedReader in = null;
        try {
            String query = "https://www.googleapis.com/youtube/v3/videos?key=" + api + "&part=snippet,contentDetails&id=" + videoID;
            URL url = new URL(query);
            yc = url.openConnection();
            in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
            JSONParser parser = new JSONParser();
            JSONObject a = (JSONObject) parser.parse(in);
            if (a != null) {
                if (((JSONArray) a.get("items")).size() != 0) {
                    JSONObject b = (JSONObject) ((JSONArray) a.get("items")).get(0);
                    title = String.valueOf(((JSONObject) b.get("snippet")).get("title"));
                }
            }
        } catch (MalformedURLException ex) {

        } catch (IOException ex) {

        } catch (ParseException ex) {
            Logger.getLogger(SeleniumJSUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (yc != null) {
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {

                }
            }
        }
        return title;
    }
}
