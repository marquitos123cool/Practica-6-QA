package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PimPage {
    WebDriver driver;
    WebDriverWait wait;

    // ── Locators ──────────────────────────────────────────────────────────────
    By lblPimTitle    = By.cssSelector(".oxd-topbar-header-breadcrumb h6");
    By txtFirstName   = By.name("firstName");
    By txtMiddleName  = By.name("middleName");
    By txtLastName    = By.name("lastName");
    By btnAddEmployee = By.cssSelector(".orangehrm-header-container .oxd-button--secondary");
    By btnSave        = By.cssSelector("[type='submit']");
    By txtSearch      = By.cssSelector(".oxd-input--active");
    By btnSearchEmp   = By.cssSelector("[type='submit']");
    By tableRows      = By.cssSelector(".oxd-table-body .oxd-table-row");
    By employeeId     = By.cssSelector(".oxd-input--focus");

    public PimPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait   = wait;
    }

    // ── Esperar carga del módulo PIM ──────────────────────────────────────────
    public void waitForPimPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(lblPimTitle));
        wait.until(ExpectedConditions.urlContains("pim"));
    }

    // ── Acciones ──────────────────────────────────────────────────────────────
    public void clickAddEmployee() {
        wait.until(ExpectedConditions.elementToBeClickable(btnAddEmployee)).click();
    }

    public void enterFirstName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtFirstName))
            .sendKeys(name);
    }

    public void enterLastName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtLastName))
            .sendKeys(name);
    }

    public void clickSave() {
        wait.until(ExpectedConditions.elementToBeClickable(btnSave)).click();
    }

    // ── Getters para validaciones ─────────────────────────────────────────────
    public String getPageTitle() {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(lblPimTitle)).getText();
    }

    public boolean isAddEmployeeButtonVisible() {
        try {
            return wait.until(
                ExpectedConditions.visibilityOfElementLocated(btnAddEmployee)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int getEmployeeCount() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows));
            return driver.findElements(tableRows).size();
        } catch (Exception e) {
            return 0;
        }
    }
}
