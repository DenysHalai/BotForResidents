package denis.controllers;

import denis.model.DataBaseAddress;
import denis.repository.DataBaseAddressRepository;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LocationController {

    @Autowired
    private DataBaseAddressRepository dataBaseAddressRepository;

    @CrossOrigin(origins = "https://bot-vue.vercel.app")
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
}
