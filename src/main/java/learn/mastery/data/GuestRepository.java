package learn.mastery.data;

import learn.mastery.models.Guest;

public interface GuestRepository {
    Guest findByEmail(String email);

    Guest findById(int id);
}
