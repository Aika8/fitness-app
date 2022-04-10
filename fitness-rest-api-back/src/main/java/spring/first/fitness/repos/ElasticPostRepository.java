package spring.first.fitness.repos;



import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import spring.first.fitness.entity.ElasticPost;

import java.util.Optional;

@Repository
public interface ElasticPostRepository extends ElasticsearchRepository<ElasticPost, Long> {
    Optional<ElasticPost> findByPostId(Long id);
}
