package rs.ktech.CRUDAndRestApi.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import rs.ktech.CRUDAndRestApi.mvc.models.AddressModel;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "address_id_seq")
    private long id;

    @Column
    private String street;

    @Column
    private String city;

    @Column
    private String suite;

    @Column
    private String zipcode;

    @OneToOne
    @MapsId
    @JoinColumn(name = "api_user_id")
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static AddressModel toAddressModel(Address address) {
        AddressModel addressModel = new AddressModel();

        addressModel.setId(address.getId());
        addressModel.setStreet(address.getStreet());
        addressModel.setCity(address.getCity());
        addressModel.setSuite(address.getSuite());
        addressModel.setZipcode(address.getZipcode());

        return addressModel;
    }

    public static Address fromAddressModel(AddressModel addressModel) {
        Address address = new Address();

        address.setStreet(addressModel.getStreet());
        address.setCity(addressModel.getCity());
        address.setSuite(addressModel.getSuite());
        address.setZipcode(addressModel.getZipcode());

        return address;
    }
}
