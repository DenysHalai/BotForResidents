package denis.service;

import denis.model.*;
import denis.repository.CityRepository;
import denis.repository.DataBaseAddressRepository;
import denis.repository.StreetRepository;
import denis.repository.UserAddressRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class FindAddressInDataBaseService {

    private final DataBaseAddressRepository dataBaseAddressRepository;
    private final UserAddressRepository userAddressRepository;
    private final CityRepository cityRepository;
    private final StreetRepository streetRepository;

    public FindAddressInDataBaseService(DataBaseAddressRepository dataBaseAddressRepository, UserAddressRepository userAddressRepository, CityRepository cityRepository, StreetRepository streetRepository) {
        this.dataBaseAddressRepository = dataBaseAddressRepository;
        this.userAddressRepository = userAddressRepository;
        this.cityRepository = cityRepository;
        this.streetRepository = streetRepository;
    }

    public LocationDataDataBase findAddressContains(LocationData locationData) {
        List<DataBaseAddress> baseAddresses = dataBaseAddressRepository.findByCityAndStreet((locationData.getCityType() + " " + locationData.getCity()).toUpperCase(),
                (locationData.getStreetType() + " " + locationData.getStreet()).toUpperCase(), locationData.getNumber().toUpperCase(), PageRequest.of(0,50));
        if (!baseAddresses.isEmpty()) {
            DataBaseAddress dataBaseAddress = baseAddresses.get(0);
            return new LocationDataDataBase(dataBaseAddress.getStreet().getCity().getTitle(), dataBaseAddress.getStreet().getTitle(), dataBaseAddress.getNumber(), dataBaseAddress.getId());
        }
        return null;
    }

    public void tryFindAddress(String title, String street, String number, Long userId, String apartment) {
        Optional<DataBaseAddress> addressList = dataBaseAddressRepository.findByUserData(title, street, number);
        UserAddress userAddressNew = new UserAddress();
        if (addressList.isPresent()) {
            Long idAddress = addressList.get().getId();
            userAddressNew.setUserId(userId);
            userAddressNew.setAddressId(idAddress);
            if (apartment != null) {
                userAddressNew.setApartmentNumber(apartment);
            }
            userAddressRepository.save(userAddressNew);
        } else {
            City city1 = cityRepository.findByTitle(title).orElseGet(() -> {
                City city = new City();
                city.setTitle(title);
                return cityRepository.save(city);
            });
            Street street2 = streetRepository.findByCityTitleAndTitle(city1.getTitle(), street).orElseGet(() -> {
                Street street1 = new Street();
                street1.setTitle(street);
                street1.setCity(city1);
                return streetRepository.save(street1);
            });
            DataBaseAddress newDataBaseAddress = new DataBaseAddress();
            newDataBaseAddress.setStreet(street2);
            newDataBaseAddress.setNumber(number);
            Long id = dataBaseAddressRepository.save(newDataBaseAddress).getId();
            userAddressNew.setAddressId(id);
            userAddressNew.setUserId(userId);
            userAddressNew.setApartmentNumber(apartment);
            userAddressRepository.save(userAddressNew);
        }
    }
}
