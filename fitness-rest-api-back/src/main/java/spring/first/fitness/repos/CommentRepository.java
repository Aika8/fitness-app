package spring.first.fitness.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.first.fitness.entity.Comment;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {
            List<Comment> findAllByPostId(Long id);
}
