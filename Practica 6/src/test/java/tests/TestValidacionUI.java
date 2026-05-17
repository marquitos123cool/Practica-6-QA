package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;

/**
 * Práctica 6 — Casos de VALIDACIÓN DE INTERFAZ (elementos visibles)
 * TUI-001 al TUI-004
 */
public class TestValidacionUI extends BaseTest {

    @BeforeClass
    public void iniciarClase() {
        System.out.println("\n====== INICIO: Tests Validación UI ======");
    }

    @AfterClass
    public void finalizarClase() {
        System.out.println("====== FIN: Tests Validación UI ======\n");
    }

    // ── TUI-001: Página de login tiene todos sus elementos visibles ───────────
    @Test(priority = 0, description = "TUI-001: Elementos del login son visibles")
    public void TUI001_elementosLoginVisibles() {
        LoginPage lp = new LoginPage(driver, wait);
        lp.waitForLoginPage();

        // Assert cada elemento por separado
        Assert.assertTrue(lp.isUsernameVisible(),
            "TUI-001 FAIL: Campo 'Username' no está visible");
        Assert.assertTrue(lp.isPasswordVisible(),
            "TUI-001 FAIL: Campo 'Password' no está visible");
        Assert.assertTrue(lp.isLoginButtonVisible(),
            "TUI-001 FAIL: Botón 'Login' no está visible");

        System.out.println("TUI-001 PASS | Username, Password y botón Login visibles");
    }

    // ── TUI-002: Título de la página de login es correcto ─────────────────────
    @Test(priority = 1, description = "TUI-002: Título del login muestra texto correcto")
    public void TUI002_tituloLoginCorrecto() {
        LoginPage lp = new LoginPage(driver, wait);
        lp.waitForLoginPage();

        String titulo = lp.getLoginTitle();

        // Assert texto en pantalla (assertEquals)
        Assert.assertEquals(titulo, "Login",
            "TUI-002 FAIL: Título esperado 'Login', obtenido: '" + titulo + "'");

        System.out.println("TUI-002 PASS | Título login: " + titulo);
    }

    // ── TUI-003: Menú lateral del dashboard tiene los módulos esperados ────────
    @Test(priority = 2, description = "TUI-003: Menú lateral contiene los módulos principales")
    public void TUI003_menuLateralContieneModulos() {
        LoginPage lp = new LoginPage(driver, wait);
        lp.waitForLoginPage();
        lp.login("Admin", "admin123");

        DashboardPage dp = new DashboardPage(driver, wait);
        dp.waitForDashboard();

        // Verificar que los links del menú existen en el DOM
        By menuAdmin       = By.xpath("//span[text()='Admin']");
        By menuPim         = By.xpath("//span[text()='PIM']");
        By menuRecruitment = By.xpath("//span[text()='Recruitment']");
        By menuDashboard   = By.xpath("//span[text()='Dashboard']");

        // Assert visibilidad de cada elemento del menú
        Assert.assertTrue(
            wait.until(ExpectedConditions.visibilityOfElementLocated(menuAdmin)).isDisplayed(),
            "TUI-003 FAIL: Menú 'Admin' no visible");
        Assert.assertTrue(
            wait.until(ExpectedConditions.visibilityOfElementLocated(menuPim)).isDisplayed(),
            "TUI-003 FAIL: Menú 'PIM' no visible");
        Assert.assertTrue(
            wait.until(ExpectedConditions.visibilityOfElementLocated(menuRecruitment)).isDisplayed(),
            "TUI-003 FAIL: Menú 'Recruitment' no visible");
        Assert.assertTrue(
            wait.until(ExpectedConditions.visibilityOfElementLocated(menuDashboard)).isDisplayed(),
            "TUI-003 FAIL: Menú 'Dashboard' no visible");

        System.out.println("TUI-003 PASS | Todos los módulos del menú son visibles");
    }

    // ── TUI-004: Dropdown de usuario en el header es visible ──────────────────
    @Test(priority = 3, description = "TUI-004: Dropdown de usuario visible en el header")
    public void TUI004_dropdownUsuarioVisibleEnHeader() {
        LoginPage lp = new LoginPage(driver, wait);
        lp.waitForLoginPage();
        lp.login("Admin", "admin123");

        DashboardPage dp = new DashboardPage(driver, wait);
        dp.waitForDashboard();

        // Verificar que el nombre de usuario aparece en el header
        By userDropdown  = By.cssSelector(".oxd-userdropdown");
        By userNameLabel = By.cssSelector(".oxd-userdropdown-name");

        WebElement dropdown = wait.until(
            ExpectedConditions.visibilityOfElementLocated(userDropdown));
        Assert.assertTrue(dropdown.isDisplayed(),
            "TUI-004 FAIL: Dropdown de usuario no visible en el header");

        WebElement nombre = wait.until(
            ExpectedConditions.visibilityOfElementLocated(userNameLabel));
        String nombreTexto = nombre.getText();
        Assert.assertFalse(nombreTexto.isEmpty(),
            "TUI-004 FAIL: El nombre de usuario en el header está vacío");

        System.out.println("TUI-004 PASS | Usuario en header: " + nombreTexto);
    }
}
