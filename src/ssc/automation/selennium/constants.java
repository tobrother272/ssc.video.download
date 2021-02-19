/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.automation.selennium;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import ssc.proxy.ProxyInfo;
import ssc.proxy.ProxyRackSetting;
import Setting.ToolSetting;
import static ssc.automation.selennium.constants.WINDOW_APP_PATH.FAKING_ADDON;
import static ssc.automation.selennium.constants.WINDOW_APP_PATH.GECKO;
import Utils.Constants;
import static Utils.Constants.DATA.RESOURCE_TOOL;
import Utils.MyFileUtils;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import ssc.automation.firefox.ChangeLineInFile;
import ssc.task.GoogleInteractive;

/**
 * @author simpl
 */
public class constants {

    public static class WINDOW_APP_PATH {

        public static String FIREFOX_PROFILE_URI = System.getProperty("user.home") + "\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles";
        public static String GECKO =System.getProperty("user.dir") + File.separator+"tool"+File.separator+"geckodriver"+(System.getProperty("os.name").contains("Linux")?"":".exe");
        public static String DATA = "D:\\Program Files\\Microvirt\\MEmu\\MemuHyperv VMs\\MEmu";
        public static String APP_PATH = "D:\\Program Files\\Microvirt\\MEmu\\MEmu.exe";
        public static String MEMU_CONSOLE = "D:\\Program Files\\Microvirt\\MEmu\\MEmuConsole.exe";
        public static String MEMU_C = "D:\\Program Files\\Microvirt\\MEmu\\memuc.exe";
        public static String ADB_C = "D:\\Program Files\\Microvirt\\MEmu\\adb.exe";
        public static String MEMU_C_FOLDER = "D:\\Program Files\\Microvirt\\MEmu";
        public static String MEMU_MANAGER = "D:\\Program Files\\Microvirt\\MEmuHyperv\\MEmuManage.exe";
        public static String FAKING_ADDON = RESOURCE_TOOL + "\\ext\\faking.xpi";
        public static String FAKING_ADDON_LOGIN = RESOURCE_TOOL + "\\ext\\fakinglogin.xpi";
        public static String CONFIG_PAGE = RESOURCE_TOOL + "\\fingerprintConfig.html";
        public static String CONFIG_EXTENSION = RESOURCE_TOOL + "\\extensions.json";
        public static String MEMU_MANAGER_FOLDER = "D:\\Program Files\\Microvirt\\MEmuHyperv";
        public static String WINDOW_VERSION = "WINDOW_VERSION";
    }
    public static ObservableList<String> optionUserAgentString
            = FXCollections.observableArrayList(
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:72.0) Gecko/20100101 Firefox/72.0",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:62.0) Gecko/20100101 Firefox/62.0",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:69.0) Gecko/20100101 Firefox/69.0",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:67.0) Gecko/20100101 Firefox/67.0"
            );
    public static ObservableList<String> GRAPHIC_CARD
            = FXCollections.observableArrayList(
                    "ANGLE (NVIDIA GeForce GTX 1060 3GB Direct3D11 vs_5_0 ps_5_0)",
                    "ANGLE (NVIDIA GeForce GTX 1060 6GB Direct3D11 vs_5_0 ps_5_0)",
                    "ANGLE (NVIDIA GeForce GTX 1650 4GB Direct3D11 vs_5_0 ps_5_0)",
                    "ANGLE (Microsoft Basic Render Driver Direct3D11 vs_5_0 ps_5_0)",
                    "ANGLE (AMD Radeon RX 560 Direct3D11 vs_5_0 ps_5_0)",
                    "ANGLE (NVIDIA GeForce GTX 660 2GB Direct3D11 vs_5_0 ps_5_0)",
                    "ANGLE (NVIDIA GeForce GTX 460 2GB Direct3D11 vs_5_0 ps_5_0)",
                    "ANGLE (NVIDIA GeForce GTX 760 2GB Direct3D11 vs_5_0 ps_5_0)",
                    "ANGLE (NVIDIA GeForce GTX 970 2GB Direct3D11 vs_5_0 ps_5_0)",
                    "ANGLE (NVIDIA GeForce GTX 1650 Direct3D11 vs_5_0 ps_5_0)",
                    "ANGLE (Radeon RX 470 4GB Direct3D11 vs_5_0 ps_5_0)",
                    "ANGLE (Radeon RX 470 8GB Direct3D11 vs_5_0 ps_5_0)",
                    "ANGLE (Radeon RX 570 4GB Direct3D11 vs_5_0 ps_5_0)",
                    "ANGLE (Radeon RX 580 4GB Driver Direct3D11 vs_5_0 ps_5_0)",
                    "ANGLE (Radeon RX 590 4GB Driver Direct3D11 vs_5_0 ps_5_0)"
            );
    public static ObservableList<String> optionUserAgentStringReach
            = FXCollections.observableArrayList(
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.79 Safari/537.36",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36"
            );
    public static String PROFILE_DEFAULT = Constants.DATA.RESOURCE_DATA + "\\MEmu.zip";
    public static String OVA = Constants.DATA.RESOURCE_DATA + "\\4.ova";

    public static void setCache(FirefoxProfile ff, Boolean enable) {
        ff.setPreference("browser.cache.disk.enable", enable);
        ff.setPreference("browser.cache.memory.enable", enable);
        ff.setPreference("browser.cache.offline.enable", enable);
        ff.setPreference("network.http.use-cache", enable);
        ff.setPreference("media.cache_resume_threshold", 3);
        ff.setPreference("media.cache_readahead_limit", 3);
        ff.setPreference("browser.cache.memory.capacity", 64000);
        ff.setPreference("media.cache_size", 64000);
        ff.setPreference("browser.cache.disk.max_chunks_memory_usage", 4096);
        ff.setPreference("browser.cache.disk.max_priority_chunks_memory_usage", 4096);
        ff.setPreference("browser.cache.disk.metadata_memory_limit", 250);
        ff.setPreference("browser.preferences.defaultPerformanceSettings.enabled", false);
        ff.setPreference("layers.acceleration.disabled", true);
        ff.setPreference("dom.ipc.processCount", 4);

        //ff.setPreference("browser.link.open_newwindow", 3);
    }

    public static WebDriver getRemoteChrome(String ipPort) {
        WebDriver driver = null;
        try {
            //System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability("version", "");
            capabilities.setJavascriptEnabled(true);
            capabilities.setPlatform(Platform.LINUX);
            //ChromeOptions options = new ChromeOptions();
            //options.addArguments("chrome.switches", "--disable-extensions");
            //options.addArguments("user-data-dir=" + "");
            //capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            driver = new RemoteWebDriver(new URL("http://" + ipPort + "/wd/hub"), capabilities);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return driver;

        // return getChromeDriver();
    }

    public static WebDriver getFireFoxGECKOHeadLess(String useragent) {
        WebDriver driver = null;
        try {
            //System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
            System.setProperty("webdriver.gecko.driver", GECKO);
            System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
            FirefoxOptions options = new FirefoxOptions();
            FirefoxProfile ff = new FirefoxProfile();
            ff.setAcceptUntrustedCertificates(true);
            ff.setAssumeUntrustedCertificateIssuer(false);
            ff.setAcceptUntrustedCertificates(true);
            ff.setPreference("general.useragent.override", useragent);
            options.setProfile(ff);
            options.setHeadless(false);

            driver = new FirefoxDriver(options);
            //driver = new FirefoxDriver(ff);
        } catch (Exception ex) {
            ex.printStackTrace();
            //StringWriter sw = new StringWriter();
            //ex.printStackTrace(new PrintWriter(sw));
            //ex.toString();

        }
        return driver;

        // return getChromeDriver();
    }

    public static WebDriver getFireFoxGECKOHeadLess(String useragent, ProxyInfo pi) {
        WebDriver driver = null;
        try {
            //System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
            System.setProperty("webdriver.gecko.driver", GECKO);
            System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
            FirefoxOptions options = new FirefoxOptions();
            FirefoxProfile ff = new FirefoxProfile();
            ff.setAcceptUntrustedCertificates(true);
            ff.setAssumeUntrustedCertificateIssuer(false);
            ff.setAcceptUntrustedCertificates(true);
            ff.setPreference("general.useragent.override", useragent);
            if (pi != null) {
                int port = pi.getPort();
                if (port == 999999) {
                    port = ProxyRackSetting.getInstance().getFreePort();
                }
                ff.setPreference("network.automatic-ntlm-auth.trusted-uris", pi.getIp() + ":" + port);
                ff.setPreference("network.proxy.type", 1);

                if (pi.getMode() == 0) {
                    ff.setPreference("network.proxy.http", pi.getIp());
                    ff.setPreference("network.proxy.http_port", port);
                    ff.setPreference("network.proxy.socks", pi.getIp());
                    ff.setPreference("network.proxy.socks_port", port);
                    ff.setPreference("network.proxy.ssl", pi.getIp());
                    ff.setPreference("network.proxy.ssl_port", port);
                    ff.setPreference("network.proxy.ftp", pi.getIp());
                    ff.setPreference("network.proxy.ftp_port", port);
                    ff.setPreference("network.proxy.share_proxy_settings", true);

                    /*
                    ff.setPreference("network.proxy.socks", pi.getIp());
                    ff.setPreference("network.proxy.socks_port", port);
                    ff.setPreference("network.proxy.socks_version", 5);
                     */
                } else if (pi.getMode() == 1) {
                    ff.setPreference("network.proxy.http", "");
                    ff.setPreference("network.proxy.http_port", "");
                    ff.setPreference("network.proxy.ssl", "");
                    ff.setPreference("network.proxy.ssl_port", "");
                    ff.setPreference("network.proxy.ftp", "");
                    ff.setPreference("network.proxy.ftp_port", "");
                    ff.setPreference("network.proxy.share_proxy_settings", false);
                    //
                    ff.setPreference("network.proxy.socks", pi.getIp());
                    ff.setPreference("network.proxy.socks_port", port);
                    ff.setPreference("network.proxy.socks_version", 4);
                } else {
                    ff.setPreference("network.proxy.http", "");
                    ff.setPreference("network.proxy.http_port", "");
                    ff.setPreference("network.proxy.ssl", "");
                    ff.setPreference("network.proxy.ssl_port", "");
                    ff.setPreference("network.proxy.ftp", "");
                    ff.setPreference("network.proxy.ftp_port", "");
                    ff.setPreference("network.proxy.share_proxy_settings", false);
                    //
                    ff.setPreference("network.proxy.socks", pi.getIp());
                    ff.setPreference("network.proxy.socks_port", port);
                    ff.setPreference("network.proxy.socks_version", 5);
                }
            } else {
                ff.setPreference("network.proxy.type", 0);
            }
            options.setProfile(ff);
            options.setHeadless(true);
            driver = new FirefoxDriver(options);
            //driver = new FirefoxDriver(ff);
        } catch (Exception ex) {
            ex.printStackTrace();
            //StringWriter sw = new StringWriter();
            //ex.printStackTrace(new PrintWriter(sw));
            //ex.toString();

        }
        return driver;

        // return getChromeDriver();
    }

    public static void initProfile(FirefoxProfile ff) {
        ff.setAcceptUntrustedCertificates(true);
        ff.setAssumeUntrustedCertificateIssuer(false);
        ff.setPreference("browser.download.folderList", 2);
        ff.setPreference("browser.download.manager.showWhenStarting", false);
        ff.setPreference("media.autoplay.default", 0);
        ff.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv,application/pdf,application/csv,application/vnd.ms-excel");
        ff.setPreference("browser.download.manager.showAlertOnComplete", false);
        ff.setPreference("browser.download.manager.showAlertOnComplete", false);
        ff.setPreference("browser.download.manager.showWhenStartinge", false);
        ff.setPreference("browser.download.panel.shown", false);
        ff.setPreference("browser.download.useToolkitUI", true);
        ff.setPreference("media.peerconnection.enabled", !ToolSetting.getInstance().getDisableRtc());
        ff.setPreference("pdfjs.disabled", true);
        ff.setPreference("pdfjs.firstRun", true);
        ff.setPreference("app.update.enabled", false);
        ff.setPreference("browser.search.update", false);
        ff.setPreference("extensions.autoDisableScopes", 10);
        ff.setPreference("extensions.blocklist.enabled", false);
        ff.setPreference("extensions.checkCompatibility", "extensions.checkCompatibility.43.0");
        ff.setPreference("extensions.checkCompatibility.nightly", false);
        ff.setPreference("extensions.logging.enabled", true);
        ff.setPreference("extensions.shownSelectionUI", true);
        ff.setPreference("browser.privatebrowsing.autostart", false);
        ff.setPreference("security.csp.enable", false);
        ff.setPreference("xpinstall.signatures.required", false);
        ff.setPreference("extensions.update.enabled", false);
        ff.setPreference("extensions.update.notifyUser", false);
        ff.setPreference("devtools.selfxss.count", 1000);
        ff.setPreference("security.csp.enable", false);
        ff.setPreference("extensions.browserfaking.onByDefault", true);
        ff.setPreference("network.proxy.type", 0);
        ff.setAcceptUntrustedCertificates(true);
    }

    public static WebDriver getFireFoxGECKOHeadLess() {
        WebDriver driver = null;
        try {
            System.setProperty("webdriver.gecko.driver", GECKO);
            FirefoxOptions options = new FirefoxOptions();
            FirefoxProfile ff = new FirefoxProfile();
            ff.setPreference("general.useragent.override", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:79.0) Gecko/20100101 Firefox/79.0");
            initProfile(ff);
            FirefoxBinary binary = new FirefoxBinary(new File(ToolSetting.getInstance().getFirefoxPath()));
            options.setBinary(binary);
            options.setProfile(ff);
            options.setHeadless(true);
            driver = new FirefoxDriver(options);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return driver;
    }

    public static WebDriver getFireFoxGECKO(String profile_path, String useragent) {
        if (profile_path.length() != 0) {
            File listChildProfile[] = new File(profile_path).listFiles();
            for (File file : listChildProfile) {
                if (file.isDirectory() && (file.getName().contains("cache2") || file.getName().contains("gmp") || file.getName().contains("startupCache") || file.getName().contains("safebrowsing"))) {
                    MyFileUtils.deleteFileInFolder(file);
                }
            }
        }
        WebDriver driver = null;
        try {
            System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
            System.setProperty("webdriver.gecko.driver", GECKO);
            FirefoxOptions options = new FirefoxOptions();
            FirefoxProfile ff = null;
            if (profile_path.length() != 0) {
                ff = new FirefoxProfile(new File(profile_path));
            } else {
                ff = new FirefoxProfile();
            }
            ff.setAcceptUntrustedCertificates(true);
            ff.setAssumeUntrustedCertificateIssuer(false);
            ff.setPreference("browser.download.folderList", 2);
            if (useragent.length() != 0) {
                //ff.setPreference("general.useragent.override", useragent);
            }
            setPre(ff, profile_path);
            ff.setPreference("network.proxy.type", 0);
            //ff.addExtension(new File(FAKING_ADDON));
            ff.setPreference("extensions.browserfaking.onByDefault", true);
            ff.setAcceptUntrustedCertificates(true);
            options.setProfile(ff);
            FirefoxBinary binary = new FirefoxBinary(new File(ToolSetting.getInstance().getFirefoxPath()));
            options.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options.setBinary(binary));
            driver = new FirefoxDriver(options);
        } catch (Exception ex) {
            ex.printStackTrace();
            //StringWriter sw = new StringWriter();
            //ex.printStackTrace(new PrintWriter(sw));
            //ex.toString();

        }
        return driver;

        // return getChromeDriver();
    }

    public static WebDriver getFireFoxGECKO() {
        WebDriver driver = null;
        try {
            System.setProperty("webdriver.gecko.driver", GECKO);
            FirefoxOptions options = new FirefoxOptions();
            FirefoxProfile ff = new FirefoxProfile();
            initProfile(ff);
            ff.addExtension(new File(FAKING_ADDON));
            FirefoxBinary binary = new FirefoxBinary(new File(ToolSetting.getInstance().getFirefoxPath()));
            options.setBinary(binary);
            options.setProfile(ff);

            driver = new FirefoxDriver(options);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return driver;
    }

    public static WebDriver getFireFoxGECKO(String profile_path, String useragent, ProxyInfo pi, String fackingAddon, GoogleInteractive task) {
        if (profile_path.length() != 0) {
            File listChildProfile[] = new File(profile_path).listFiles();
            for (File file : listChildProfile) {
                if (file.isDirectory() && (file.getName().contains("cache2") || file.getName().contains("gmp") || file.getName().contains("startupCache") || file.getName().contains("safebrowsing"))) {
                    MyFileUtils.deleteFileInFolder(file);
                }
            }
        }

        if (profile_path.length() != 0) {
            MyFileUtils.deleteFileInFolder(new File(profile_path + "\\extensions"));
        }

        WebDriver driver = null;
        try {
            System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
            FirefoxOptions options = new FirefoxOptions();
            options.setLogLevel(FirefoxDriverLogLevel.TRACE);
            FirefoxProfile ff = null;
            if (!ToolSetting.getInstance().getPre().getBoolean("TESTMODE", false) && profile_path.length() != 0) {
                ff = new FirefoxProfile(new File(profile_path));
            } else {
                ff = new FirefoxProfile();
            }
            ff.setAcceptUntrustedCertificates(true);
            ff.setAssumeUntrustedCertificateIssuer(false);
            ff.setPreference("browser.download.folderList", 2);
            if (useragent.length() != 0 && ToolSetting.getInstance().getFakeUseragent()) {
                ff.setPreference("general.useragent.override", useragent);
            }
            setPre(ff, profile_path);

            try {
                if (profile_path.length() != 0 && new File(profile_path).exists()) {
                    ff.setPreference("browser.startup.homepage", new File(profile_path).getParent() + "\\fingerprintConfig.html");
                }
            } catch (Exception e) {
            }
            File arrAddon[] = new File(ToolSetting.getInstance().getAddonPath()).listFiles();
            if (ToolSetting.getInstance().getAddonPath().length() != 0 && arrAddon != null) {
                ff.setPreference("extensions.browserfaking.onByDefault", true);
                for (File file : arrAddon) {
                    ff.addExtension(file);
                }
            }
            setCache(ff, false);
            ff.setAcceptUntrustedCertificates(true);
            options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
            if (pi != null) {
                int port = pi.getPort();
                if (port == 999999) {
                    port = ProxyRackSetting.getInstance().getFreePort();
                }
                ff.setPreference("network.automatic-ntlm-auth.trusted-uris", pi.getIp() + ":" + port);
                ff.setPreference("network.proxy.type", 1);

                if (pi.getMode() == 0) {
                    ff.setPreference("network.proxy.http", pi.getIp());
                    ff.setPreference("network.proxy.http_port", port);
                    ff.setPreference("network.proxy.socks", pi.getIp());
                    ff.setPreference("network.proxy.socks_port", port);
                    ff.setPreference("network.proxy.ssl", pi.getIp());
                    ff.setPreference("network.proxy.ssl_port", port);
                    ff.setPreference("network.proxy.ftp", pi.getIp());
                    ff.setPreference("network.proxy.ftp_port", port);
                    ff.setPreference("network.proxy.share_proxy_settings", true);
                } else if (pi.getMode() == 1) {
                    ff.setPreference("network.proxy.http", "");
                    ff.setPreference("network.proxy.http_port", "");
                    ff.setPreference("network.proxy.ssl", "");
                    ff.setPreference("network.proxy.ssl_port", "");
                    ff.setPreference("network.proxy.ftp", "");
                    ff.setPreference("network.proxy.ftp_port", "");
                    ff.setPreference("network.proxy.share_proxy_settings", false);
                    //
                    ff.setPreference("network.proxy.socks", pi.getIp());
                    ff.setPreference("network.proxy.socks_port", port);
                    ff.setPreference("network.proxy.socks_version", 4);
                } else {
                    ff.setPreference("network.proxy.http", "");
                    ff.setPreference("network.proxy.http_port", "");
                    ff.setPreference("network.proxy.ssl", "");
                    ff.setPreference("network.proxy.ssl_port", "");
                    ff.setPreference("network.proxy.ftp", "");
                    ff.setPreference("network.proxy.ftp_port", "");
                    ff.setPreference("network.proxy.share_proxy_settings", false);
                    ff.setPreference("network.proxy.socks", pi.getIp());
                    ff.setPreference("network.proxy.socks_port", port);
                    ff.setPreference("network.proxy.socks_version", 5);
                }
            } else {
                ff.setPreference("network.proxy.type", 0);
            }
            FirefoxBinary binary = new FirefoxBinary(new File(ToolSetting.getInstance().getFirefoxPath()));
            options.setBinary(binary);
            options.setProfile(ff);
            driver = new FirefoxDriver(options);
        } catch (WebDriverException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return driver;
    }

    public static void setPre(FirefoxProfile ff, String profile) {
        ff.setPreference("browser.download.manager.showWhenStarting", false);
        ff.setPreference("media.autoplay.default", 0);
        ff.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv,application/pdf,application/csv,application/vnd.ms-excel");
        ff.setPreference("browser.download.manager.showAlertOnComplete", false);
        ff.setPreference("browser.download.manager.showAlertOnComplete", false);
        ff.setPreference("browser.download.manager.showWhenStartinge", false);
        ff.setPreference("browser.download.panel.shown", false);
        ff.setPreference("browser.download.useToolkitUI", true);
        ff.setPreference("media.peerconnection.enabled", !ToolSetting.getInstance().getDisableRtc());
        ff.setPreference("pdfjs.disabled", true);
        ff.setPreference("pdfjs.firstRun", true);
        ff.setPreference("app.update.enabled", false);
        ff.setPreference("browser.search.update", false);
        ff.setPreference("devtools.selfxss.count", 1000);
        if (profile.length() != 0 && new File(new File(profile).getParent() + "\\fingerprintConfig.html").exists()) {
            ff.setPreference("browser.startup.homepage", new File(profile).getParent() + "\\fingerprintConfig.html");
        }
        ff.setPreference("extensions.autoDisableScopes", 10);
        ff.setPreference("extensions.blocklist.enabled", false);
        ff.setPreference("extensions.checkCompatibility", "extensions.checkCompatibility.43.0");
        ff.setPreference("extensions.checkCompatibility.nightly", false);
        ff.setPreference("extensions.logging.enabled", true);
        ff.setPreference("extensions.shownSelectionUI", true);
        ff.setPreference("browser.privatebrowsing.autostart", false);
        ff.setPreference("security.csp.enable", false);
        ff.setPreference("xpinstall.signatures.required", false);
        ff.setPreference("extensions.update.enabled", false);
        ff.setPreference("extensions.update.notifyUser", false);
        ff.setPreference("devtools.selfxss.count", 1000);
        setCache(ff, false);
    }

    public static WebDriver getFireFoxGECKO(String profile_path, String useragent, String fackingAddon, GoogleInteractive task) {
        if (profile_path.length() != 0) {
            File listChildProfile[] = new File(profile_path).listFiles();
            for (File file : listChildProfile) {
                if (file.isDirectory() && (file.getName().contains("cache2") || file.getName().contains("gmp") || file.getName().contains("startupCache") || file.getName().contains("safebrowsing"))) {
                    MyFileUtils.deleteFileInFolder(file);
                }
            }
        }
        WebDriver driver = null;
        try {
            //ChangeLineInFile.changeFirefoxPre(profile_path+"\\user.js", "browser.link.open_newwindow", 3);
            if (profile_path.length() != 0) {
                try {
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(new FileReader(profile_path + "\\extensions.json"));
                    JSONObject jsonObject = (JSONObject) obj;
                    JSONArray arr = (JSONArray) jsonObject.get("addons");
                    JSONArray arrN = new JSONArray();
                    Iterator i = arr.iterator();
                    while (i.hasNext()) {
                        JSONObject jo = (JSONObject) i.next();
                        if (jo.get("id").toString().contains("faking@simplesolution.co")) {
                            continue;
                        }
                        arrN.add(jo);
                    }
                    jsonObject.put("addons", arrN);
                    FileWriter file = new FileWriter(profile_path + "\\extensions.json");
                    file.write(jsonObject.toJSONString());
                } catch (Exception e) {

                }
            }
            System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
            System.setProperty("webdriver.gecko.driver", GECKO);
            ChangeLineInFile.changeFirefoxPre(profile_path + "\\user.js", "browser.link.open_newwindow", 3);
            ChangeLineInFile.changeFirefoxPre(profile_path + "\\prefs.js", "browser.link.open_newwindow", 3);
            FirefoxOptions options = new FirefoxOptions();
            options.setLogLevel(FirefoxDriverLogLevel.TRACE);
            FirefoxProfile ff = null;
            if (profile_path.length() != 0) {
                ff = new FirefoxProfile(new File(profile_path));
            } else {
                ff = new FirefoxProfile();
            }
            ff.setAcceptUntrustedCertificates(true);
            ff.setAssumeUntrustedCertificateIssuer(false);
            ff.setPreference("browser.download.folderList", 2);
            if (useragent.length() != 0 && ToolSetting.getInstance().getFakeUseragent()) {
                ff.setPreference("general.useragent.override", useragent);
            }
            if (ToolSetting.getInstance().getPre().getBoolean("chLoginWithAppleTv", false)) {
                ff.setPreference("general.useragent.override", "AppleTV6,2/11.1");
            }
            ff.setPreference("network.proxy.http", "");
            ff.setPreference("network.proxy.http_port", "");
            ff.setPreference("network.proxy.ssl", "");
            ff.setPreference("network.proxy.ssl_port", "");
            ff.setPreference("network.proxy.ftp", "");
            ff.setPreference("network.proxy.ftp_port", "");
            ff.setPreference("network.proxy.socks", "");
            ff.setPreference("network.proxy.socks_port", "");
            ff.setPreference("network.proxy.share_proxy_settings", false);
            setPre(ff,profile_path);
            ff.setPreference("security.csp.enable", false);
            ff.setPreference("network.proxy.type", 0);
            File arrAddon[] = new File(ToolSetting.getInstance().getAddonPath()).listFiles();
            if (ToolSetting.getInstance().getAddonPath().length() != 0 && arrAddon != null) {
                ff.setPreference("extensions.browserfaking.onByDefault", true);
                for (File file : arrAddon) {
                    ff.addExtension(file);
                }
            }
            ff.setAcceptUntrustedCertificates(true);
            FirefoxBinary binary = new FirefoxBinary(new File(ToolSetting.getInstance().getFirefoxPath()));
            options.setBinary(binary);
            options.setProfile(ff);
            driver = new FirefoxDriver(options);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return driver;
    }

    public static WebDriver getFireFoxGECKOHeadless(String useragent) {
        WebDriver driver = null;
        try {
            System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
            System.setProperty("webdriver.gecko.driver", GECKO);
            FirefoxOptions options = new FirefoxOptions();
            FirefoxProfile ff = new FirefoxProfile();
            ff.setAcceptUntrustedCertificates(true);
            ff.setAssumeUntrustedCertificateIssuer(false);
            ff.setPreference("browser.download.folderList", 2);
            if (useragent.length() != 0 && ToolSetting.getInstance().getFakeUseragent()) {
                ff.setPreference("general.useragent.override", useragent);
            }
            setPre(ff,"");
            ff.setAcceptUntrustedCertificates(true);
            options.setProfile(ff);
            FirefoxBinary binary = new FirefoxBinary(new File(ToolSetting.getInstance().getFirefoxPath()));
            options.setBinary(binary);
            options.setHeadless(true);
            driver = new FirefoxDriver(options);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return driver;
    }
    //public static String CHROME_DRIVER = System.getProperty("user.dir") + "\\tool\\chromedriver.exe";
   
    public static String CHROME_DRIVER ="C:\\Users\\PC\\Desktop\\QniTubeAction\\enginesprotected\\21.9.2\\webdriver\\chromedriver.exe";
    
    
    public static WebDriver getFireFoxGECKOHeadless(String profile_path, String useragent, ProxyInfo pi) {
        WebDriver driver = null;
        try {
            System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
            System.setProperty("webdriver.gecko.driver", GECKO);
            FirefoxOptions options = new FirefoxOptions();
            FirefoxProfile ff = null;
            if (profile_path.length() != 0) {
                ff = new FirefoxProfile(new File(profile_path));
            } else {
                ff = new FirefoxProfile();
            }
            ff.setAcceptUntrustedCertificates(true);
            ff.setAssumeUntrustedCertificateIssuer(false);
            ff.setPreference("browser.download.folderList", 2);
            if (useragent.length() != 0 && ToolSetting.getInstance().getFakeUseragent()) {
                ff.setPreference("general.useragent.override", useragent);
            }
            setPre(ff,profile_path);
            ff.addExtension(new File(FAKING_ADDON));
            ff.setPreference("extensions.browserfaking.onByDefault", true);
            if (pi != null) {
                int port = pi.getPort();
                ff.setPreference("network.automatic-ntlm-auth.trusted-uris", pi.getIp() + ":" + port);
                ff.setPreference("network.proxy.type", 1);
                if (pi.getMode() == 0) {
                    ff.setPreference("network.proxy.http", pi.getIp());
                    ff.setPreference("network.proxy.http_port", port);
                    ff.setPreference("network.proxy.socks", pi.getIp());
                    ff.setPreference("network.proxy.socks_port", port);
                    ff.setPreference("network.proxy.ssl", pi.getIp());
                    ff.setPreference("network.proxy.ssl_port", port);
                    ff.setPreference("network.proxy.ftp", pi.getIp());
                    ff.setPreference("network.proxy.ftp_port", port);
                    ff.setPreference("network.proxy.share_proxy_settings", true);
                    /*
                    ff.setPreference("network.proxy.socks", pi.getIp());
                    ff.setPreference("network.proxy.socks_port", port);
                    ff.setPreference("network.proxy.socks_version", 5);
                     */
                } else if (pi.getMode() == 1) {
                    ff.setPreference("network.proxy.socks", pi.getIp());
                    ff.setPreference("network.proxy.socks_port", port);
                    ff.setPreference("network.proxy.socks_version", 4);
                } else {
                    ff.setPreference("network.proxy.socks", pi.getIp());
                    ff.setPreference("network.proxy.socks_port", port);
                    ff.setPreference("network.proxy.socks_version", 5);
                }
            } else {
                ff.setPreference("network.proxy.type", 0);
            }
            ff.setAcceptUntrustedCertificates(true);
            FirefoxBinary binary = new FirefoxBinary(new File(ToolSetting.getInstance().getFirefoxPath()));
            options.setBinary(binary);
            options.setProfile(ff);
            options.setHeadless(true);
            driver = new FirefoxDriver(options);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return driver;
    }

    public static WebDriver getPhanTomJs(int timeout) {
        WebDriver driver = null;
        try {
            DesiredCapabilities DesireCaps = null;
            // DesiredCapabilities.phantomjs();
            String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:67.0) Gecko/20100101 Firefox/67.0";
            String[] phantomArgs = new String[]{
                "--webdriver-loglevel=NONE"
            };
            DesireCaps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, phantomArgs);
            DesireCaps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "userAgent", userAgent);
            DesireCaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, System.getProperty("user.dir") + "\\phantomjs.exe");
            DesireCaps.setJavascriptEnabled(true);
            driver = new PhantomJSDriver(DesireCaps);
            driver.manage().window().setSize(new Dimension(1024, 768));
            driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(timeout, TimeUnit.SECONDS);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return driver;
    }

    public static WebDriver getChromeDriver() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER);
        //options.binary_location="C:\\Users\\AtechM_03\\AppData\\Local\\Google\\Chrome SxS\\Application\\chrome.exe"
        //driver = webdriver.Chrome(chrome_options=options, executable_path=r'C:\Utility\BrowserDrivers\chromedriver.exe')



        ChromeOptions options = new ChromeOptions();
        options.setBinary(new File("C:\\Users\\PC\\Desktop\\chrome-win\\chrome.exe"));
        options.addArguments("--user-agent=\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36\"");
        options.addArguments("--disable-webrtc-multiple-routes");
        options.addArguments("--window-size=300,600");
        options.addArguments("--enforce-webrtc-ip-permission-check=false");
        //options.setCapability("--autoplay-policy", "Document user activation is required.");
        WebDriver driver = new ChromeDriver(options);
        return driver;
    }
    public static WebDriver getChromiumDriver() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER);
        ChromeOptions opt = new ChromeOptions();
        opt.setBinary("C:\\Users\\PC\\AppData\\Local\\Chromium\\Application\\chrome.exe");
        //opt.setBinary("C:\\Users\\PC\\Desktop\\MyCef\\browser.exe");

        opt.addArguments("--remote-debugging-port=8001");
        
        WebDriver driver = new ChromeDriver(opt);
        
         
        
        //driver.get("https://www.google.com/");
        System.out.println(driver.getTitle());
        return driver;
    }
    

    public static WebDriver getChromeDriver(String useragent, int x, int y) {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-agent=\"" + useragent + "\"");
        options.addArguments("--disable-webrtc-multiple-routes");
        options.addArguments("--window-size=300,600");
        options.addArguments("--enforce-webrtc-ip-permission-check=false");
        //options.setCapability("--autoplay-policy", "Document user activation is required.");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().setPosition(new Point(x, y));
        return driver;
    }
}
