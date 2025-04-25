import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.example.Order;
import io.restassured.response.Response;

public class OrderListTest {

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Все активные/завершенные заказы курьера")
    public void orders() {
        Order order = new Order();

        // Выполняем запрос и получаем ответ
        Response response = order.getOrderList();

        // Проверяем, что статус-код равен 200
        assertEquals(200, response.getStatusCode());

        // Проверяем, что тело ответа не пустое
        assertNotNull(response.body());
    }
}
