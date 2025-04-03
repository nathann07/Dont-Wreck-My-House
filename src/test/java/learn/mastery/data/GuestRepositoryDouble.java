package learn.mastery.data;

import learn.mastery.models.Guest;

import java.util.List;

public class GuestRepositoryDouble implements GuestRepository{

    private List<Guest> findAll() {
        return List.of(
                new Guest(1,"Sullivan","Lomas","slomas0@mediafire.com","(702) 7768761","NV"),
                new Guest(2,"Olympie","Gecks","ogecks1@dagondesign.com","(202) 2528316","DC"),
                new Guest(3,"Tremain","Carncross","tcarncross2@japanpost.jp","(313) 2245034","MI"),
                new Guest(4,"Leonidas","Gueny","lgueny3@example.com","(412) 6493981","PA"),
                new Guest(5,"Berta","Seppey","bseppey4@yahoo.com","(202) 2668098","DC")
        );
    }

    @Override
    public Guest findByEmail(String email) {
        return findAll().stream()
                .filter(i -> i.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Guest findById(int id) {
        return findAll().stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
