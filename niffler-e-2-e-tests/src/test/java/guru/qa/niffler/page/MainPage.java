package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainPage {
    private final ElementsCollection tableRows = $$("#spendings tbody tr");
    private final SelenideElement statistics = $("#stat");
    private final SelenideElement spendingHistory = $("#spendings");
    private final SelenideElement errorText = $(".form__error");
    private final SelenideElement newSpendingLink = $(By.xpath("//a[@href = '/spending']"));


    public EditSpendingPage editSpending(String spendingDescription) {
        tableRows.find(text(spendingDescription)).$$("td")
                .get(5)
                .click();
        return new EditSpendingPage();
    }

    public EditSpendingPage addNewSpending(){
        newSpendingLink.click();
        return new EditSpendingPage();
    }

    public void checkThatTableContains(String spendingDescription) {
        tableRows.find(text(spendingDescription)).should(visible);
    }

    public void checkThatMainPageOpened() {
        statistics.should(visible);
        spendingHistory.should(visible);
    }

    public void checkThatUserStaysOnLoginPage() {
        errorText.should(visible);
        assertEquals("Неверные учетные данные пользователя", errorText.getText());
    }
}
