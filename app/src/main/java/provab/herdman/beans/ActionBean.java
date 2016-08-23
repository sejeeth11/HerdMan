package provab.herdman.beans;

import java.util.ArrayList;

/**
 * Created by ptblr1035 on 29/7/16.
 */
public class ActionBean {

String idno;
    String farmercode;
    String phoneno;
    String farmername;
    String task;
    String idno1;
    String orderno;
    String lotno;

    public ArrayList<String> getInsertid() {
        return insertid;
    }

    public void setInsertid(ArrayList<String> insertid) {
        this.insertid = insertid;
    }

    ArrayList<String> insertid;

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getFarmercode() {
        return farmercode;
    }

    public void setFarmercode(String farmercode) {
        this.farmercode = farmercode;
    }

    public String getFarmername() {
        return farmername;
    }

    public void setFarmername(String farmername) {
        this.farmername = farmername;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getIdno1() {
        return idno1;
    }

    public void setIdno1(String idno1) {
        this.idno1 = idno1;
    }

    public String getLotno() {
        return lotno;
    }

    public void setLotno(String lotno) {
        this.lotno = lotno;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }
}
