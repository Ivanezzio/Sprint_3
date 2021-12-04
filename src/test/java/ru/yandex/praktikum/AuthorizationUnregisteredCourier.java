package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class AuthorizationUnregisteredCourier {
    private Courier courier;
    private CourierClient courierClient;

    @Before
    public void setUp() {
        RestAssured.baseURI = BaseUri.getBaseUri();
        courier = Courier.getRandom();
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Авторизация незарегистрированного курьера")
    public void courierAuthorisationWithIncorrectData(){
        Response response = (Response) courierClient.authorizationUnregisteredCourier(CourierData.from(courier));

        int statusCode = response.statusCode();
        String errorText = response.path("message");

        assertThat(statusCode, equalTo(404));
        assertEquals("Учетная запись не найдена", errorText);
    }
}
