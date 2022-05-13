package spring.first.fitness.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.first.fitness.util.DateUtil;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    private String message;

    @Column(name = "creation_date")
    @JsonFormat(pattern = DateUtil.DEFAULT_DATE_PATTERN)
    private LocalDateTime creationDate;
}
