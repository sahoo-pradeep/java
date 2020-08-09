package projects.sahoo.myspringboot.models.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorMessage {
    private int code;
    private String message;
    private LocalDateTime timestamp;
}
