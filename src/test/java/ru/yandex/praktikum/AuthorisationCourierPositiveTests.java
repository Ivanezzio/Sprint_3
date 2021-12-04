package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotEquals;


public class AuthorisationCourierPositiveTests {
    private Courier courier;
    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = BaseUri.getBaseUri();
        courier = Courier.getRandom();
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Авторизация зарегистрированного курьера")
    public void courierLoginWithFullData() {
        ValidatableResponse response = courierClient.create(courier);
        courierId = courierClient.login(CourierData.from(courier));

        assertNotEquals(0,courierId);

    }

}
