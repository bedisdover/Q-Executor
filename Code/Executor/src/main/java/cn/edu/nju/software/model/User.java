package cn.edu.nju.software.model;



import javax.persistence.*;

/**
 * Created by Jiayiwu on 16/7/29.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
@Entity
@Table(name = "user")
public class User {

    private int ID;
    private String userName;
    private String nickName;
    private String password;
    private String mail;

    public User() {
    }

    public User( String userName, String nickName, String password, String mail) {

        this.userName = userName;
        this.nickName = nickName;
        this.password = password;
        this.mail = mail;
    }
    @Id
    @GeneratedValue
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    @Column(length = 20)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
