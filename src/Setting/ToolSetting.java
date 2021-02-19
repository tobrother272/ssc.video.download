/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Setting;

import static Utils.Constants.DATA.RESOURCE_TOOL;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Utils.MyFileUtils;
import Utils.StringUtil;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import javafx.scene.layout.AnchorPane;

/**
 * @author simplesolution.co
 */
public class ToolSetting {

    public static ObservableList<String> PERCENT
            = FXCollections.observableArrayList(
                    "0",
                    "10",
                    "20",
                    "30",
                    "40",
                    "50",
                    "60",
                    "70",
                    "80",
                    "90",
                    "100"
            );
    public static ObservableList<String> CHANGEIPMODE
            = FXCollections.observableArrayList(
                    "Không reset",
                    "Reset modem",
                    "Reset 3g",
                    "Reset tinsoft",
                    "Proxy tài khoản",
                    "Proxy TM"
            );
    public static ObservableList<String> PRIORITY
            = FXCollections.observableArrayList(
                    "1",
                    "2",
                    "3",
                    "4",
                    "5",
                    "6",
                    "7",
                    "8",
                    "9",
                    "10"
            );
    public static ObservableList<String> THREADS
            = FXCollections.observableArrayList(
                    "1 Luồng",
                    "2 Luồng",
                    "3 Luồng",
                    "4 Luồng",
                    "5 Luồng",
                    "6 Luồng",
                    "7 Luồng",
                    "8 Luồng",
                    "9 Luồng",
                    "10 Luồng"
            );
    public static ObservableList<String> RUN_MODE
            = FXCollections.observableArrayList(
                    "Chạy ngẫu nhiên",
                    "Chạy Theo Nhóm"
            );
    public static ObservableList<String> NUMBER_VIDEO_PER_LINK
            = FXCollections.observableArrayList(
                    "5",
                    "10",
                    "15",
                    "20",
                    "25",
                    "30",
                    "35",
                    "40",
                    "45",
                    "50",
                    "55",
                    "60",
                    "65",
                    "70",
                    "75",
                    "80",
                    "85",
                    "90",
                    "95",
                    "100"
            );

    public static void openUrl(String url) {
        if (System.getProperty("os.name").contains("Linux")) {
            try {
                Runtime rt = Runtime.getRuntime();
                String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror",
                    "netscape", "opera", "links", "lynx"};

                StringBuffer cmd = new StringBuffer();
                for (int i = 0; i < browsers.length; i++) {
                    if (i == 0) {
                        cmd.append(String.format("%s \"%s\"", browsers[i], url));
                    } else {
                        cmd.append(String.format(" || %s \"%s\"", browsers[i], url));
                    }
                }
                // If the first didn't work, try the next browser and so on

                rt.exec(new String[]{"sh", "-c", cmd.toString()});
            } catch (Exception e) {
            }
        } else {
            try {
                Desktop desktop = java.awt.Desktop.getDesktop();
                URI oURL = new URI(url);
                desktop.browse(oURL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void openFile(String url) {
        if(url.length()==0){
            return;
        }
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop desktop = Desktop.getDesktop();
                File myFile = new File(url);
                desktop.open(myFile);
            } catch (IOException ex) {
            }
        }
    }

    public Boolean isLinux;

    public Boolean getIsLinux() {
        return isLinux;
    }

    public void setIsLinux(Boolean isLinux) {
        this.isLinux = isLinux;
    }
    private AnchorPane apRootContainer;

    public AnchorPane getApRootContainer() {
        return apRootContainer;
    }

    public void setApRootContainer(AnchorPane apRootContainer) {
        this.apRootContainer = apRootContainer;
    }

    public void refreshScreen() {
        double w = 1378;
        double h = 768;
        double width = w;
        double height = h;
        apRootContainer.setPrefSize(width, height);
    }
    private Preferences pre;
    private String firefoxPath;
    private Boolean disableRtc;
    private Boolean fakeUseragent;
    private int timeOpenFirefox;
    private String serviceUrl;
    private String servicePass;
    private String locationPlantMode;
    private String keyPhoneApi;
    private String tmKey;
    private String gmailRecoverReg;
    private int timeReset;
    private int timeDelayReg;
    private int timeWaitImage;
    private String ethernetName;
    private String ethernet3GName;
    private String tinsoftKey;
    private String resetModemScript;
    private int resetMode;
    private int pageLoadTimeout;
    private Boolean toolReady;
    private String userToken;
    private int dayLeft;
    private String userId;
    private String breadScrumbTitle;
    private String breadScrumbDescription;
    private String description;
    private List<String> arrPrices;
    private String toolCode;
    private int phoneOperation;
    private int timeViewVideo;
    private boolean makeChannel;
    private String imageTMP;


    public String getBreadScrumbTitle() {
        return breadScrumbTitle;
    }

    public void setBreadScrumbTitle(String breadScrumbTitle) {
        this.breadScrumbTitle = breadScrumbTitle;
    }

    public String getBreadScrumbDescription() {
        return breadScrumbDescription;
    }

    public void setBreadScrumbDescription(String breadScrumbDescription) {
        this.breadScrumbDescription = breadScrumbDescription;
    }

    public void setImageTMP(String imageTMP) {
        this.imageTMP = imageTMP;
        pre.put("imageTMP", imageTMP);
    }

    public boolean isMakeChannel() {
        return makeChannel;
    }

    public void setMakeChannel(boolean makeChannel) {
        this.makeChannel = makeChannel;
        pre.putBoolean("makeChannel", makeChannel);
    }

    public int getTimeViewVideo() {
        return timeViewVideo;
    }

    public void setTimeViewVideo(int timeViewVideo) {
        this.timeViewVideo = timeViewVideo;
        pre.putInt("timeViewVideo", timeViewVideo);
    }

    public String getGmailRecoverReg() {
        return gmailRecoverReg;
    }

    public void setGmailRecoverReg(String gmailRecoverReg) {
        this.gmailRecoverReg = gmailRecoverReg;
        pre.put("gmailRecoverReg", gmailRecoverReg);
    }

    public int getTimeDelayReg() {
        return timeDelayReg;
    }

    public void setTimeDelayReg(int timeDelayReg) {
        this.timeDelayReg = timeDelayReg;
        pre.putInt("timeDelayReg", timeDelayReg);
    }

    public int getTimeWaitImage() {
        return timeWaitImage;
    }

    public void setTimeWaitImage(int timeWaitImage) {
        this.timeWaitImage = timeWaitImage;
        pre.putInt("timeWaitImage", phoneOperation);
    }

    public int getPhoneOperation() {
        return phoneOperation;
    }

    public void setPhoneOperation(int phoneOperation) {
        this.phoneOperation = phoneOperation;
        pre.putInt("phoneOperation", phoneOperation);
    }

    public String getKeyPhoneApi() {
        return keyPhoneApi;
    }

    public void setKeyPhoneApi(String keyPhoneApi) {
        this.keyPhoneApi = keyPhoneApi;
        pre.put("keyPhoneApi", keyPhoneApi);
    }

    private String addonPath;
    private int numberPasswd;
    private String recoverStructure;

    private boolean loginBySelenium;

    public boolean isLoginBySelenium() {
        return loginBySelenium;
    }

    public void setLoginBySelenium(boolean loginBySelenium) {
        this.loginBySelenium = loginBySelenium;
        pre.putBoolean("loginBySelenium", loginBySelenium);
    }

    public String getTmKey() {
        return tmKey;
    }

    public void setTmKey(String tmKey) {
        this.tmKey = tmKey;
        pre.put("tmKey", tmKey);
    }

    public String getRecoverStructure() {
        return recoverStructure;
    }

    public void setRecoverStructure(String recoverStructure) {
        this.recoverStructure = recoverStructure;
        pre.put("recoverStructure", recoverStructure);
    }

    public int getNumberPasswd() {
        return numberPasswd;
    }

    public void setNumberPasswd(int numberPasswd) {
        this.numberPasswd = numberPasswd;
        pre.putInt("numberPasswd", numberPasswd);
    }

    public String getAddonPath() {
        return addonPath;
    }

    public void setAddonPath(String addonPath) {
        this.addonPath = addonPath;
        pre.put("addonPath", addonPath);
    }

    public String getFirefoxPath() {
        return firefoxPath;
    }

    public void setFirefoxPath(String firefoxPath) {
        this.firefoxPath = firefoxPath;
        pre.put("firefoxPath", firefoxPath);
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        pre.put("description", description);
    }

    public List<String> getArrPrices() {
        return arrPrices;
    }

    public void setArrPrices(String prices) {
        this.arrPrices.clear();
        this.arrPrices = StringUtil.getListStringBySplit(prices, ",");
        pre.put("prices", prices);
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
        pre.put("userToken", userToken);
    }

    public int getDayLeft() {
        return dayLeft;
    }

    public void setDayLeft(int dayLeft) {
        this.dayLeft = dayLeft;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        pre.put("userId", userId);
    }

    public Boolean getToolReady() {
        return toolReady;
    }

    public void setToolReady(Boolean toolReady) {
        this.toolReady = toolReady;
        pre.putBoolean("toolReady", toolReady);
    }

    public int getPageLoadTimeout() {
        return pageLoadTimeout;
    }

    public void setPageLoadTimeout(int pageLoadTimeout) {
        this.pageLoadTimeout = pageLoadTimeout;
        pre.putInt("pageLoadTimeout", pageLoadTimeout);
    }

    public Preferences getPre() {
        return pre;
    }

    public void setPre(Preferences pre) {
        this.pre = pre;
    }

    public int getResetMode() {
        return resetMode;
    }

    public void setResetMode(int resetMode) {
        this.resetMode = resetMode;
        pre.putInt("resetMode", resetMode);
    }

    public String getEthernet3GName() {
        return ethernet3GName;
    }

    public void setEthernet3GName(String ethernet3GName) {
        this.ethernet3GName = ethernet3GName;
        pre.put("ethernet3GName", ethernet3GName);
    }

    public Boolean getDisableRtc() {
        return disableRtc;
    }

    public void setDisableRtc(Boolean disableRtc) {
        this.disableRtc = disableRtc;
        pre.putBoolean("disableRtc", disableRtc);
    }

    public Boolean getFakeUseragent() {
        return fakeUseragent;
    }

    public void setFakeUseragent(Boolean fakeUseragent) {
        this.fakeUseragent = fakeUseragent;
        pre.putBoolean("fakeUseragent", fakeUseragent);
    }

    public int getTimeOpenFirefox() {
        return timeOpenFirefox;
    }

    public void setTimeOpenFirefox(int timeOpenFirefox) {
        this.timeOpenFirefox = timeOpenFirefox;
        pre.putInt("timeOpenFirefox", timeOpenFirefox);
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
        pre.put("serviceUrl", serviceUrl);
    }

    public String getServicePass() {
        return servicePass;
    }

    public void setServicePass(String servicePass) {
        this.servicePass = servicePass;
        pre.put("servicePass", servicePass);
    }

    public String getLocationPlantMode() {
        return locationPlantMode;
    }

    public void setLocationPlantMode(String locationPlantMode) {
        this.locationPlantMode = locationPlantMode;
        pre.put("locationPlantMode", locationPlantMode);
    }

    public int getTimeReset() {
        return timeReset;
    }

    public void setTimeReset(int timeReset) {
        this.timeReset = timeReset;
        pre.putInt("timeReset", timeReset);
    }

    public String getEthernetName() {
        return ethernetName;
    }

    public void setEthernetName(String ethernetName) {
        this.ethernetName = ethernetName;
        pre.put("ethernetName", ethernetName);
    }

    public String getTinsoftKey() {
        return tinsoftKey;
    }

    public void setTinsoftKey(String tinsoftKey) {
        this.tinsoftKey = tinsoftKey;
        pre.put("tinsoftKey", tinsoftKey);
    }

    public String getResetModemScript() {
        return resetModemScript;
    }

    public void setResetModemScript(String resetModemScript) {
        this.resetModemScript = resetModemScript;
        pre.put("resetModemScript", resetModemScript);
    }

    public static ToolSetting instance;

    private String firstName;
    private String middleName;
    private String lastName;
    private String userAgentReg;
    private String keywordReg;
    private int timeWaitKeyword;
    private boolean changeUserAgent;
    private int timeDelayRegGmail;
    private String profileRegFolder;

    public String getProfileRegFolder() {
        return profileRegFolder;
    }

    public void setProfileRegFolder(String profileRegFolder) {
        this.profileRegFolder = profileRegFolder;
        pre.put("profileRegFolder", profileRegFolder);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        pre.put("firstName", firstName);
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
        pre.put("middleName", middleName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        pre.put("lastName", lastName);
    }

    public String getUserAgentReg() {
        return userAgentReg;
    }

    public void setUserAgentReg(String userAgentReg) {
        this.userAgentReg = userAgentReg;
        pre.put("userAgentReg", userAgentReg);
    }

    public String getKeywordReg() {
        return keywordReg;
    }

    public void setKeywordReg(String keywordReg) {
        this.keywordReg = keywordReg;
        pre.put("keywordReg", keywordReg);
    }

    public int getTimeWaitKeyword() {
        return timeWaitKeyword;
    }

    public void setTimeWaitKeyword(int timeWaitKeyword) {
        this.timeWaitKeyword = timeWaitKeyword;
        pre.putInt("timeWaitKeyword", timeWaitKeyword);
    }

    public boolean isChangeUserAgent() {
        return changeUserAgent;
    }

    public void setChangeUserAgent(boolean changeUserAgent) {
        this.changeUserAgent = changeUserAgent;
        pre.putBoolean("changeUserAgent", changeUserAgent);
    }

    public int getTimeDelayRegGmail() {
        return timeDelayRegGmail;
    }

    public void setTimeDelayRegGmail(int timeDelayRegGmail) {
        this.timeDelayRegGmail = timeDelayRegGmail;
        pre.putInt("timeDelayRegGmail", timeDelayRegGmail);
    }
    private String accountUploadFolder;

    public String getAccountUploadFolder() {
        return accountUploadFolder;
    }

    public void setAccountUploadFolder(String accountUploadFolder) {
        this.accountUploadFolder = accountUploadFolder;
        pre.put("accountUploadFolder", accountUploadFolder);
    }

    private ToolSetting() {
        pre = Preferences.userRoot().node("SSCAccountCreator");
        this.disableRtc = pre.getBoolean("disableRtc", false);
        this.isLinux = System.getProperty("os.name").contains("Linux") ? true : false;
        this.fakeUseragent = pre.getBoolean("fakeUseragent", false);
        this.timeOpenFirefox = pre.getInt("timeOpenFirefox", 120);
        if (new File("backend.txt").exists()) {
            this.serviceUrl = MyFileUtils.getStringFromFile(new File("backend.txt"));
        } else {
            this.serviceUrl = "http://backend.simplesolution.co";
        }
        this.servicePass = pre.get("servicePass", "");
        this.locationPlantMode = pre.get("locationPlantMode", "");
        this.ethernet3GName = pre.get("ethernet3GName", "");
        this.timeReset = pre.getInt("timeReset", 120);
        this.resetMode = pre.getInt("resetMode", 1);
        this.numberPasswd = pre.getInt("numberPasswd", 10);
        this.makeChannel = pre.getBoolean("makeChannel", false);
        this.ethernetName = pre.get("ethernetName", "");
        this.tinsoftKey = pre.get("tinsoftKey", "");
        this.resetModemScript = pre.get("resetModemScript", "");
        this.addonPath = pre.get("addonPath", "");
        this.pageLoadTimeout = pre.getInt("pageLoadTimeout", 120);
        this.timeViewVideo = pre.getInt("timeViewVideo", 30);
        this.setFakeUseragent(true);
        this.imageTMP = RESOURCE_TOOL + "\\tempImages";
        MyFileUtils.createFolder(imageTMP);
        this.loginBySelenium = pre.getBoolean("loginBySelenium", false);
        this.toolReady = pre.getBoolean("toolReady", false);
        this.recoverStructure = pre.get("recoverStructure", "");
        this.accountUploadFolder = pre.get("accountUploadFolder", "");
        this.phoneOperation = pre.getInt("phoneOperation", 0);
        this.timeDelayReg = pre.getInt("timeDelayReg", 3);
        this.timeWaitImage = pre.getInt("timeWaitImage", 30);
        this.userToken = pre.get("userToken", "");
        this.keyPhoneApi = pre.get("keyPhoneApi", "");
        this.tmKey = pre.get("tmKey", "");
        this.userId = pre.get("userId", "");
        this.toolCode = "3A89Q";
        this.arrPrices = new ArrayList<>();
        this.arrPrices.addAll(StringUtil.getListStringBySplit(pre.get("prices", ""), ","));
        this.description = pre.get("description", "");
        this.firefoxPath = pre.get("firefoxPath", "");
        this.profileRegFolder = pre.get("profileRegFolder", "");
        this.gmailRecoverReg = pre.get("gmailRecoverReg", "");
        firstName = pre.get("firstName", "");
        middleName = pre.get("middleName", "");
        lastName = pre.get("lastName", "");
        userAgentReg = pre.get("userAgentReg", "");
        keywordReg = pre.get("keywordReg", "");
        timeWaitKeyword = pre.getInt("timeWaitKeyword", 30);
        changeUserAgent = pre.getBoolean("changeUserAgent", false);
        timeDelayRegGmail = pre.getInt("timeDelayRegGmail", 30);
    }

    public static ToolSetting getInstance() {
        if (instance == null) {
            instance = new ToolSetting();
        }
        return instance;
    }

}
