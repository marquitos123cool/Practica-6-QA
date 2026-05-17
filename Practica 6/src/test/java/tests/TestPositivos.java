package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;

/**
 * Práctica 6 — Casos POSITIVOS (flujo exitoso)
 * TP-001 al TP-004
 *
 * Anotaciones usadas:
 *   @BeforeClass  → mensaje de inicio de clase
 *   @BeforeMethod → ya hereda setup() de BaseTest (abre Chrome + URL)
 *   @AfterMethod  → ya hereda tearDown() de BaseTest (cierra Chrome)
 *   @AfterClass   → mensaje de fin de clase
 *   @Test         → cada caso de prueba
 */
public class TestPositivos extends BaseTest {

    // ── Anotaciones de ciclo de vida ──────────────────────────────────────────

    @BeforeClass
    public void iniciarClase() {
        System.out.println("\n====== INICIO: Tests Positivos ======");
    }

    @AfterClass
    public void finalizarClase() {
        System.out.println("====== FIN: Tests Positivos ======\n");
    }

    // @BeforeMethod y @AfterMethod vienen de BaseTest
    // (abren y cierran Chrome automáticamente en cada test)

    // ── Helpers ───────────────────────────────────────────────────────────────

    private LoginPage getLoginPage() {
        LoginPage lp = new LoginPage(driver, wait);
        lp.waitForLoginPage();
        return lp;
    }

    private DashboardPage loginAndGetDashboard() {
        getLoginPage().login("Admin", "admin123");
        DashboardPage dp = new DashboardPage(driver, wait);
        dp.waitForDashboard();
        return dp;
    }

    // ── TP-001: Login exitoso redirige al dashboard ───────────────────────────
    @Test(priority = 0, description = "TP-001: Login con credenciales válidas")
    public void TP001_loginExitosoRedirigeDashboard() {
        getLoginPage().login("Admin", "admin123");

        // Assert URL contiene "dashboard"
        String url = driver.getCurrentUrl();
        Assert.assertTrue(url.contains("dashboard"),
            "TP-001 FAIL: URL esperada contiene 'dashboard', obtenida: " + url);

        System.out.println("TP-001 PASS | URL: " + url);
    }

    // ── TP-002: Título del dashboard es correcto ──────────────────────────────
    @Test(priority = 1, description = "TP-002: Dashboard muestra título 'Dashboard'")
    public void TP002_dashboardMuestraTituloCorrect() {
        DashboardPage dp = loginAndGetDashboard();

        String titulo = dp.getDashboardTitle();

        // Assert texto en pantalla
        Assert.assertEquals(titulo, "Dashboard",
            "TP-002 FAIL: Título esperado 'Dashboard', obtenido: " + titulo);

        System.out.println("TP-002 PASS | Título: " + titulo);
    }

    // ── TP-003: Logout exitoso regresa al login ───────────────────────────────
    @Test(priority = 2, description = "TP-003: Logout redirige a la pantalla de login")
    public void TP003_logoutExitosoRegresaLogin() {
        DashboardPage dp = loginAndGetDashboard();
        dp.logout();

        // Assert URL contiene "auth/login"
        String url = driver.getCurrentUrl();
        Assert.assertTrue(url.contains("auth/login"),
            "TP-003 FAIL: URL esperada contiene 'auth/login', obtenida: " + url);

        System.out.println("TP-003 PASS | URL tras logout: " + url);
    }

    // ── TP-004: Navegar a Admin y volver al Dashboard ─────────────────────────
    @Test(priority = 3, description = "TP-004: Navegar a Admin y regresar al Dashboard")
    public void TP004_navegarAdminYRegresarDashboard() {
        DashboardPage dp = loginAndGetDashboard();
        dp.goToAdmin();

        // Assert que llegó a Admin
        Assert.assertTrue(driver.getCurrentUrl().contains("admin"),
            "TP-004 FAIL: No navegó al módulo Admin");

        dp.goToDashboard();

        // Assert que regresó al Dashboard
        String url = driver.getCurrentUrl();
        Assert.assertTrue(url.contains("dashboard"),
            "TP-004 FAIL: No regresó al Dashboard, URL: " + url);

        System.out.println("TP-004 PASS | Navegación Admin → Dashboard correcta");
    }
}
