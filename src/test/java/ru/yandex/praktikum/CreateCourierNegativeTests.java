package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;


public class CreateCourierNegativeTests {
    private Courier courier;
    private CourierClient courierClient;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Создание курьера без указания логина")
    public void newCourierWithoutLoginTest(){
        Courier courier = Courier.getRandom();
        ValidatableResponse response = new CourierClient().createWithOutLogin(courier);

        int statusCode = response.extract().statusCode();
        String errorText = response.extract().path("message");

        assertThat(statusCode, equalTo(400));
        assertEquals("Недостаточно данных для создания учетной записи", errorText);
    }

    @Test
    @DisplayName("Создание курьера без указания пароля")
    public void newCourierWithoutPasswordTest(){
        Courier courier = Courier.getRandom();
        ValidatableResponse response = new CourierClient().createWithOutPassword(courier);

        int statusCode = response.extract().statusCode();
        String errorText = response.extract().path("message");

        assertThat(statusCode, equalTo(400));
        assertEquals("Недостаточно данных для создания учетной записи", errorText);
    }

}
