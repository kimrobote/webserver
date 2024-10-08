package kr.co.kim.model;

import org.apache.commons.lang3.StringUtils;

public class User {

    private String id;
    private String password;
    private String name;
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean vailidateInputDate() {
        return (!StringUtils.isEmpty(id) && !StringUtils.isEmpty(password)
                && !StringUtils.isEmpty(name) && !StringUtils.isEmpty(email));
    }

}
