package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:${env}.properties",
        "classpath:testing_local.properties"
})
public interface TestConfig extends Config {

    @Key("baseUrl")
    String getBaseUrl();
    @Key("windowSize")
    String getWindowSize();
    @Key("browser")
    String getBrowser();
    @Key("browserVersion")
    String getBrowserVersion();
    @Key("remoteUrl")
    String getRemoteUrl();
}
