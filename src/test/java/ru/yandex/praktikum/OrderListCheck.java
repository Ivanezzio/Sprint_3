package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.*;
import static io.restassured.RestAssured.given;

public class OrderListCheck  {
    @Before
    public void setUp() {
        RestAssured.baseURI = BaseUri.getBaseUri();
    }

    @Test
    @DisplayName("Тест на проверку того, что запрос возвращает список заказов")
    public void isReturnedOrderListNotEmpty (){

        List<Object> orders = given()
                .header("Content-type", "application/json")
                .when()
                .get("/api/v1/orders")
                .then()
                .extract()
                .jsonPath()
                .getList("orders");

        assertFalse(orders.isEmpty());
    }
}