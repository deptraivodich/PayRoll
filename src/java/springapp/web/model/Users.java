package springapp.web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users") 
public class Users implements java.io.Serializable {

    // SỬA: Đặt User_Name làm Khóa Chính (@Id) luôn
    @Id
    @Column(name = "User_Name", unique = true, nullable = false)
    private String userName;

    @Column(name = "Password")
    private String password;

    public Users() {
    }

    public Users(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    // --- BỎ HẾT GETTER/SETTER CỦA ID ---
    
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}