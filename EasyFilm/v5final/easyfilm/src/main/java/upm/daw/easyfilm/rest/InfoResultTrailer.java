package upm.daw.easyfilm.rest;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "results"
})
public class InfoResultTrailer {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("results")
    private List<TrailerInfo> results = null;

    public InfoResultTrailer() {}

    public InfoResultTrailer(Integer id, List<TrailerInfo> results) {
        super();
        this.id = id;
        this.results = results;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("results")
    public List<TrailerInfo> getResults() {
        return results;
    }

    @JsonProperty("results")
    public void setResults(List<TrailerInfo> results) {
        this.results = results;
    }

}