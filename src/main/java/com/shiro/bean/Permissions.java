package com.shiro.bean;

/**
 * Created by dell on 2020/9/22.
 */
public class Permissions {

    private String id;
    private String permissionsName;

    public Permissions() {
    }

    public Permissions(String id, String permissionsName) {
        this.id = id;
        this.permissionsName = permissionsName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermissionsName() {
        return permissionsName;
    }

    public void setPermissionsName(String permissionsName) {
        this.permissionsName = permissionsName;
    }

    @Override
    public String toString() {
        return "Permissions{" +
                "id='" + id + '\'' +
                ", permissionsName='" + permissionsName + '\'' +
                '}';
    }
}
