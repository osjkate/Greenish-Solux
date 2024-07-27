/*package com.solux.greenish.Weather.Service;

import com.solux.greenish.Location.Domain.Location;
import com.solux.greenish.Weather.Response.KmaWeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KmaWeatherService {
    @Value("${kma.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public KmaWeatherResponse getWeatherForecast(Location location) {
        String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst";
        url += "?serviceKey=" + apiKey;
        url += "&dataType=json";
        url += "&base_date=" + location.getBaseDate();
        url += "&base_time=" + location.getBaseTime();
        url += "&nx=" + location.getNx();
        url += "&ny=" + location.getNy();

        return restTemplate.getForObject(url, KmaWeatherResponse.class);
    }
}
*/