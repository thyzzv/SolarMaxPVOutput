package nl.vonkit.solardatalogger.pvoutput;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Map;

import static nl.vonkit.solardatalogger.solarmax.SolarMaxCommand.P_AC;
import static nl.vonkit.solardatalogger.solarmax.SolarMaxCommand.U_AC_L1;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class PvOutputRequestTest {

    @Test
    public void toParams() {
        var dateTime = LocalDateTime.of(2019, 6, 27, 12,59,59);
        var req = new PvOutputRequest(Map.of(P_AC, 1.1f, U_AC_L1, 0.2f), dateTime, "123");

        assertEquals(req.toParams(), Map.of("v2", "1", "v6", "0.02", "d", "20190627", "t", "12:59"));
    }
}
