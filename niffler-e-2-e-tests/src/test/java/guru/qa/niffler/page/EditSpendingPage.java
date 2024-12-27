package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EditSpendingPage {

    private final SelenideElement descriptionInput = $("#description");
    private final SelenideElement submitBtn = $("#save");
    private final ElementsCollection categoryList = $$(By.xpath("//li//span"));

    public void editSpendingDescription(String description) {
        descriptionInput.clear();
        descriptionInput.setValue(description);
        submitBtn.click();
    }

    public void shouldSeeCategoryInCategoryList(String category, boolean archived) {
        if (archived){
            assertFalse(categoryList.stream().anyMatch(e -> e.text().equals(category)));
        } else {
            assertTrue(categoryList.stream().anyMatch(e -> e.text().equals(category)));
        }

    }
}
