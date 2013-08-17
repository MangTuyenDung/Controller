package vn.mangtuyendung.form.job;

public class JobCategoryForm {

    private long id;
    private String[] jobCategories;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String[] getJobCategories() {
        return jobCategories;
    }

    public void setJobCategories(String[] jobCategories) {
        this.jobCategories = jobCategories;
    }
}
