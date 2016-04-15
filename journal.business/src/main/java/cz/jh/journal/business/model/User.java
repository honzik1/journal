package cz.jh.journal.business.model;

import cz.jh.journal.model.DBEntity;
import cz.jh.journal.rest.view.View;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonView;

/**
 * @author jan.horky
 * @version 1.0
 * @created 09-4-2016 17:39:59
 */
@Entity
@XmlRootElement
public class User extends DBEntity<Long> {

    @Enumerated(EnumType.STRING)
    @JsonView(View.Detail.class)
    private UserRole role;
    @JsonView({View.Summary.class,View.Detail.class})
    private String firstName;
    @JsonView({View.Summary.class,View.Detail.class})
    private String lastName;
    @Column(unique = true)
    @JsonView({View.Summary.class,View.Detail.class})
    private String email;
    @XmlTransient
    @JsonIgnore
    private String password;
    @JsonView(View.Detail.class)
    private boolean active;

    public User() {
        this.active = true;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
