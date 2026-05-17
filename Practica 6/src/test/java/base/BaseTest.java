package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.ByteArrayInputStream;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    // URL base del sistema a probar
    public static final String BASE_URL =
        "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";

    @BeforeMethod
    public void setup() {
        // WebDriverManager descarga ChromeDriver automáticamente
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Espera implícita global (3 segundos)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        // WebDriverWait explícita (10 segundos) — requerida por la práctica
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get(BASE_URL);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        // Captura screenshot si el test falló
        if (ITestResult.FAILURE == result.getStatus()) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Screenshot al fallar", new ByteArrayInputStream(screenshot));
        }
        driver.quit();
    }
}
