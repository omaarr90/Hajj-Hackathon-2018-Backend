package io.fouad.hajjhackathon.entity;

import java.util.Objects;

public class LoginBean
{
    private String displayName;
    private String token;

    public LoginBean(String displayName, String token) {
        this.displayName = displayName;
        this.token = token;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginBean loginBean = (LoginBean) o;
        return Objects.equals(displayName, loginBean.displayName) &&
                Objects.equals(token, loginBean.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(displayName, token);
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "displayName='" + displayName + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
