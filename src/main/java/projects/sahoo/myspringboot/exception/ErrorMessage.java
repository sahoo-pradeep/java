package projects.sahoo.myspringboot.exception;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorMessage {

  private int code;
  private String message;
  private LocalDateTime timestamp;
}
