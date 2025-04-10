package org.example;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.example.Endpoints.*;

public class Order {
    public void orderGetList() {
        given().log().all()
                .baseUri(URL)
                .get(GET_ORDER_LIST)
                .then()
                .assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
    }
}
