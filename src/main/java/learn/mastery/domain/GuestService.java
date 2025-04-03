package learn.mastery.domain;

import learn.mastery.data.GuestRepository;
import learn.mastery.models.Guest;
import org.springframework.stereotype.Service;

@Service
public class GuestService {
    private final GuestRepository repository;

    public GuestService(GuestRepository repository) {
        this.repository = repository;
    }

    public Guest findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
