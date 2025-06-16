
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.devsenior.pablo.exception.UserNotFoundException;
import com.devsenior.pablo.model.User;
import com.devsenior.pablo.service.UserService;

public class UserServiceTest {
    private UserService userService;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp(){
        userService = new UserService();
    }

    @Test
    void testAddUser(){
        //GIVEN
        var id = "1";
        var name = "Juan Pablo";
        var user = new User(id, name);
        
        //WHEN
        userService.addUser(user);

        //THEN
        assertEquals(userService.getUserbyId(id).getName(), name);
    }

    @Test
    void testDeleteUser(){
        //GIVEN
        var id = "1";
        var name = "Juan Pablo";
        var user = new User(id, name);
        

        //WHEN
        userService.deleteUser(user);
        
        //THEN
        assertEquals(true, userService.getUsers().isEmpty());
    }

    @Test
    void testGetUserById(){
        //GIVEN
        testAddUser();
        //WHEN - THEN
        var id = "2";
        try {
            var user = userService.getUserbyId(id);  
                assertEquals(id, user.getId());  
        } catch (UserNotFoundException e) {
        
            assertTrue(true);
        }
        
        
        
        
    }
}
