package projects.sahoo.myspringboot.controllers.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PreviousCompanyRequest {
    private String globalId;
    private String companyName;
    private LocalDate startDate;
    private LocalDate endDate;
}
