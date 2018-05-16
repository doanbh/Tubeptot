package vn.k2studio.appanhdai.model;

public class UserInfo {
    private String name;
    private String birthday;
    private String hometown;
    private String numberPhone;

    public UserInfo() {
    }

    public UserInfo(String name, String birthday, String hometown, String numberPhone) {
        this.name = name;
        this.birthday = birthday;
        this.hometown = hometown;
        this.numberPhone = numberPhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }
}
