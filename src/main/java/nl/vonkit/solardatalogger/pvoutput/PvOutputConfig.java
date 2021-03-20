package nl.vonkit.solardatalogger.pvoutput;

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
public class PvOutputConfig {
    @NonNull
    private String host;
    @NonNull
    private String apiKey;
}
