package spring.first.fitness.repos;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;
import spring.first.fitness.entity.Users;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<Users, Long> {

    @Query("select u from Users u join fetch u.role where u.email = ?1")
    Optional<Users> findByEmail(String email);

    @Query(value = "SELECT u FROM Users u JOIN FETCH u.role ORDER BY u.id",
            countQuery = "SELECT count(u) FROM Users u")
    Page<Users> findAllUsers(Pageable pageable);

    Boolean existsByEmail(String email);

    @Query("select u from Users u join fetch u.role where u.id = ?1")
    Optional<Users> findByUserId(Long id);
}
