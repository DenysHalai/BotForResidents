package denis.controllers;

import denis.googleMapsApi.GeodecodingSample;
import denis.model.City;
import denis.model.LocationData;
import denis.model.LocationDataDataBase;
import denis.model.Street;
import denis.repository.CityRepository;
import denis.repository.DataBaseAddressRepository;
import denis.repository.StreetRepository;
import denis.service.FindAddressInDataBaseService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin
@RestController
public class LocationController {

    private final DataBaseAddressRepository dataBaseAddressRepository;
    private final FindAddressInDataBaseService findAddressInDataBaseService;
    private final CityRepository cityRepository;
    private final StreetRepository streetRepository;

    public LocationController(DataBaseAddressRepository dataBaseAddressRepository, FindAddressInDataBaseService findAddressInDataBaseService, CityRepository cityRepository, StreetRepository streetRepository) {
        this.dataBaseAddressRepository = dataBaseAddressRepository;
        this.findAddressInDataBaseService = findAddressInDataBaseService;
        this.cityRepository = cityRepository;
        this.streetRepository = streetRepository;
    }

    @GetMapping("/location/")
    public List<String> findUserEndpoint(@RequestParam String cityName, @RequestParam(required = false) String streetName) {
        List<String> resultList;
        if (cityName != null && streetName == null) {
            resultList = cityRepository.findByTitleContainingIgnoreCase(cityName).stream().map(City::getTitle).collect(Collectors.toList());
        } else if (streetName != null) {
            resultList = streetRepository.findCityAndStreet(cityName, streetName).stream().map(Street::getTitle).collect(Collectors.toList());
        } else {
            resultList = new ArrayList<>();
        }
        return resultList;
    }

    @GetMapping("/location/auto/")
    public LocationDataDataBase findUserLocation(@RequestParam String lng, @RequestParam String lat) throws IOException {
        LocationData locationData = new GeodecodingSample().geodecodingSample(lat, lng);
        return findAddressInDataBaseService.findAddressContains(locationData);
    }
}
