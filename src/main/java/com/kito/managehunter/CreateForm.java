package com.kito.managehunter;

import jakarta.validation.constraints.*;

public class CreateForm {

    @NotBlank(message = "Enter your name.")
    @Size(max = 20, message = "Names should be no more than 20 characters.")
    private String name;
    @NotBlank(message = "Enter your sex.")
    @Pattern(regexp = "^[MF]$", message = "Please enter M or F.")
    private String sex;
    @NotNull(message = "Enter your age.")
    @Min(value = 4, message = "Children under 3 years old cannot register.")
    private Integer age;

    public CreateForm(String name, String sex, int age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
