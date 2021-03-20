package nl.vonkit.solardatalogger.solarmax;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SolarMaxRequestTest {

    @Test
    public void messageShouldBeEncodedCorrectly() {
        var req = new SolarMaxRequest(List.of("PAK", "PKK", "KDY", "UL1"));
        assertEquals(req.toString(), "{FB;00;22|64:PAK;PKK;KDY;UL1|078B}");
    }
}
