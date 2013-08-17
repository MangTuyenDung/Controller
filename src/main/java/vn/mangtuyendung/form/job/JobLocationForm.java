package vn.mangtuyendung.form.job;

public class JobLocationForm {

    private long jobId;
    private String[] jobLocations;

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String[] getJobLocations() {
        return jobLocations;
    }

    public void setJobLocations(String[] jobLocations) {
        this.jobLocations = jobLocations;
    }
}
