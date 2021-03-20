package nl.vonkit.solardatalogger.sma;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class SmaClient {
    private final static String LOGIN_ENDPOINT = "/dyn/login.json";
    private final static String STATUS_ENDPOINT = "/dyn/getAllOnlValues.json";
    private final SmaConfig config;
    private Gson gson = new Gson();

    public SmaClient(SmaConfig config) {
        this.config = config;
        log.info("Starting with:  {} ", config);
    }

    public Optional<SmaLoginResponse> login(){
        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        var endpoint = String.format("%s%s", config.getHost(), LOGIN_ENDPOINT);
        headers.add("Cookie", "tmhDynamicLocale.locale=%22en-gb%22");
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        var json = gson.toJson(SmaLoginRequest.builder().pass(config.getPassword()).right(config.getRight()));
        var entity = new HttpEntity<>(json, headers);

        try {
            return Optional.ofNullable(restTemplate.postForEntity(endpoint, entity, SmaLoginResponse.class).getBody());
        } catch (Exception e) {
            log.error("Call to pvoutput failed", e);
            throw e;
        }

    }

    public Optional<SmaResponse> request(){
        var loginResponse = login();
        if(loginResponse.isEmpty() || loginResponse.get().getResult() == null){
            return Optional.empty();
        }
        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        var endpoint = String.format("%s%s?sid=%s", config.getHost(), STATUS_ENDPOINT, loginResponse.get().getResult().getSid());
        headers.add("Cookie", "tmhDynamicLocale.locale=%22en-gb%22");
        headers.add("Cookie", "user80=%7B%22role%22%3A%7B%22bitMask%22%3A2%2C%22title%22%3A%22usr%22%2C%22loginLevel%22%3A1%7D%2C%22username%22%3A861%2C%22sid%22%3A%22"+loginResponse.get().getResult().getSid()+"%22%7D");
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        var json = "{\n \"destDev\": []\n }";
        var entity = new HttpEntity<String>(json, headers);

        try {
            String response = restTemplate.postForEntity(endpoint, entity, String.class).getBody();
            return Optional.of(new SmaResponse(response, config.getPvOutputSystemId()));
        } catch (Exception e) {
            log.error("Call to pvoutput failed", e);
            throw e;
        }

    }

}
