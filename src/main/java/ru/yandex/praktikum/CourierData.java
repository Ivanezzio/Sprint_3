package ru.yandex.praktikum;

public class CourierData {
    public final String login;
    public final String password;

    public CourierData(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierData from(Courier courier) {
        return new CourierData(courier.login, courier.password);
    }
}
