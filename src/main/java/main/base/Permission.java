package main.base;

public enum Permission {
    USER("user:wright"),
    MODERATE("user:moderate");

    private final String permission;

    Permission(String permission){
        this.permission = permission;
    }

    public String getPermission(){
        return permission;
    }
}
