package ru.yandex.praktikum;

public class BaseUri {
    private static String baseURI;
    public static String getBaseUri() {
       return baseURI = "http://qa-scooter.praktikum-services.ru";
    }
}