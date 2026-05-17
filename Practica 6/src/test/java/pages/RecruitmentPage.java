package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class RecruitmentPage {
    WebDriver driver;
    WebDriverWait wait;

    // ── Locators ──────────────────────────────────────────────────────────────
    By lblTitle       = By.cssSelector(".oxd-topbar-header-breadcrumb h6");
    By btnAddVacancy  = By.cssSelector(".orangehrm-header-container .oxd-button--secondary");
    By dropVacancy    = By.xpath("(//div[@class='oxd-select-text-input'])[1]");
    By dropOptions    = By.cssSelector(".oxd-select-dropdown .oxd-select-option");
    By btnSearch      = By.cssSelector("[type='submit']");
    By tableRows      = By.cssSelector(".oxd-table-body .oxd-table-row");

    public RecruitmentPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait   = wait;
    }

    // ── Esperar carga del módulo Recruitment ──────────────────────────────────
    public void waitForRecruitmentPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(lblTitle));
        wait.until(ExpectedConditions.urlContains("recruitment"));
    }

    // ── Seleccionar vacante en dropdown ───────────────────────────────────────
    public void selectVacancy(String vacancyName) {
        // Interacción con dropdown — requerida por la práctica
        wait.until(ExpectedConditions.elementToBeClickable(dropVacancy)).click();
        List<WebElement> options = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(dropOptions));
        for (WebElement option : options) {
            if (option.getText().trim().equals(vacancyName)) {
                option.click();
                return;
            }
        }
    }

    // ── Acciones ──────────────────────────────────────────────────────────────
    public void clickAddVacancy() {
        wait.until(ExpectedConditions.elementToBeClickable(btnAddVacancy)).click();
    }

    public void clickSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(btnSearch)).click();
    }

    // ── Getters para validaciones ─────────────────────────────────────────────
    public String getPageTitle() {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(lblTitle)).getText();
    }

    public boolean isAddVacancyButtonVisible() {
        try {
            return wait.until(
                ExpectedConditions.visibilityOfElementLocated(btnAddVacancy)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int getResultCount() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows));
            return driver.findElements(tableRows).size();
        } catch (Exception e) {
            return 0;
        }
    }
}
