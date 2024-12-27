package guru.qa.niffler.util;

import com.github.javafaker.Faker;
import guru.qa.niffler.model.UserJson;

public class DataHelper {
    public static UserJson getRandomUser() {
        Faker faker = new Faker();
        return new UserJson(faker.name().firstName(), faker.internet().password(3, 7));
    }

    public static String getRandomCategory() {
        return new Faker().random().hex();
    }
}
