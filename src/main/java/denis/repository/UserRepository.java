package denis.repository;

import denis.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Contact;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> getByChatId(Long chatId);

    Optional<PhoneNumber> getByPhoneNumber(String phoneNumber);

    Optional<User> findByChatId(Long chatId);
}