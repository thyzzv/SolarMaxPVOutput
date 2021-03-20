package nl.vonkit.solardatalogger.pvoutput;

import nl.vonkit.solardatalogger.BaseCommand;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


public class PvOutputRequest {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.BASIC_ISO_DATE;
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private final LocalDateTime dateTime;
    private final String systemId;

    Map<BaseCommand, Float> solarMaxValues;

    public PvOutputRequest(Map<BaseCommand, Float> solarMaxValues, LocalDateTime localDateTime, String systemId){
        this.solarMaxValues = solarMaxValues;
        this.dateTime = localDateTime;
        this.systemId = systemId;
    }

    public Map<String, String> toParams() {
        Map<String, String> params = new HashMap<>();
        params.put("d", dateFormatter.format(dateTime));
        params.put("t", timeFormatter.format(dateTime));
        for(var entry: solarMaxValues.entrySet()){
            params.put(entry.getKey().getPvOutputParam(), entry.getKey().getToPvOutputConverter().apply(entry.getValue()));
        }
        return params;
    }

    public String getSystemId(){
        return systemId;
    }

    @Override
    public String toString() {
        return "" + toParams();
    }
}
