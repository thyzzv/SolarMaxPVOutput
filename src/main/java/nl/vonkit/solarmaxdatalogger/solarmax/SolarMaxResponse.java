package nl.vonkit.solarmaxdatalogger.solarmax;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
public class SolarMaxResponse {
    private static final String messageDelimiter = ";";
    private static final String messagePartDelimiter = "|";

    private static final int PREFIX_LENGTH = 13;
    private static final int SUFFIX_LENGTH = 6;
    private static final String source = "FB";
    private static final String defaultDestination = "00";

    private final String fullResponse;
    private Map<SolarMaxCommand, Float> responseParameters = new HashMap<>();
    private LocalDateTime dateTime;
    private boolean processable;

//    private String destination = "00";
//    private final String encoding = "64:";


    public SolarMaxResponse(@NonNull String response) {
        if(response.contains("}")) {
            this.fullResponse = response.substring(0, response.indexOf('}'));
            parse();
        } else {
            this.fullResponse = response;
        }
    }

    private void parse() {
        final String[] splitResponse = fullResponse.substring(fullResponse.indexOf("|") + 4, fullResponse.lastIndexOf("|")).split(";");

        for (final String kvPair : splitResponse) {
            String[] keyValue = kvPair.split("=");
            final SolarMaxCommand key = SolarMaxCommand.getEnumByString(keyValue[0]);
            String valueHex = keyValue[1];
            //fix error (crash)
            if (valueHex == null || valueHex.contains(",")) {
                processable = false;
                return;
            }
            float value = (float) Integer.parseInt(valueHex, 16);
            responseParameters.put(key, value);
        }
        dateTime = LocalDateTime.now();
        processable = true;
    }

    public Map<SolarMaxCommand, Float> getResponseValues() {
        if (processable) {
            return responseParameters;
        }
        return null;
    }

    public static SolarMaxResponse from(final char[] charbuffer) {
        return new SolarMaxResponse(new String(charbuffer));
    }

    @Override
    public String toString() {
        return "SolarMaxResponseMessage{" +
                "fullResponse='" + fullResponse + '\'' +
                " responseParams='" + getHumanResponseValues() + '\'' +
                ", processable=" + processable +
                ", dateTime= " + dateTime +
                '}';
    }

    private Map<SolarMaxCommand, String> getHumanResponseValues() {
        final Map<SolarMaxCommand, String> map = new HashMap<>();
        responseParameters.forEach((key, value) -> {
            final float convertedValue = key.getToHumanConverter().apply(value);
            final String strValue = (convertedValue % 1.0 != 0) ? String.format("%s", convertedValue) : String.format("%.00f", convertedValue);
            map.put(key, strValue);
        });
        return map;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
