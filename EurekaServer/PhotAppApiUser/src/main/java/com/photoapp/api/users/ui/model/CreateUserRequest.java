package com.photoapp.api.users.ui.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequest {
    @NotNull(message = "First Name cannot be null")
    @Size(min=2,message="First name must not be less than two characters")
    private String firstname;
    @NotNull(message = "last Name cannot be null")
    @Size(min=2,message="last name must not be less than two characters")
    private String lastname;
    @NotNull(message = "password should not be null")
    @Size(min=8,max=16,message="Length must be between 8-16")
    private String password;
    @Email
    @NotNull(message = "Email cannot be null")
    private String email;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
