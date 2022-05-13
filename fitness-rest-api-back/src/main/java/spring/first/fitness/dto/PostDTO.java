package spring.first.fitness.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.first.fitness.payload.CommentResponse;
import spring.first.fitness.util.DateUtil;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {

    private Long id;
    private String cover;
    private Integer access;
    private Integer priority;
    private String dateOfCreation;
    private Set<LikeUserDTO> users;
    private String title;
    private String brief;
    private String description;
    private List<CommentResponse> comments;

}
