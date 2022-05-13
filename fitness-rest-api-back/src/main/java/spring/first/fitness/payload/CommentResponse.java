package spring.first.fitness.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {

    private Long id;
    private String userEmail;
    private String message;
    private LocalDateTime creationDate;
}
