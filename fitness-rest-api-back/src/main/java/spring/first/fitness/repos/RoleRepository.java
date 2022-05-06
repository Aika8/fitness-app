package spring.first.fitness.repos;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.first.fitness.entity.Role;

import javax.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String role);
    List<Role> findAllByOrderByWeight();
}
