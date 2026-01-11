package school.faang.user_service.repository.contact;

import org.springframework.data.jpa.repository.JpaRepository;
import school.faang.user_service.entity.contact.Contact;
import school.faang.user_service.exception.EntityNotFoundException;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    default Contact getByIdOrThrow(long contactId) {
        return findById(contactId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Contact %d not found", contactId))
        );
    }
}
