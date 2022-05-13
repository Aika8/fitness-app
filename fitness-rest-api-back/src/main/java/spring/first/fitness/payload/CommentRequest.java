package spring.first.fitness.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequest {

    private Long id;

    @NotBlank
    private Long postId;

    private String message;
}
