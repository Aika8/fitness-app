package spring.first.fitness.repos;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.first.fitness.entity.Post;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {
        Page<Post> findAllByOrderByPriorityAsc(Pageable pageable);
}
