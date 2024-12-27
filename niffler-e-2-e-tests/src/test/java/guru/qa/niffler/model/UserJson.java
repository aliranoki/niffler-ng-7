package guru.qa.niffler.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserJson(
        @JsonProperty("password")
        String password,
        @JsonProperty("username")
        String username
) {


}
