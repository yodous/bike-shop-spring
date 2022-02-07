package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO ML: this is a candidate for immutable object, which has final fields and is created only through constructor; take a look on `@Data  @Builder  @RequiredArgsConstructor(onConstructor_ = @JsonCreator(mode = JsonCreator.Mode.PROPERTIES))`
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
	// TODO ML: @NotNull? @NotEmpty?
    private String username;
	// TODO ML: @NotNull? @Size?
    private char[] password;
}
