package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class CreateCourierPositiveTests {
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
    @DisplayName("Создание курьера")
    public void newRegistrationCourierTest() {
        ValidatableResponse response = courierClient.create(courier);
        courierId = courierClient.login(CourierData.from(courier));

        int statusCode = response.extract().statusCode();
        boolean isCourierCreated = response.extract().path("ok");

        assertTrue(isCourierCreated);
        assertThat(statusCode, equalTo(201));
    }

    @Test
    @DisplayName("Повторное создание курьера с уже зарегистрированными данными")
    public void reRegistrationCourierTest() {

        courierClient.create(courier);
        ValidatableResponse response = courierClient.create(courier);

        int statusCode = response.extract().statusCode();
        String errorText = response.extract().path("message");

        assertThat(statusCode, equalTo(409));
        assertEquals("Этот логин уже используется.", errorText);
    /*
    Тест будет завершаться с ошибкой, так как ожидаемый результат не соответствует документации.
    Expected: Этот логин уже используется
    Actual: Этот логин уже используется. Попробуйте другой.
     */
    }
}
