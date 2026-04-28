import org.junit.Test;
import org.ulpgc.model.EventWriter;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertTrue;

public class EventWriterTest {

    @Test
    public void shouldCreateEventFileAndWriteJson() throws Exception {

        String json =
                "{\"ts\":\"2026-04-28T10:00:00Z\",\"ss\":\"zalando-feeder\",\"product\":{\"id\":\"1\"}}";

        EventWriter.write("Product", json);

        Path path = Path.of(
                "eventstore",
                "Product",
                "zalando-feeder",
                "20260428.events"
        );

        assertTrue(Files.exists(path));

        String content = Files.readString(path);

        assertTrue(content.contains("zalando-feeder"));
    }
}