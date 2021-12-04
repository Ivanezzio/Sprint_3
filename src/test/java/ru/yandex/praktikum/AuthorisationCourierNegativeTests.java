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

public class AuthorisationCourierNegativeTests {
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
    public void tearDown(){
        courierId = courierClient.login(CourierData.from(courier));
        courierClient.delete(courierId);
    }


    @Test
    @DisplayName("Авторизация курьера без указания логина")
    public void courierAuthorisationWithOutLogin(){
        courierClient.create(courier);
        ValidatableResponse response1 = courierClient.authorizationWithoutLogin(courier);
        courierId = courierClient.login(CourierData.from(courier));

        int statusCode = response1.extract().statusCode();
        String errorText = response1.extract().path("message");

        assertThat(statusCode, equalTo(400));
        assertEquals("Недостаточно данных для входа", errorText);
    }

    @Test(timeout=15000) // Добавлен тайм-аут, так как с такими параметрами запроса сервер не отвечает
    @DisplayName("Авторизация курьера без указания пароля")
    public void courierAuthorisationWithOutPassword() {
        courierClient.create(courier);
        ValidatableResponse response = courierClient.authorizationWithoutPassword(courier);
        courierId = courierClient.login(CourierData.from(courier));

        int statusCode = response.extract().statusCode();
        String errorText = response.extract().path("message");

        assertThat(statusCode, equalTo(400));
        assertEquals("Недостаточно данных для входа", errorText);
    }
}
