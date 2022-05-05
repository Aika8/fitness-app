package spring.first.fitness;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import spring.first.fitness.controllers.PostController;
import spring.first.fitness.services.impl.PostServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class FitnessApplicationTests {

    @Autowired
    private PostController postController;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(postController);
    }

}
