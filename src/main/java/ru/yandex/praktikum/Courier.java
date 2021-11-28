package ru.yandex.praktikum;

import org.apache.commons.lang3.RandomStringUtils;


public class Courier {
    public String login;
    public String password;
    public String firstName;


    //конструктор курьера с параметрами для регистрации
   public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    //конструктор курьера со случайными данными
    public static Courier getRandom() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, firstName);
    }










}
