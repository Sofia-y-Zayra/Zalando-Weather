import org.junit.Test;
import org.ulpgc.model.DatamartStore;
import org.ulpgc.model.DatamartUpdater;

import static org.junit.jupiter.api.Assertions.*;

public class DatamartUpdaterTest {

    @Test
    public void shouldProcessWeatherWithoutErrors() {

        DatamartStore store = new DatamartStore();
        DatamartUpdater updater = new DatamartUpdater(store);

        String json =
                """
                {
                  "ts":"2026-04-28T12:00:00Z",
                  "data":{
                    "city":"Madrid",
                    "temperature":22.5,
                    "description":"Sunny"
                  }
                }
                """;

        assertDoesNotThrow(() ->
                updater.update(json, "Weather"));
    }

    @Test
    public void shouldProcessProductWithoutErrors() {

        DatamartStore store = new DatamartStore();
        DatamartUpdater updater = new DatamartUpdater(store);

        String json =
                """
                {
                  "ts":"2026-04-28T12:00:00Z",
                  "product":{
                    "id":"101",
                    "name":"Zapatillas",
                    "price":59.99,
                    "category":"Shoes"
                  }
                }
                """;

        assertDoesNotThrow(() ->
                updater.update(json, "Product"));
    }
}