package bw.com.yunifangstore.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * @author : 张鸿鹏
 * @date : 2016/12/24.
 */
@Table(name = "USERTABLE")
public class UserBean implements Serializable{
    //id
    @Column(isId = true, name = "ID", autoGen = true)
    private int id;
    @Column(name = "USERNAME")
    private String userName;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "PROVINCE")
    private String province;
    @Column(name = "DETAILADDRESS")
    private String detailAddress;
    @Column(name = "FLAG")
    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }



    public UserBean(String userName, String phone, String province, String detailAddress, boolean flag) {
        this.userName = userName;
        this.phone = phone;
        this.province = province;
        this.detailAddress = detailAddress;
        this.flag = flag;
    }

    public UserBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }
}
