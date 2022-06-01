package com.rhisco.book;
import com.rhisco.book.Models.User;
import com.rhisco.book.Repositorys.UserRepository;
import com.rhisco.book.Services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestUserService {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testAddUser() {
        User user = new User(1L,"Juan", "Perez");
        assert userService.addUser(user) == false;
    }
}
