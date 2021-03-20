package nl.vonkit.solardatalogger.sma;

import com.google.gson.JsonArray;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import lombok.Data;
import lombok.ToString;
import nl.vonkit.solardatalogger.BaseCommand;
import nl.vonkit.solardatalogger.BaseResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Data
@ToString
public class SmaResponse implements BaseResponse {
    private String response;
    private final String pvOutputSystemId;
    private DocumentContext context;
    private LocalDateTime dateTime;
    private boolean processable;
    private Map<BaseCommand, Float> responseParameters;

    SmaResponse(String response, final String pvOutputSystemId) {
        this.response = response;
        this.pvOutputSystemId = pvOutputSystemId;
        if (!isBlank(response)) {
            this.responseParameters = new HashMap<>();
            parse();
        }
    }

    private void parse() {
        Configuration conf = Configuration.builder()
                .jsonProvider(new GsonJsonProvider())
                .options(Option.SUPPRESS_EXCEPTIONS)
                .build();
        context = JsonPath.using(conf).parse(response);

        for (final SmaCommand command : SmaCommand.values()) {

            var valueStr = ((JsonArray) context.read("$.result.*." + command.getResponeKey() + ".1.[0].val")).get(0).toString();

            if(valueStr == null){
                processable = false;
                return;
            } else if(valueStr.equals("null")){
                valueStr = "0";
            }
            float value = (float) Integer.parseInt(valueStr);
            responseParameters.put(command, value);
        }

        dateTime = LocalDateTime.now();
        processable = true;
    }

    public Map<BaseCommand, Float> getResponseValues() {
        if (processable) {
            return responseParameters;
        }
        return null;
    }

    @Override
    public String toString() {
        return "SmaResponse{" +
                " responseParams='" + getHumanResponseValues() + '\'' +
                ", processable=" + processable +
                ", dateTime= " + dateTime +
                '}';
    }

    @Override
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String getSystemId() {
        return pvOutputSystemId;
    }
}
