package com.solux.greenish.Location.Service;

import com.solux.greenish.Location.Domain.Location;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    private Location currentLocation;
    public Location getCurrentLocation(){
        return currentLocation;
    }
    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }
}
