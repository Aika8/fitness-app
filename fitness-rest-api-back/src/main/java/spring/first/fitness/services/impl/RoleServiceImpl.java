package spring.first.fitness.services.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.first.fitness.entity.Role;
import spring.first.fitness.repos.RoleRepository;
import spring.first.fitness.services.RoleService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role getRole(Long id) {

        Role role = roleRepository.findById(id).orElse(null);
        if (role != null) {
            log.info("role: " + role.getName());
        }

        return role;
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> roles = roleRepository.findAllByOrderByWeight();
        log.info("count of roles:" + roles.size());
        return roles;
    }
}
