package spring.first.fitness.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeUserDTO {
    private Long id;
    private String name;
    private String imageUrl;
}
