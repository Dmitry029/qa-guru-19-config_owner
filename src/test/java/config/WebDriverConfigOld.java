package config;

public class WebDriverConfigOld {

    public String getBaseUrl() {
        //Configuration.baseUrl = System.getProperty("baseUrl");
        return System.getProperty("baseUrl", "https://demoqa.com");
    }

    public Browser getBrowser() {
        String browser = System.getProperty("browser", "CHROME");
        return Browser.valueOf(browser);
    }

    public Integer getBrowserVersion() {
        return 5;
    }
}
