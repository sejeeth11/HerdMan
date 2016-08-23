package provab.herdman.beans;

/**
 * Created by PTBLR-1057 on 5/25/2016.
 */
public class UserInfo {

    String uid;
    String password;
    String groop;
    String herd;
    String companycode;
    String apptype;
    String updatedby;
    String updatedat;
    String usercode;
    boolean qrcode;

    public UserInfo(String uid, String password, String groop, String herd, String companycode, String apptype, String updatedby, String updatedat, String usercode, boolean qrcode) {
        this.uid = uid;
        this.password = password;
        this.groop = groop;
        this.herd = herd;
        this.companycode = companycode;
        this.apptype = apptype;
        this.updatedby = updatedby;
        this.updatedat = updatedat;
        this.usercode = usercode;
        this.qrcode = qrcode;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGroop(String groop) {
        this.groop = groop;
    }

    public void setHerd(String herd) {
        this.herd = herd;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode;
    }

    public void setApptype(String apptype) {
        this.apptype = apptype;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public void setUpdatedat(String updatedat) {
        this.updatedat = updatedat;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public void setQrcode(boolean qrcode) {
        this.qrcode = qrcode;
    }

    public String getUid() {
        return uid;
    }

    public String getUsercode() {
        return usercode;
    }

    public String getUpdatedat() {
        return updatedat;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public String getApptype() {
        return apptype;
    }

    public String getCompanycode() {
        return companycode;
    }

    public String getHerd() {
        return herd;
    }

    public String getGroop() {
        return groop;
    }

    public String getPassword() {
        return password;
    }

    public boolean getQrcode() {
        return qrcode;
    }
}
