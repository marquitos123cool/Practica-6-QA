package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;
    WebDriverWait longWait; // ✅ NUEVO: espera extendida para carga inicial

    // ── Locators ──────────────────────────────────────────────────────────────
    By txtUsername  = By.name("username");
    By txtPassword  = By.name("password");
    By btnLogin     = By.cssSelector("[type='submit']");
    By lblError     = By.cssSelector(".oxd-alert-content-text");        // credenciales incorrectas
    By lblFieldError= By.cssSelector(".oxd-input-field-error-message"); // ✅ NUEVO: campo vacío / requerido
    By lblTitle     = By.cssSelector(".orangehrm-login-title");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver    = driver;
        this.wait      = wait;
        this.longWait  = new WebDriverWait(driver, Duration.ofSeconds(25)); // ✅ NUEVO
    }

    // ── Esperar a que cargue el login ─────────────────────────────────────────
    public void waitForLoginPage() {
        // ✅ CORRECCIÓN: usar longWait para la carga inicial del sitio demo
        longWait.until(ExpectedConditions.visibilityOfElementLocated(txtUsername));
    }

    // ── Acciones ──────────────────────────────────────────────────────────────
    public void enterUsername(String username) {
        WebElement field = wait.until(
                ExpectedConditions.elementToBeClickable(txtUsername));
        field.clear();
        field.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement field = wait.until(
                ExpectedConditions.elementToBeClickable(txtPassword));
        field.clear();
        field.sendKeys(password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(btnLogin)).click();
    }

    public void login(String username, String password) {
        waitForLoginPage();
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    // ── Getters para validaciones ─────────────────────────────────────────────

    // Para errores de credenciales incorrectas (alerta global roja)
    public String getErrorMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(lblError)).getText();
    }

    // ✅ NUEVO: Para errores de campo requerido (validación inline "Required")
    public String getFieldError() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(lblFieldError)).getText();
    }

    public String getLoginTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(lblTitle)).getText();
    }

    public boolean isUsernameVisible() {
        return driver.findElement(txtUsername).isDisplayed();
    }

    public boolean isPasswordVisible() {
        return driver.findElement(txtPassword).isDisplayed();
    }

    public boolean isLoginButtonVisible() {
        return driver.findElement(btnLogin).isDisplayed();
    }
}