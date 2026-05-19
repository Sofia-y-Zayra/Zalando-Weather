import org.junit.Test;
import org.ulpgc.control.utils.ColorType;
import org.ulpgc.model.Outfit;
import org.ulpgc.model.Product;
import org.ulpgc.control.OutfitBuilder;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class OutfitBuilderTest {
    @Test
    public void shouldAddJacketWhenTemperatureIsLow() {

        Product tshirt = new Product();
        tshirt.setName("Camiseta Blanca");
        tshirt.setCategory("HOMBRE_CAMISETA");
        tshirt.setColor(ColorType.BLANCO);
        tshirt.setPrice(20);

        Product pants = new Product();
        pants.setName("Pantalon Negro");
        pants.setCategory("HOMBRE_PANTALON");
        pants.setColor(ColorType.NEGRO);
        pants.setPrice(30);

        Product jacket = new Product();
        jacket.setName("Chaqueta Negra");
        jacket.setCategory("HOMBRE_CHAQUETA");
        jacket.setColor(ColorType.NEGRO);
        jacket.setPrice(50);

        OutfitBuilder builder = new OutfitBuilder();

        Outfit result = builder.build(
                10,
                "rain",
                "HOMBRE",
                List.of(tshirt, pants, jacket)
        );

        assertNotNull(result.getJacket());
    }
}
