package denis.service;

import denis.controllers.LocationData;
import denis.controllers.LocationDataDataBase;
import denis.model.DataBaseAddress;
import denis.model.UserAddress;
import denis.repository.DataBaseAddressRepository;
import denis.repository.UserAddressRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FindAddressInDataBaseService {

    private final DataBaseAddressRepository dataBaseAddressRepository;
    private final UserAddressRepository userAddressRepository;

    public FindAddressInDataBaseService(DataBaseAddressRepository dataBaseAddressRepository, UserAddressRepository userAddressRepository) {
        this.dataBaseAddressRepository = dataBaseAddressRepository;
        this.userAddressRepository = userAddressRepository;
    }

    public LocationDataDataBase findAddressContains(LocationData locationData) {
        List<DataBaseAddress> baseAddresses;
        if (locationData.getNumber() != null) {
            baseAddresses = dataBaseAddressRepository.findByLastnameOrFirstname(locationData.getCity(),
                    locationData.getStreetType() + " " + locationData.getStreet(), locationData.getNumber());
        } else if (locationData.getStreet() != null) {
            baseAddresses = dataBaseAddressRepository.findByLastnameOrFirstname(locationData.getCity(),
                    locationData.getStreetType() + " " + locationData.getStreet());
        } else if (locationData.getCity() != null) {
            baseAddresses = dataBaseAddressRepository.findByLastnameOrFirstname(locationData.getCity());
        } else {
            baseAddresses = new ArrayList<>();
        }
        if (!baseAddresses.isEmpty()) {
            DataBaseAddress dataBaseAddress = baseAddresses.get(0);
            return new LocationDataDataBase(dataBaseAddress.getTitle(), dataBaseAddress.getStreet(), dataBaseAddress.getNumber(), dataBaseAddress.getId());
        }
        return null;
    }

    public void tryFindAddress(String title, String street, String number, Long userId, String apartment) {
        Optional<DataBaseAddress> addressList = dataBaseAddressRepository.findOneByTitleIgnoreCaseAndStreetIgnoreCaseAndNumberIgnoreCase(title, street, number);
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
            DataBaseAddress newDataBaseAddress = new DataBaseAddress();
            newDataBaseAddress.setTitle(title);
            newDataBaseAddress.setStreet(street);
            newDataBaseAddress.setNumber(number);
            Long id = dataBaseAddressRepository.save(newDataBaseAddress).getId();
            userAddressNew.setAddressId(id);
            userAddressNew.setUserId(userId);
            userAddressNew.setApartmentNumber(apartment);
            userAddressRepository.save(userAddressNew);
        }
    }
}
