package learn.mastery.data;

import learn.mastery.models.Host;

public interface HostRepository {
    Host findByEmail(String email);
}
