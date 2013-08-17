package vn.mangtuyendung.form;

public class SearchForm {

    private String stext;
    private String slocation;

    public String getStext() {
        if (this.stext != null) {
            this.stext = this.stext.replace('"', ' ');
            this.stext = this.stext.replaceAll("\\+", " ");
            this.stext = this.stext.replaceAll("  ", " ");
        }
        return this.stext;
    }

    public void setStext(String stext) {
        this.stext = stext;
    }

    public String getSlocation() {
        if (this.slocation != null) {
            this.slocation = this.slocation.replaceAll("\\+", " ");
        }
        return this.slocation;
    }

    public void setSlocation(String slocation) {
        this.slocation = slocation;
    }
}