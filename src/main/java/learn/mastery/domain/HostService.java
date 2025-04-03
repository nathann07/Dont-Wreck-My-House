package learn.mastery.domain;

import learn.mastery.data.HostRepository;
import learn.mastery.models.Host;
import org.springframework.stereotype.Service;

@Service
public class HostService {
    private final HostRepository repository;

    public HostService(HostRepository repository) {
        this.repository = repository;
    }

    public Host findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
