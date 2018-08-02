package io.fouad.hajjhackathon.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "user")
@NamedQuery(
        name="User.getUserByUsername",
        query="SELECT user FROM User user WHERE user.username = :username"
)
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String email;
    private byte[] password;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "registration_datetime")
    private LocalDateTime registrationDatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public LocalDateTime getRegistrationDatetime() {
        return registrationDatetime;
    }

    public void setRegistrationDatetime(LocalDateTime registrationDatetime) {
        this.registrationDatetime = registrationDatetime;
    }
}