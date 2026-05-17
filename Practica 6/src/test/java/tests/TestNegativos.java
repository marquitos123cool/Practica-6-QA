package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.LoginPage;

/**
 * Práctica 6 — Casos NEGATIVOS (errores y validaciones de fallo)
 * TN-001 al TN-004
 */
public class TestNegativos extends BaseTest {

    @BeforeClass
    public void iniciarClase() {
        System.out.println("\n====== INICIO: Tests Negativos ======");
    }

    @AfterClass
    public void finalizarClase() {
        System.out.println("====== FIN: Tests Negativos ======\n");
    }

    // ── TN-001: Login con contraseña incorrecta muestra error ─────────────────
    @Test(priority = 0, description = "TN-001: Contraseña incorrecta muestra mensaje de error")
    public void TN001_passwordIncorrectoMuestraError() {
        LoginPage lp = new LoginPage(driver, wait);
        lp.waitForLoginPage();
        lp.login("Admin", "passwordMalo123");

        // Assert que el mensaje de error aparece
        String error = lp.getErrorMessage();
        Assert.assertFalse(error.isEmpty(),
                "TN-001 FAIL: Se esperaba mensaje de error pero no apareció");

        // Assert que NO redirigió al dashboard
        Assert.assertFalse(driver.getCurrentUrl().contains("dashboard"),
                "TN-001 FAIL: No debió redirigir al dashboard con contraseña mala");

        System.out.println("TN-001 PASS | Error mostrado: " + error);
    }

    // ── TN-002: Login con usuario inexistente muestra error ───────────────────
    @Test(priority = 1, description = "TN-002: Usuario inexistente muestra mensaje de error")
    public void TN002_usuarioInexistenteMuestraError() {
        LoginPage lp = new LoginPage(driver, wait);
        lp.waitForLoginPage();
        lp.login("usuarioQueNoExiste999", "admin123");

        // Assert mensaje de error visible
        String error = lp.getErrorMessage();
        Assert.assertTrue(error.contains("Invalid credentials"),
                "TN-002 FAIL: Mensaje esperado 'Invalid credentials', obtenido: " + error);

        System.out.println("TN-002 PASS | Error: " + error);
    }

    // ── TN-003: Login con campos vacíos muestra error ─────────────────────────
    @Test(priority = 2, description = "TN-003: Campos vacíos muestran validación de requerido")
    public void TN003_camposVaciosMuestranValidacion() {
        LoginPage lp = new LoginPage(driver, wait);
        lp.waitForLoginPage();

        // Click en Login sin llenar nada
        lp.clickLogin();

        // Assert que sigue en la página de login (no redirigió)
        String url = driver.getCurrentUrl();
        Assert.assertTrue(url.contains("auth/login"),
                "TN-003 FAIL: No debió salir del login con campos vacíos, URL: " + url);

        // campos vacíos generan validación inline, no alerta global
        String error = lp.getFieldError();
        Assert.assertFalse(error.isEmpty(),
                "TN-003 FAIL: Se esperaba mensaje de validación con campos vacíos");

        System.out.println("TN-003 PASS | Validación mostrada: " + error);
    }

    // ── TN-004: Login con solo usuario (sin contraseña) muestra error ─────────
    @Test(priority = 3, description = "TN-004: Solo usuario sin contraseña muestra error")
    public void TN004_soloUsuarioSinPasswordMuestraError() {
        LoginPage lp = new LoginPage(driver, wait);
        lp.waitForLoginPage();

        // Llenar solo el usuario
        lp.enterUsername("Admin");
        lp.clickLogin();

        // Assert que NO entró al dashboard
        Assert.assertFalse(driver.getCurrentUrl().contains("dashboard"),
                "TN-004 FAIL: No debió entrar al dashboard sin contraseña");

        // contraseña vacía genera validación inline, no alerta global
        String error = lp.getFieldError();
        Assert.assertFalse(error.isEmpty(),
                "TN-004 FAIL: Se esperaba mensaje de error con contraseña vacía");

        System.out.println("TN-004 PASS | Error con contraseña vacía: " + error);
    }

}