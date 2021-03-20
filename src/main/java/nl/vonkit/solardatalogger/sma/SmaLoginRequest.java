package nl.vonkit.solardatalogger.sma;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SmaLoginRequest {

    private final String right;
    private String pass;

}
