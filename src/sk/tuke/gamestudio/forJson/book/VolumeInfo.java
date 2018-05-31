
package sk.tuke.gamestudio.forJson.book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VolumeInfo {

    public String title;
    public List<String> authors = null;
    public String publisher;
    public String publishedDate;
    public String description;
    public String maturityRating;
    public boolean allowAnonLogging;
    public String contentVersion;
    public String previewLink;
    public String infoLink;
    public String canonicalVolumeLink;
    public String subtitle;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public VolumeInfo() {
    }

    public VolumeInfo(String title, List<String> authors, String publisher, String publishedDate, String description,String maturityRating, boolean allowAnonLogging, String contentVersion,  String previewLink, String infoLink, String canonicalVolumeLink, String subtitle) {
        super();
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.maturityRating = maturityRating;
        this.allowAnonLogging = allowAnonLogging;
        this.contentVersion = contentVersion;
        this.previewLink = previewLink;
        this.infoLink = infoLink;
        this.canonicalVolumeLink = canonicalVolumeLink;
        this.subtitle = subtitle;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
