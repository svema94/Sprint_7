package org.example;

import static io.restassured.RestAssured.*;
import static org.example.Endpoints.GET_ORDER_LIST;
import static org.example.Endpoints.URL;
import io.restassured.response.Response;

public class Order {

    private final String baseUrl = URL;

    // Метод для отправки GET-запроса на получение списка заказов
    public Response getOrderList() {
        return given()
                .baseUri(baseUrl)
                .when()
                .get(GET_ORDER_LIST)
                .then()
                .extract()
                .response();
    }
}
