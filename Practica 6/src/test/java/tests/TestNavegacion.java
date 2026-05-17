package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PimPage;
import pages.RecruitmentPage;

/**
 * Práctica 6 — Casos de NAVEGACIÓN dentro del sistema
 * TNav-001 al TNav-004
 */
public class TestNavegacion extends BaseTest {

    @BeforeClass
    public void iniciarClase() {
        System.out.println("\n====== INICIO: Tests Navegación ======");
    }

    @AfterClass
    public void finalizarClase() {
        System.out.println("====== FIN: Tests Navegación ======\n");
    }

    // Helper: login y obtener dashboard
    private DashboardPage loginYDashboard() {
        LoginPage lp = new LoginPage(driver, wait);
        lp.waitForLoginPage();
        lp.login("Admin", "admin123");
        DashboardPage dp = new DashboardPage(driver, wait);
        dp.waitForDashboard();
        return dp;
    }

    // ── TNav-001: Navegar al módulo PIM ──────────────────────────────────────
    @Test(priority = 0, description = "TNav-001: Navegar al módulo PIM correctamente")
    public void TNav001_navegarAPim() {
        DashboardPage dp = loginYDashboard();
        dp.goToPim();

        PimPage pim = new PimPage(driver, wait);
        pim.waitForPimPage();

        // Assert URL
        String url = driver.getCurrentUrl();
        Assert.assertTrue(url.contains("pim"),
            "TNav-001 FAIL: URL no contiene 'pim', obtenida: " + url);

        // Assert título del módulo
        String titulo = pim.getPageTitle();
        Assert.assertFalse(titulo.isEmpty(),
            "TNav-001 FAIL: Título del módulo PIM está vacío");

        System.out.println("TNav-001 PASS | PIM URL: " + url + " | Título: " + titulo);
    }

    // ── TNav-002: Navegar al módulo Recruitment ───────────────────────────────
    @Test(priority = 1, description = "TNav-002: Navegar al módulo Recruitment correctamente")
    public void TNav002_navegarARecruitment() {
        DashboardPage dp = loginYDashboard();
        dp.goToRecruitment();

        RecruitmentPage rec = new RecruitmentPage(driver, wait);
        rec.waitForRecruitmentPage();

        // Assert URL
        String url = driver.getCurrentUrl();
        Assert.assertTrue(url.contains("recruitment"),
            "TNav-002 FAIL: URL no contiene 'recruitment', obtenida: " + url);

        // Assert título
        String titulo = rec.getPageTitle();
        Assert.assertFalse(titulo.isEmpty(),
            "TNav-002 FAIL: Título del módulo Recruitment está vacío");

        System.out.println("TNav-002 PASS | Recruitment URL: " + url);
    }

    // ── TNav-003: Navegar de Admin de regreso al Dashboard ────────────────────
    @Test(priority = 2, description = "TNav-003: Regresar de Admin al Dashboard")
    public void TNav003_regresarDeAdminADashboard() {
        DashboardPage dp = loginYDashboard();

        // Ir a Admin
        dp.goToAdmin();
        Assert.assertTrue(driver.getCurrentUrl().contains("admin"),
                "TNav-003 FAIL: No llegó a Admin");

        // Regresar al Dashboard
        dp.goToDashboard();

        // esperar a que el sidebar cargue completamente
        dp.waitForDashboard();

        // Assert regresó al dashboard
        String url = driver.getCurrentUrl();
        Assert.assertTrue(url.contains("dashboard"),
                "TNav-003 FAIL: No regresó al Dashboard, URL: " + url);

        // Assert que el dashboard sigue visible
        Assert.assertTrue(dp.isDashboardVisible(),
                "TNav-003 FAIL: El Dashboard no está visible después de regresar");

        System.out.println("TNav-003 PASS | Regreso de Admin a Dashboard correcto");
    }

    // ── TNav-004: Navegar a través de múltiples módulos en secuencia ──────────
    @Test(priority = 3, description = "TNav-004: Navegación secuencial entre módulos")
    public void TNav004_navegacionSecuencialEntreModulos() {
        DashboardPage dp = loginYDashboard();

        // Dashboard → Admin
        dp.goToAdmin();
        Assert.assertTrue(driver.getCurrentUrl().contains("admin"),
            "TNav-004 FAIL: No llegó a Admin");

        // Admin → PIM
        dp.goToPim();
        String urlPim = driver.getCurrentUrl();
        Assert.assertTrue(urlPim.contains("pim"),
            "TNav-004 FAIL: No llegó a PIM, URL: " + urlPim);

        // PIM → Recruitment
        dp.goToRecruitment();
        String urlRec = driver.getCurrentUrl();
        Assert.assertTrue(urlRec.contains("recruitment"),
            "TNav-004 FAIL: No llegó a Recruitment, URL: " + urlRec);

        // Recruitment → Dashboard
        dp.goToDashboard();
        String urlFinal = driver.getCurrentUrl();
        Assert.assertTrue(urlFinal.contains("dashboard"),
            "TNav-004 FAIL: No regresó al Dashboard, URL: " + urlFinal);

        System.out.println("TNav-004 PASS | Secuencia completa: Dashboard→Admin→PIM→Recruitment→Dashboard");
    }
}
