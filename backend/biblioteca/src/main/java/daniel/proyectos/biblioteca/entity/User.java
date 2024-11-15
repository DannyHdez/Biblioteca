package daniel.proyectos.biblioteca.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "usuarios")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surnames;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "registration_date") //registration date
    @Temporal(TemporalType.DATE)
    private Date registrationDate;

    @Column(name = "deactivation_date")
    @Temporal(TemporalType.DATE)
    private Date deactivationDate;

    @Column(name = "user_type", nullable = false)
    private String userType;

    @Column(name = "account_disabled", nullable = false)
    private boolean accountDisabled = false;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore //To avoid infinite recursion
    private List<Book> books;

    // Methods of the UserDetails interface
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // You can return roles here if you have a role entity. For future reference
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //The account will be locked if `accountDisabled` is true
        return !accountDisabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return deactivationDate == null && !accountDisabled; // Disabled if there is a withdrawal date or it is disabled
    }
}
