package IRCI;

import edu.uci.ics.jung.samples.ClusteringDemo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Article {
    String title;
    String abstr;
    String author;
    String year;
    String keyword;
    String references;
    String authref;
    Integer referredtimes;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstr() {
        return abstr;
    }

    public void setAbstr(String abstr) {
        this.abstr = abstr;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getReferences() {
        return references;
    }

    public void setReferences(String references) {
        this.references = references;
    }

    public String getAuthref() {
        return authref;
    }

    public void setAuthref(String authref) {
        this.authref = authref;
    }

    public Integer getReferredtimes() {
        return referredtimes;
    }

    public void setReferredtimes(Integer referredtimes) {
        this.referredtimes = referredtimes;
    }
}
