/*package com.solux.greenish.Weather.Controller;

import com.solux.greenish.Location.Domain.Location;
import com.solux.greenish.Location.Service.LocationService;
import com.solux.greenish.Weather.Response.KmaWeatherResponse;
import com.solux.greenish.Weather.Service.KmaWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    @Autowired
    private KmaWeatherService kmaWeatherService;

    @Autowired
    private LocationService locationService;

    @GetMapping("/weather/forecast")
    public KmaWeatherResponse getWeatherForecast() {
        Location location = locationService.getCurrentLocation();
        return kmaWeatherService.getWeatherForecast(location);
    }

    @PostMapping("/weather/set-location")
    public void setLocation(@RequestBody Location location) {
        locationService.setCurrentLocation(location);
    }
}
*/
