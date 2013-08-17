package vn.mangtuyendung.form.job;

public class JobCompanyForm {

    private long id;
    private long jobId;
    private String companyName;
    private String companyOverview;
    private String companyAddress;
    private String companyRange;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyOverview() {
        return companyOverview;
    }

    public void setCompanyOverview(String companyOverview) {
        this.companyOverview = companyOverview;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyRange() {
        return companyRange;
    }

    public void setCompanyRange(String companyRange) {
        this.companyRange = companyRange;
    }
}
