package nl.vonkit.solardatalogger;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public interface BaseResponse {
    Map<BaseCommand, Float> getResponseValues();

    default Map<BaseCommand, String> getHumanResponseValues() {
        final Map<BaseCommand, String> map = new HashMap<>();
        getResponseValues().forEach((key, value) -> {
            final float convertedValue;
            if (key.getToHumanConverter() != null) {
                convertedValue = key.getToHumanConverter().apply(value);
            } else {
                convertedValue = value;
            }
            final String strValue = (convertedValue % 1.0 != 0) ? String.format("%s", convertedValue) : String.format("%.00f", convertedValue);
            map.put(key, strValue);
        });
        return map;
    }

    LocalDateTime getDateTime();

    String getSystemId();
}
