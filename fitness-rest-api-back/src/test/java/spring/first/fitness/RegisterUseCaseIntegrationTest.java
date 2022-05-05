package spring.first.fitness;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import spring.first.fitness.entity.Users;
import spring.first.fitness.payload.SignUpRequest;
import spring.first.fitness.repos.UserRepository;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class RegisterUseCaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    void registrationWorksThroughAllLayers() throws Exception {
        SignUpRequest user = new SignUpRequest("Zaphod", "zaphod@galaxy.net", "qwerty");

        mockMvc.perform(post("/auth/signup", 42L)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().is(201));

        Optional<Users> userEntity = userRepository.findByEmail("zaphod@galaxy.net");

        Assertions.assertTrue(userEntity.isPresent());
        Assertions.assertEquals("Zaphod", userEntity.get().getName());
    }

    @Test
    void DeleteUser() {
        Optional<Users> userEntity = userRepository.findByEmail("zaphod@galaxy.net");
        Assertions.assertTrue(userEntity.isPresent());
        userRepository.deleteById(userEntity.get().getId());
    }



}
