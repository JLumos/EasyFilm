package upm.daw.easyfilm.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "iso_639_1",
        "iso_3166_1",
        "key",
        "name",
        "site",
        "size",
        "type"
})
public class TrailerInfo {

    @JsonProperty("id")
    private String id;
    @JsonProperty("iso_639_1")
    private String iso6391;
    @JsonProperty("iso_3166_1")
    private String iso31661;
    @JsonProperty("key")
    private String key;
    @JsonProperty("name")
    private String name;
    @JsonProperty("site")
    private String site;
    @JsonProperty("size")
    private Integer size;
    @JsonProperty("type")
    private String type;

    public TrailerInfo() {}

    public TrailerInfo(String id, String iso6391, String iso31661, String key, String name, String site, Integer size, String type) {
        super();
        this.id = id;
        this.iso6391 = iso6391;
        this.iso31661 = iso31661;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("iso_639_1")
    public String getIso6391() {
        return iso6391;
    }

    @JsonProperty("iso_639_1")
    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    @JsonProperty("iso_3166_1")
    public String getIso31661() {
        return iso31661;
    }

    @JsonProperty("iso_3166_1")
    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    @JsonProperty("key")
    public String getKey() {
        return key;
    }

    @JsonProperty("key")
    public void setKey(String key) {
        this.key = key;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("site")
    public String getSite() {
        return site;
    }

    @JsonProperty("site")
    public void setSite(String site) {
        this.site = site;
    }

    @JsonProperty("size")
    public Integer getSize() {
        return size;
    }

    @JsonProperty("size")
    public void setSize(Integer size) {
        this.size = size;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

}
