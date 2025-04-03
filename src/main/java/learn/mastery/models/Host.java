package learn.mastery.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Host {
    private String id;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private int postalCode;
    private BigDecimal standardRate;
    private BigDecimal weekendRate;

    public Host() {
    }

    public Host(String id, String lastName, String email, String phone, String address, String city, String state, int postalCode, BigDecimal standardRate, BigDecimal weekendRate) {
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.standardRate = standardRate;
        this.weekendRate = weekendRate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public BigDecimal getStandardRate() {
        return standardRate;
    }

    public void setStandardRate(BigDecimal standardRate) {
        this.standardRate = standardRate;
    }

    public BigDecimal getWeekendRate() {
        return weekendRate;
    }

    public void setWeekendRate(BigDecimal weekendRate) {
        this.weekendRate = weekendRate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Host host = (Host) o;
        return Objects.equals(id, host.id) && Objects.equals(lastName, host.lastName) && Objects.equals(email, host.email) && Objects.equals(phone, host.phone) && Objects.equals(address, host.address) && Objects.equals(city, host.city) && Objects.equals(state, host.state) && Objects.equals(postalCode, host.postalCode) && Objects.equals(standardRate, host.standardRate) && Objects.equals(weekendRate, host.weekendRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, email, phone, address, city, state, postalCode, standardRate, weekendRate);
    }
}
