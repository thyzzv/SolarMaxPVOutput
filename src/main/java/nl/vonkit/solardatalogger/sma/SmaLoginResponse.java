package nl.vonkit.solardatalogger.sma;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SmaLoginResponse {

    private Result result;

    @Data
    @ToString
    class Result {
        private String sid;
    }

}
