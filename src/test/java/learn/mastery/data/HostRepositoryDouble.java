package learn.mastery.data;

import learn.mastery.models.Host;

import java.math.BigDecimal;
import java.util.List;

public class HostRepositoryDouble implements HostRepository {

    private List<Host> findAll() {
        return List.of(
                new Host("3edda6bc-ab95-49a8-8962-d50b53f84b15","Yearnes","eyearnes0@sfgate.com","(806) 1783815","3 Nova Trail","Amarillo","TX",79182,new BigDecimal("340"),new BigDecimal("425")),
                new Host("a0d911e7-4fde-4e4a-bdb7-f047f15615e8","Rhodes","krhodes1@posterous.com","(478) 7475991","7262 Morning Avenue","Macon","GA",31296,new BigDecimal("295"),new BigDecimal("368.75")),
                new Host("b4f38829-c663-48fc-8bf3-7fca47a7ae70","Fader","mfader2@amazon.co.jp","(501) 2490895","99208 Morning Parkway","North Little Rock","AR",72118,new BigDecimal("451"),new BigDecimal("563.75")),
                new Host("9f2578e7-6723-482b-97c1-f9be0b7c96dd","Spellesy","rspellesy3@google.co.jp","(214) 5201692","78765 Lotheville Drive","Garland","TX",75044,new BigDecimal("433"),new BigDecimal("541.25")),
                new Host("b6ddb844-b990-471a-8c0a-519d0777eb9b","Harley","charley4@apple.com","(954) 7895760","1 Maple Wood Terrace","Orlando","FL",32825,new BigDecimal("176"),new BigDecimal("220"))
        );
    }

    @Override
    public Host findByEmail(String email) {
        return findAll().stream()
                .filter(i -> i.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }
}
