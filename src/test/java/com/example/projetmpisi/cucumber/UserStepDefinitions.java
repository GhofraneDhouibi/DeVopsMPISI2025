package com.example.projetmpisi.cucumber;

import com.example.projetmpisi.entity.User;
import com.example.projetmpisi.repository.UserRepository;
import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration
public class UserStepDefinitions {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @Given("the user details are")
    public void the_user_details_are(io.cucumber.datatable.DataTable dataTable) {
        var data = dataTable.asMaps().get(0);
        user = new User(
                Integer.parseInt(data.get("id")),
                data.get("username"),
                data.get("email")
        );
    }

    @When("the user is created")
    public void the_user_is_created() {
        userRepository.save(user);
    }

    @Then("the user should be successfully created with username {string}")
    public void the_user_should_be_successfully_created_with_username(String username) {
        User savedUser = userRepository.findById(user.getId()).orElse(null);
        assertNotNull(savedUser);
        assertEquals(username, savedUser.getUsername());
    }
}
