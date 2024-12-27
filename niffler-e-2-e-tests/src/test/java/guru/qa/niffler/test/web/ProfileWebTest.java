package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.BrowserExtension;
import guru.qa.niffler.jupiter.category.Category;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.page.LoginPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(BrowserExtension.class)
public class ProfileWebTest {
    private static final Config CFG = Config.getInstance();

    @Category(
            username = "lisa_kolbasa",
            archived = true
    )
    @Test
    void archivedCategoryShouldNotPresentInCategoryList(CategoryJson category) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .doLogin("lisa_kolbasa", "12345")
                .addNewSpending()
                .shouldSeeCategoryInCategoryList(category.name(), category.archived());
    }

    @Category(
            username = "lisa_kolbasa",
            archived = false
    )
    @Test
    void activeCategoryShouldPresentInCategoryList(CategoryJson category) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .doLogin("lisa_kolbasa", "12345")
                .addNewSpending()
                .shouldSeeCategoryInCategoryList(category.name(), category.archived());
    }

}
