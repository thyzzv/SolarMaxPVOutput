package nl.vonkit.solarmaxdatalogger.pvoutput;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@Slf4j
public class PVOutputClient {
    private final static String STATUS_ENDPOINT = "/service/r2/addstatus.jsp";
    private final PVOutputConfig config;

    public PVOutputClient(PVOutputConfig config) {
        this.config = config;
        log.info("Starting with:  {} ", config);
    }

    public void postStatus(PVOutputRequest request) {
        var restTemplate = new RestTemplate();
        var fooResourceUrl = String.format("%s%s", config.getHost(), STATUS_ENDPOINT);
        var headers = new HttpHeaders();
        headers.add("X-Pvoutput-Apikey", config.getApiKey());
        headers.add("X-Pvoutput-SystemId", config.getSystemId());
        headers.setAccept(List.of(MediaType.TEXT_PLAIN));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        var parameters = request.toParams();
        var map = new LinkedMultiValueMap<String, String>();
        parameters.forEach(map::add);

        var entity = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        try {
            restTemplate.postForEntity(fooResourceUrl, entity, String.class);
        } catch (Exception e) {
            log.error("Call to pvoutput failed", e);
            throw e;
        }
    }


}
