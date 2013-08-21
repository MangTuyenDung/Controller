package vn.mangtuyendung.form;

public class SearchQuickForm {

    private String ftext;
    private String flocation;
    private int pageNumber = 1;
    private String[] flocations;
    private String[] feducations;
    private String[] fexperiences;

    public String getFtext() {
        if (this.ftext != null) {
            this.ftext = this.ftext.replace('"', ' ');
            this.ftext = this.ftext.replaceAll("\\+", " ");
            this.ftext = this.ftext.replaceAll("  ", " ");
        }
        return this.ftext;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setFtext(String ftext) {
        this.ftext = ftext;
    }

    public String getFlocation() {
        if (this.flocation != null) {
            this.flocation = this.flocation.replaceAll("\\+", " ");
        }
        return this.flocation;
    }

    public void setFlocation(String flocation) {
        this.flocation = flocation;
    }

    public String[] getFlocations() {
        return flocations;
    }

    public void setFlocations(String[] flocations) {
        this.flocations = flocations;
    }

    public String[] getFeducations() {
        return feducations;
    }

    public void setFeducations(String[] feducations) {
        this.feducations = feducations;
    }

    public String[] getFexperiences() {
        return fexperiences;
    }

    public void setFexperiences(String[] fexperiences) {
        this.fexperiences = fexperiences;
    }
}
