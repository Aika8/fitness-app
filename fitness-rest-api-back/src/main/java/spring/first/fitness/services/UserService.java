package spring.first.fitness.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.first.fitness.dto.PasswordDTO;
import spring.first.fitness.dto.UserDTO;
import spring.first.fitness.security.oauth2.UserPrincipal;

public interface UserService {

    UserDTO getUserByEmail(String email);

    UserDTO updateUser(UserDTO user);

    UserDTO getUser(Long id);

    void deleteUser(Long id);

    void updatePassword(UserPrincipal userPrincipal, PasswordDTO passwordDTO);

    UserDTO getCurrentUser(UserPrincipal userPrincipal);

    Page<UserDTO> getAllUsers(Pageable pageable);

    void becomeAdmin(UserPrincipal userPrincipal);

}
