package spring.first.fitness.services.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.first.fitness.exceptions.NotFoundException;
import spring.first.fitness.payload.PasswordRequest;
import spring.first.fitness.dto.UserDTO;
import spring.first.fitness.entity.Role;
import spring.first.fitness.entity.Users;
import spring.first.fitness.exceptions.AccessDeniedException;
import spring.first.fitness.exceptions.BadRequestException;
import spring.first.fitness.payload.UserUpdateRequest;
import spring.first.fitness.repos.RoleRepository;
import spring.first.fitness.repos.UserRepository;
import spring.first.fitness.security.oauth2.UserPrincipal;
import spring.first.fitness.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private RoleRepository roleRepository;

    private static final String EMAIL = "email: ";


    @Override
    public UserDTO getUserByEmail(String email) {
        return getUserDto(userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(EMAIL + email)));

    }

    private UserDTO getUserDto(Users user) {
        if (user == null) {
            return null;
        }
        return UserDTO.builder()
                .email(user.getEmail())
                .id(user.getId())
                .role(user.getRole())
                .name(user.getName())
                .imageUrl(user.getImageUrl())
                .build();
    }


    @Override
    public UserDTO updateUser(UserDTO user) {
        Users checkUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new NotFoundException(EMAIL + user.getEmail()));

        checkUser.setName(user.getName());
        checkUser.setImageUrl(user.getImageUrl());
        return getUserDto(userRepository.save(checkUser));
    }

    @Override
    public UserDTO updateRole(UserUpdateRequest user) {
        Users checkUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new NotFoundException(EMAIL + user.getEmail()));

        Role role = Optional.of(roleRepository.findByName(user.getRoleName()))
                .orElseThrow(() -> new NotFoundException(EMAIL + user.getEmail()));
        checkUser.setRole(role);
        return getUserDto(userRepository.save(checkUser));
    }

    @Override
    public void updatePassword(UserPrincipal userPrincipal, PasswordRequest passwordRequest) {
        Users user = userRepository.findByEmail(userPrincipal.getEmail())
                .orElseThrow(() -> new NotFoundException(EMAIL + userPrincipal.getEmail()));

        if (!passwordEncoder.matches(user.getPassword(), passwordRequest.getCurrentPassword())) {
            throw new BadRequestException("current password is wrong");
        }

        user.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
        userRepository.save(user);
    }

    private Role getUserRole() {
        Role role = roleRepository.findByName("ROLE_USER");

        if (role == null) {
            role = Role.builder()
                    .description("registered user")
                    .name("ROLE_USER")
                    .title("registered user")
                    .weight(2)
                    .build();
            roleRepository.save(role);
        }

        return role;
    }


    @Override
    public UserDTO getUser(Long id) {
        return getUserDto(userRepository.findByUserId(id).orElse(null));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO getCurrentUser(UserPrincipal userPrincipal) {
        return getUserDto(userRepository.findByEmail(userPrincipal.getEmail())
                .orElseThrow(() -> new NotFoundException(EMAIL + userPrincipal.getEmail())));
    }

    @Override
    public Page<UserDTO> getAllUsers(Pageable pageable) {

        Page<Users> users = userRepository.findAllUsers(pageable);
        List<UserDTO> dtoList = new ArrayList<>();

        for (Users user : users) {
            dtoList.add(getUserDto(user));
        }

        return new PageImpl<>(dtoList);
    }

    @Override
    public void becomeAdmin(UserPrincipal userPrincipal) {
        Users user = userRepository.findByEmail(userPrincipal.getEmail())
                .orElseThrow(() -> new NotFoundException(EMAIL + userPrincipal.getEmail()));

        if (!user.getEmail().equals("aigerimt922@gmail.com") && !user.getEmail().equals("ertaevamanbai40@gmail.com")) {
            throw new AccessDeniedException("you cannot be admin");
        }

        Role role = roleRepository.findByName("ROLE_ADMIN");
        if (role == null) {
            role = Role.builder()
                    .description("main user")
                    .name("ROLE_ADMIN")
                    .title("admin")
                    .weight(0)
                    .build();
            roleRepository.save(role);
        }

        user.setRole(role);
        userRepository.save(user);
    }

}

