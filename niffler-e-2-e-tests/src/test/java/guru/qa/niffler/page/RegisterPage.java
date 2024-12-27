package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterPage {
    private final SelenideElement usernameInput = $("#username");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement passwordSubmitInput = $("#passwordSubmit");
    private final SelenideElement submitBtn = $("button[type='submit']");
    private final SelenideElement successText = $(".form__paragraph_success");
    private final SelenideElement errorText = $(".form__error");
    private final SelenideElement signInBtn = $(".form_sign-in");


    public RegisterPage setUsername(String username) {
        usernameInput.setValue(username);
        return this;
    }

    public RegisterPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    public RegisterPage setPasswordSubmit(String passwordSubmit) {
        passwordSubmitInput.setValue(passwordSubmit);
        return this;
    }

    public RegisterPage submitRegistration() {
        submitBtn.click();
        return this;
    }

    public void checkThatUserWasRegistered() {
        successText.should(visible);
        assertEquals("Congratulations! You've registered!", successText.getText());
    }

    public void checkThatUserWithTheSameNameWasNotRegistered(String username) {
        errorText.should(visible);
        assertEquals("Username `"+username+"` already exists", errorText.getText());
    }

    public void checkThatPasswordsShouldBeEqual() {
        errorText.should(visible);
        assertEquals("Passwords should be equal", errorText.getText());
    }

    public LoginPage signIn() {
        signInBtn.click();
        return new LoginPage();
    }

}
