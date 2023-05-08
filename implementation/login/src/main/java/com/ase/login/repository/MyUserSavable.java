package com.ase.login.repository;

import com.ase.login.data.ERole;
import com.ase.login.data.MyUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class MyUserSavable {

    @Id
    private String id;
    @Column(unique = true)
    private String email;
    private String password;
    private ERole role;

    public MyUserSavable() {
    }

    public MyUserSavable(String id, String email, String password, ERole role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public MyUserSavable(MyUser user) {
        this(user.getId(), user.getEmail(), user.getPassword(), user.getRole());
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public ERole getRole() {
        return role;
    }

    public String getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    public void setId(String id) {
        this.id = id;
    }

}
