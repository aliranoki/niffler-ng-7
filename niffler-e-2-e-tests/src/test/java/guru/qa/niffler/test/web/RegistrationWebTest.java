package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.BrowserExtension;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.RegisterPage;
import guru.qa.niffler.util.DataHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(BrowserExtension.class)
public class RegistrationWebTest {

    private static final Config CFG = Config.getInstance();

    @Test
    void shouldRegisterNewUser() {
        UserJson randomUser = DataHelper.getRandomUser();
        String username = randomUser.username();
        String password = randomUser.password();

        doUserRegistration(username, password, password)
                .checkThatUserWasRegistered();

    }

    @Test
    void shouldNotRegisterUserWithExistingUsername() {
        UserJson randomUser = DataHelper.getRandomUser();
        String username = randomUser.username();
        String password = randomUser.password();

        doUserRegistration(username, password, password);

        doUserRegistration(username, password, password)
                .checkThatUserWithTheSameNameWasNotRegistered(username);
    }

    @Test
    void shouldNotShowErrorIfPasswordAndConfirmPasswordAreNotEquals() {
        UserJson randomUser = DataHelper.getRandomUser();
        String username = randomUser.username();
        String password = randomUser.password();
        String passwordConfirm = DataHelper.getRandomUser().password();

        doUserRegistration(username, password, passwordConfirm)
                .checkThatPasswordsShouldBeEqual();


    }


    @Test
    void mainPageShouldBeDisplayedAfterSuccessLogin() {
        UserJson randomUser = DataHelper.getRandomUser();
        String username = randomUser.username();
        String password = randomUser.password();

        doUserRegistration(username, password, password)
                .signIn()
                .doLogin(username, password)
                .checkThatMainPageOpened();

    }

    @Test
    void userShouldStayOnLoginPageAfterLoginWithBadCredentials() {
        UserJson randomUser = DataHelper.getRandomUser();
        String username = randomUser.username();
        String password = randomUser.password();

        doUserRegistration(username, password, password)
                .signIn()
                .doLogin(username, DataHelper.getRandomUser().password())
                .checkThatUserStaysOnLoginPage();

    }


    private static RegisterPage doUserRegistration(String username, String password, String passwordConfirm) {
        return Selenide.open(CFG.frontUrl(), LoginPage.class)
                .openRegisterPage()
                .setUsername(username)
                .setPassword(password)
                .setPasswordSubmit(passwordConfirm)
                .submitRegistration();
    }
}
