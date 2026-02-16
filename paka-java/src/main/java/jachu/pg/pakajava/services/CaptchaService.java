package jachu.pg.pakajava.services;

import jachu.pg.pakajava.entities.DTOs.GoogleResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class CaptchaService {

    @Value("${captcha-token}")
    private String recaptchaSecret;

    private final String recaptchaVerifyUrl = "https://www.google.com/recaptcha/api/siteverify";

    private final RestTemplate restTemplate;

    public CaptchaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean verifyRecaptcha(String ip, String responseToken) {
        // Tworzymy parametry żądania
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("secret", recaptchaSecret);
        map.add("response", responseToken);
        map.add("remoteip", ip); // Opcjonalne, ale zalecane przez Google

        try {
//             Wysyłamy żądanie POST do Google
            GoogleResponseDto apiResponse = restTemplate.postForObject(
                    recaptchaVerifyUrl,
                    map,
                    GoogleResponseDto.class
            );

//            ResponseEntity<String> apiResponse = restTemplate.postForEntity(recaptchaVerifyUrl, map, String.class);

            // Podstawowa weryfikacja
            if (apiResponse == null) {
                return false;
            }

            // DLA RECAPTCHA V2 (Checkbox):
            return apiResponse.isSuccess();
//            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
