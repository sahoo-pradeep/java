package projects.sahoo.myspringboot.controllers.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectRequest {
    private String globalId;
    private String projectCode;
    private String projectName;
}
