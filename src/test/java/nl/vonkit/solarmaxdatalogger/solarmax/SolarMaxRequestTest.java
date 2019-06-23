package nl.vonkit.solarmaxdatalogger.solarmax;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;


public class SolarMaxRequestTest {

    @Test
    public void messageShouldBeEncodedCorrectly() {
        var req = new SolarMaxRequest(List.of("PAK", "PKK", "KDY", "UL1"));
        assertThat(req.toString(), is("{FB;00;22|64:PAK;PKK;KDY;UL1|078B}"));
    }
}
