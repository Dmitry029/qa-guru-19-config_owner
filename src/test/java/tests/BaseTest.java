package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.AuthConfig;
import config.TestConfig;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import lombok.Data;
import models.AuthModel;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;

@Data
public class BaseTest {
    private String userName = getAuthModel().getUserName();
    private String password = getAuthModel().getPassword();

    private AuthModel getAuthModel() {
        AuthConfig config = ConfigFactory.create(AuthConfig.class, System.getProperties());
        return new AuthModel(config.userName(), config.password());
    }

    @BeforeAll
    static void setup() {
        System.getProperty("env", "testing_local");

        TestConfig config = ConfigFactory.create(TestConfig.class, System.getProperties());

        RestAssured.baseURI = config.getBaseUrl();
        Configuration.baseUrl = config.getBaseUrl();
        Configuration.browserSize = config.getWindowSize();
        Configuration.browser = config.getBrowser();
        Configuration.browserVersion = config.getBrowserVersion();
        Configuration.remote = config.getRemoteUrl();
        Configuration.pageLoadStrategy = "eager";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;
    }

    @BeforeEach
    void setUpBeforeEach() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        closeWebDriver();
    }
}


