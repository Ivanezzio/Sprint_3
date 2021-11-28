package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

public class AuthorisationCourierNegativeTests {
    private Courier courier;
    private CourierClient courierClient;
    private int courierId;


    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        courier = Courier.getRandom();
        courierClient = new CourierClient();
    }


    @Test
    @DisplayName("Авторизация курьера без указания логина")
    public void courierAuthorisationWithOutLogin(){
        courierClient.create(courier);
        ValidatableResponse response1 = courierClient.loginWithoutLogin(courier);

        int statusCode = response1.extract().statusCode();
        String errorText = response1.extract().path("message");

        assertThat(statusCode, equalTo(400));
        assertEquals("Недостаточно данных для входа", errorText);
// корректная авторизация для удаления ранее созданных тестовых данных
        courierId = courierClient.login(CourierData.from(courier));
        courierClient.delete(courierId);

    }

    @Test(timeout=15000) // Добавлен тайм-аут, так как с такими параметрами запроса сервер не отвечает
    @DisplayName("Авторизация курьера без указания пароля")
    public void courierAuthorisationWithOutPassword() {
        courierClient.create(courier);
        ValidatableResponse response1 = courierClient.loginWithoutPassword(courier);

        int statusCode = response1.extract().statusCode();
        String errorText = response1.extract().path("message");

        assertThat(statusCode, equalTo(400));
        assertEquals("Недостаточно данных для входа", errorText);

// корректная авторизация для удаления ранее созданных тестовых данных
        courierId = courierClient.login(CourierData.from(courier));
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Авторизация незарегистрированного курьера ")
    public void courierAuthorisationWithIncorrectData() {
        ValidatableResponse response = courierClient.unregisteredCourierlogin(courier);

        int statusCode = response.extract().statusCode();
        String errorText = response.extract().path("message");

        assertThat(statusCode, equalTo(404));
        assertEquals("Учетная запись не найдена", errorText);
    }



}
