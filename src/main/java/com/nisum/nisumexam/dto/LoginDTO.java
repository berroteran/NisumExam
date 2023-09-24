package com.nisum.nisumexam.dto;


import com.nisum.nisumexam.support.annotation.NotEmptyBlankString;

public record LoginDTO(@NotEmptyBlankString(message = "{user.name.required}") String password, @NotEmptyBlankString(message = "{user.password.required}") String email) {

}
