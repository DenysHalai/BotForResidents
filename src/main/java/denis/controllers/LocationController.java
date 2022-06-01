package denis.controllers;

import denis.googleMapsApi.GeodecodingSample;
import denis.model.LocationData;
import denis.model.LocationDataDataBase;
import denis.repository.DataBaseAddressRepository;
import denis.service.FindAddressInDataBaseService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "https://bot-vue.vercel.app")
@RestController
public class LocationController {

    private final DataBaseAddressRepository dataBaseAddressRepository;
    private final FindAddressInDataBaseService findAddressInDataBaseService;

    public LocationController(DataBaseAddressRepository dataBaseAddressRepository, FindAddressInDataBaseService findAddressInDataBaseService) {
        this.dataBaseAddressRepository = dataBaseAddressRepository;
        this.findAddressInDataBaseService = findAddressInDataBaseService;
    }

    @GetMapping("/location/{column}")
    public List<String> findUserEndpoint(@PathVariable String column, @RequestParam String value) {
        List<String> resultList;
        switch (column) {
            case "city":
                resultList = dataBaseAddressRepository.findUniqTitle(value);
                break;
            case "street":
                resultList = dataBaseAddressRepository.findUniqStreet(value);
                break;
            default:
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
