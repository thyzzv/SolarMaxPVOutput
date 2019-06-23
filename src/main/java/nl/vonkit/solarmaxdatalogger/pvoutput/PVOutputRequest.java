package nl.vonkit.solarmaxdatalogger.pvoutput;

import nl.vonkit.solarmaxdatalogger.solarmax.SolarMaxCommand;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


public class PVOutputRequest {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.BASIC_ISO_DATE;
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private final LocalDateTime dateTime;

    Map<SolarMaxCommand, Float> solarMaxValues;

    public PVOutputRequest(Map<SolarMaxCommand, Float> solarMaxValues, LocalDateTime localDateTime){
        this.solarMaxValues = solarMaxValues;
        this.dateTime = localDateTime;
    }

    public Map<String, String> toParams() {
        Map<String, String> params = new HashMap<>();
        params.put("d", dateFormatter.format(dateTime));
        params.put("t", timeFormatter.format(dateTime));
        for(var entry: solarMaxValues.entrySet()){
            params.put(entry.getKey().getPVoutputParam(), entry.getKey().getToPVOutputConverter().apply(entry.getValue()));
        }
        return params;
    }

    @Override
    public String toString() {
        return "" + toParams();
    }
}
