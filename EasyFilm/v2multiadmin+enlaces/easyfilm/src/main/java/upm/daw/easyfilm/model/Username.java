package upm.daw.easyfilm.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

public class Username {

    public Username () {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Size(min = 3, max = 60)
    @NotBlank
    private String name;

}
