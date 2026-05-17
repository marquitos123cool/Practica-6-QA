package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class AdminPage {
    WebDriver driver;
    WebDriverWait wait;

    // ── Locators corregidos para OrangeHRM (dropdowns Vue.js, no <select>) ───
    By lblAdminTitle = By.cssSelector(".oxd-topbar-header-breadcrumb h6");
    By btnSearch     = By.cssSelector("button[type='submit']");
    By btnAddUser    = By.xpath("//button[normalize-space()='Add']");
    By tableRows     = By.cssSelector(".oxd-table-body .oxd-table-row");

    // Dropdown "User Role" — primer oxd-select-wrapper de la página
    By dropUserRoleBox     = By.xpath(
        "(//div[contains(@class,'oxd-select-wrapper')])[1]");
    // Dropdown "Status" — segundo oxd-select-wrapper
    By dropStatusBox       = By.xpath(
        "(//div[contains(@class,'oxd-select-wrapper')])[2]");
    // Opciones del dropdown abierto
    By dropOptionsList     = By.xpath(
        "//div[@role='listbox']//span");

    public AdminPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait   = wait;
    }

    // ── Esperar carga del módulo Admin ────────────────────────────────────────
    public void waitForAdminPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(lblAdminTitle));
        wait.until(ExpectedConditions.urlContains("admin"));
    }

    // ── Seleccionar opción en dropdown "User Role" ────────────────────────────
    public void selectUserRole(String role) {
        // 1. Abrir el dropdown con WebDriverWait (obligatorio según práctica)
        WebElement dropdown = wait.until(
            ExpectedConditions.elementToBeClickable(dropUserRoleBox));
        dropdown.click();

        // 2. Esperar que aparezcan las opciones
        List<WebElement> options = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(dropOptionsList));

        // 3. Hacer click en la opción correcta
        for (WebElement option : options) {
            if (option.getText().trim().equalsIgnoreCase(role)) {
                option.click();
                return;
            }
        }
        throw new RuntimeException("Opción '" + role + "' no encontrada en dropdown User Role");
    }

    // ── Seleccionar opción en dropdown "Status" ───────────────────────────────
    public void selectStatus(String status) {
        WebElement dropdown = wait.until(
            ExpectedConditions.elementToBeClickable(dropStatusBox));
        dropdown.click();

        List<WebElement> options = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(dropOptionsList));

        for (WebElement option : options) {
            if (option.getText().trim().equalsIgnoreCase(status)) {
                option.click();
                return;
            }
        }
        throw new RuntimeException("Opción '" + status + "' no encontrada en dropdown Status");
    }

    // ── Acciones ──────────────────────────────────────────────────────────────
    public void clickSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(btnSearch)).click();
    }

    public void clickAddUser() {
        wait.until(ExpectedConditions.elementToBeClickable(btnAddUser)).click();
    }

    // ── Getters para validaciones ─────────────────────────────────────────────
    public String getPageTitle() {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(lblAdminTitle)).getText();
    }

    public int getTableRowCount() {
        try {
            // Esperar a que aparezca al menos una fila
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows));
            return driver.findElements(tableRows).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean isAddUserButtonVisible() {
        try {
            return wait.until(
                ExpectedConditions.visibilityOfElementLocated(btnAddUser)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
