package provab.herdman.constants;

/**
 * Created by ptblr1035 on 3/8/16.
 */
public class AnimalDetailsData {


  String LotCode,
          OwnerCode,
          OwnerName,
          Age,
          BreedName,
          Status,
          BreedingStatus,
          OpenPeriod,
          DryDays,
          MilkYield,
          AvgYield,
          PeakYield,image,statuspic;

    public String getStatuspic() {
        return statuspic;
    }

    public void setStatuspic(String statuspic) {
        this.statuspic = statuspic;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLotCode() {
        return LotCode;
    }

    public void setLotCode(String lotCode) {
        LotCode = lotCode;
    }

    public String getOwnerCode() {
        return OwnerCode;
    }

    public void setOwnerCode(String ownerCode) {
        OwnerCode = ownerCode;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getBreedName() {
        return BreedName;
    }

    public void setBreedName(String breedName) {
        BreedName = breedName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getBreedingStatus() {
        return BreedingStatus;
    }

    public void setBreedingStatus(String breedingStatus) {
        BreedingStatus = breedingStatus;
    }

    public String getOpenPeriod() {
        return OpenPeriod;
    }

    public void setOpenPeriod(String openPeriod) {
        OpenPeriod = openPeriod;
    }

    public String getDryDays() {
        return DryDays;
    }

    public void setDryDays(String dryDays) {
        DryDays = dryDays;
    }

    public String getMilkYield() {
        return MilkYield;
    }

    public void setMilkYield(String milkYield) {
        MilkYield = milkYield;
    }

    public String getAvgYield() {
        return AvgYield;
    }

    public void setAvgYield(String avgYield) {
        AvgYield = avgYield;
    }

    public String getPeakYield() {
        return PeakYield;
    }

    public void setPeakYield(String peakYield) {
        PeakYield = peakYield;
    }




    private AnimalDetailsData() {
    }
}
