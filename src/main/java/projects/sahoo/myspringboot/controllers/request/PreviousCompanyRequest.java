package projects.sahoo.myspringboot.controllers.request;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreviousCompanyRequest {

  private String globalId;
  private String companyName;
  private LocalDate startDate;
  private LocalDate endDate;

  public PreviousCompanyRequest() {
  }

  public PreviousCompanyRequest(String globalId, String companyName, LocalDate startDate,
      LocalDate endDate) {
    this.globalId = globalId;
    this.companyName = companyName;
    this.startDate = startDate;
    this.endDate = endDate;
  }
}
