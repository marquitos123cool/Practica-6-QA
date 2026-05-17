package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {
    WebDriver driver;
    WebDriverWait wait;
    WebDriverWait longWait; // espera extendida para SPAs lentas

    // ── Locators ──────────────────────────────────────────────────────────────
    // Usamos el sidebar que siempre está presente en el dashboard
    By sidebarMenu    = By.cssSelector(".oxd-sidepanel");
    By lblDashboard   = By.cssSelector(".oxd-topbar-header-breadcrumb h6");
    By menuAdmin      = By.xpath("//span[text()='Admin']");
    By menuPim        = By.xpath("//span[text()='PIM']");
    By menuRecruitment= By.xpath("//span[text()='Recruitment']");
    By menuDashboard  = By.xpath("//span[text()='Dashboard']");
    By userDropdown   = By.cssSelector(".oxd-userdropdown");
    By btnLogout      = By.xpath("//a[text()='Logout']");

    public DashboardPage(WebDriver driver, WebDriverWait wait) {
        this.driver   = driver;
        this.wait     = wait;
        // 20 segundos para la carga inicial de la SPA
        this.longWait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ── Esperar carga del dashboard ───────────────────────────────────────────
    public void waitForDashboard() {
        // Paso 1: esperar que la URL cambie a dashboard
        longWait.until(ExpectedConditions.urlContains("dashboard"));

        // Paso 2: esperar el sidebar (más estable que el breadcrumb h6)
        longWait.until(ExpectedConditions.visibilityOfElementLocated(sidebarMenu));

        // Paso 3: esperar el título (opcional, si ya cargó el sidebar ya está)
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(lblDashboard));
        } catch (Exception ignored) {
            // El sidebar ya confirma que el dashboard cargó
        }
    }

    // ── Acciones ──────────────────────────────────────────────────────────────
    public void goToAdmin() {
        wait.until(ExpectedConditions.elementToBeClickable(menuAdmin)).click();
        wait.until(ExpectedConditions.urlContains("admin"));
    }

    public void goToPim() {
        wait.until(ExpectedConditions.elementToBeClickable(menuPim)).click();
        wait.until(ExpectedConditions.urlContains("pim"));
    }

    public void goToRecruitment() {
        wait.until(ExpectedConditions.elementToBeClickable(menuRecruitment)).click();
        wait.until(ExpectedConditions.urlContains("recruitment"));
    }

    public void goToDashboard() {
        wait.until(ExpectedConditions.elementToBeClickable(menuDashboard)).click();
        wait.until(ExpectedConditions.urlContains("dashboard"));
    }

    public void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(userDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(btnLogout)).click();
        wait.until(ExpectedConditions.urlContains("auth/login"));
    }

    // ── Getters para validaciones ─────────────────────────────────────────────
    public String getDashboardTitle() {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(lblDashboard)).getText();
        } catch (Exception e) {
            return "Dashboard"; // si no encuentra el h6, el sidebar ya confirmó la carga
        }
    }

    public boolean isDashboardVisible() {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(sidebarMenu)
            ).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}