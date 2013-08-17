package vn.mangtuyendung.form.profile;

public class PersonInformationForm {

    private long id;
    private String userEncrypt;
    private String personName;
    private int myDate;
    private int myMonth;
    private int myYear;
    private String personSex;
    private String personMarried;
    private String personAddress;
    private String personMobile;
    private String resumeOverview;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserEncrypt() {
        return this.userEncrypt;
    }

    public void setUserEncrypt(String userEncrypt) {
        this.userEncrypt = userEncrypt;
    }

    public int getMyDate() {
        return this.myDate;
    }

    public void setMyDate(int myDate) {
        this.myDate = myDate;
    }

    public int getMyMonth() {
        return this.myMonth;
    }

    public void setMyMonth(int myMonth) {
        this.myMonth = myMonth;
    }

    public int getMyYear() {
        return this.myYear;
    }

    public void setMyYear(int myYear) {
        this.myYear = myYear;
    }

    public String getPersonName() {
        return this.personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonSex() {
        return this.personSex;
    }

    public void setPersonSex(String personSex) {
        this.personSex = personSex;
    }

    public String getPersonMarried() {
        return this.personMarried;
    }

    public void setPersonMarried(String personMarried) {
        this.personMarried = personMarried;
    }

    public String getPersonAddress() {
        return this.personAddress;
    }

    public void setPersonAddress(String personAddress) {
        this.personAddress = personAddress;
    }

    public String getPersonMobile() {
        return this.personMobile;
    }

    public void setPersonMobile(String personMobile) {
        this.personMobile = personMobile;
    }

    public String getResumeOverview() {
        return this.resumeOverview;
    }

    public void setResumeOverview(String resumeOverview) {
        this.resumeOverview = resumeOverview;
    }
}