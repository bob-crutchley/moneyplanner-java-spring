package org.bob.moneyplanner.java.spring.model.persistence;

import org.bob.moneyplanner.java.spring.model.Model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Account extends Model {
    @Id
    @GeneratedValue
    private@Column Long id;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String forename;
    @Column
    private String surname;
    @Column
    private boolean accountActivated;
    @Column(length = 1024)
    private String authToken;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isAccountActivated() {
        return accountActivated;
    }

    public void setAccountActivated(boolean accountActivated) {
        this.accountActivated = accountActivated;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String sessionToken) {
        this.authToken = sessionToken;
    }

    public Map<String, String> asHashMap(){
        Map<String, String> map = new HashMap<>();
        map.put("forename", forename);
        map.put("surname", surname);
        map.put("email", email);
        map.put("authToken", authToken);
        return map;
    }
}
