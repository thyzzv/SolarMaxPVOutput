package nl.vonkit.solarmaxdatalogger.pvoutput;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties("pvoutput")
@Validated
public class PVOutputConfig {
    @NonNull
    private String host;
    @NonNull
    private String apiKey;
    @NonNull
    private String systemId;
}
