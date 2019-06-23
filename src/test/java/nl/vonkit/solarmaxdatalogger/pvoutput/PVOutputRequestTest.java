package nl.vonkit.solarmaxdatalogger.pvoutput;

import nl.vonkit.solarmaxdatalogger.solarmax.SolarMaxCommand;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Map;
import static org.hamcrest.Matchers.is;

import static org.junit.Assert.*;
import static nl.vonkit.solarmaxdatalogger.solarmax.SolarMaxCommand.*;
public class PVOutputRequestTest {

    @Test
    public void toParams() {
        var dateTime = LocalDateTime.of(2019, 6, 27, 12,59,59);
        var req = new PVOutputRequest(Map.of(P_AC, 1.1f, U_AC_L1, 0.2f), dateTime);

        assertThat(req.toParams(), is(Map.of("v2", "1", "v6", "0.02", "d", "20190627", "t", "12:59")));
    }
}