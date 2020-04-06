package com.zhouchen.mall.bean.admin;

/**
 * User：zhouchen
 * Time: 2020/3/29  22:14
 * Description:
 */
public class Admin {
    //管理员id
    private Integer id;
    //管理员邮箱
    private String email;
    //管理员昵称
    private String nickname;
    //管理员密码
    private String pwd;

    private Integer level;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", pwd='" + pwd + '\'' +
                ", level=" + level +
                '}';
    }
}
