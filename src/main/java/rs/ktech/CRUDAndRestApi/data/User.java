package rs.ktech.CRUDAndRestApi.data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import rs.ktech.CRUDAndRestApi.mvc.models.UserModel;

@Entity
@Table(name = "api_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "api_user_id_seq")
    private long id;

    @Column
    private String name;

    @Column
    private String phone;

    @Column
    private String website;

    @Column(name = "ext_id")
    private long extId;

    @OneToOne(mappedBy = "user", cascade = {CascadeType.ALL})
    @PrimaryKeyJoinColumn
    private Address address;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public long getExtId() {
        return extId;
    }

    public void setExtId(long extId) {
        this.extId = extId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public static UserModel toUserModel(User user) {
        UserModel userModel = new UserModel();

        userModel.setId(user.getId());
        userModel.setName(user.getName());
        userModel.setPhone(user.getPhone());
        userModel.setWebsite(user.getWebsite());
        userModel.setExtId(user.getExtId());

        if(user.getAddress() != null) {
            userModel.setAddress(Address.toAddressModel(user.getAddress()));
        }

        return userModel;
    }
}
