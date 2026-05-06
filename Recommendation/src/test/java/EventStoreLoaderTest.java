import org.junit.Test;
import org.ulpgc.persistence.DatamartStore;
import org.ulpgc.persistence.EventStoreLoader;

import java.io.File;
import java.io.FileWriter;

import static org.junit.jupiter.api.Assertions.*;

public class EventStoreLoaderTest {

    @Test
    public void shouldLoadHistoricalEventsWithoutErrors() throws Exception {

        File folder =
                new File("eventstore/Product/test-feeder");

        folder.mkdirs();

        File file =
                new File(folder, "20260428.events");

        try (FileWriter writer = new FileWriter(file)) {
            writer.write("{\"ts\":\"2026-04-28T12:00:00Z\",\"id\":\"1\",\"name\":\"Camiseta\",\"price\":19.99,\"category\":\"Clothes\"}\n");
        }

        DatamartStore store = new DatamartStore();
        EventStoreLoader loader =
                new EventStoreLoader(store);

        assertDoesNotThrow(loader::loadAll);
    }
}
