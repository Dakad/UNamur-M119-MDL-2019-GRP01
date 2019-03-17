package be.unamur.info.mdl.ctrler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import be.unamur.info.mdl.dal.repository.UserRepository;
import be.unamur.info.mdl.service.exceptions.InvalidCredentialException;
import be.unamur.info.mdl.service.exceptions.RegistrationException;
import be.unamur.info.mdl.service.impl.UserServiceImpl;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

  @Autowired
  private MockMvc api;

  @MockBean
  private UserServiceImpl userService;

  @MockBean
  private UserRepository userDAO;


  private static final String LOGIN_URL = "/api/login";

  private static final String REGISTER_URL = "/api/register";


  @Test
  public void register_with_null_data() throws Exception {
    JSONObject newUser = new JSONObject();
    api.perform(MockMvcRequestBuilders.post(REGISTER_URL)
        .content(newUser.toString())
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }


  @Test
  public void register_with_missing_required_data() throws Exception {
    JSONObject newUser = new JSONObject();
    newUser.put("username","");
    newUser.put("password", "");
    newUser.put("email", "");

    api.perform(post(REGISTER_URL)
        .content(newUser.toString())
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
//        .andExpect(content().json("Yello Greetings from Spring Boot!"))
    ;

    JSONObject newUser2 = new JSONObject();
    newUser2.put("username","correct_username");
    newUser2.put("password", "");
    newUser2.put("email", "");

    api.perform(post(REGISTER_URL)
        .content(newUser2.toString())
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
//        .andExpect(jsonPath("$.validation.password", Is.is("The username cannot be empty or blank")))
    ;


    JSONObject newUser3 = new JSONObject();
    newUser3.put("username","correct_username");
    newUser3.put("password", "incorrect_password");
    newUser3.put("email", "");

    api.perform(post(REGISTER_URL)
        .content(newUser3.toString())
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
//        .andExpect(jsonPath("$.validation.username", Is.is("The password cannot be empty or blank")))
    ;

    JSONObject newUser4 = new JSONObject();
    newUser4.put("username","correct_username");
    newUser4.put("password", "correct_password_123");
    newUser4.put("email", "");

    api.perform(post(REGISTER_URL)
        .content(newUser4.toString())
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
//        .andExpect(jsonPath("$.validation.username", Is.is("The password cannot be empty or blank")))
    ;


    JSONObject newUser5 = new JSONObject();
    newUser5.put("username","correct_username");
    newUser5.put("password", "correct_password_123");
    newUser5.put("email", "");

    api.perform(post(REGISTER_URL)
        .content(newUser5.toString())
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
//        .andExpect(jsonPath("$.validation.username", Is.is("The password cannot be empty or blank")))
    ;
  }


  @Test
  public void register_with_taken_username() throws Exception {
    JSONObject newUser = new JSONObject();
    newUser.put("username","already_taken");
    newUser.put("password", "my_PWD_123");
    newUser.put("email", "new_user@mail.dom");

    when(userService.register(any())).thenThrow(new RegistrationException("Username already taken."));

    api.perform(post(REGISTER_URL)
        .content(newUser.toString())
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.error").isNotEmpty());
  }


  @Test
  public void register_with_taken_email() throws Exception {
    JSONObject newUser = new JSONObject();
    newUser.put("username","user_name");
    newUser.put("password", "my_PWD_123");
    newUser.put("email", "already_taken@mail.dom");

    when(userService.register(any())).thenThrow(new RegistrationException("Email already taken."));

    api.perform(post(REGISTER_URL)
        .content(newUser.toString())
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.error").isNotEmpty());
  }


  @Test
  public void register_with_taken_ok() throws Exception {
    JSONObject newUser = new JSONObject();
    newUser.put("username","user_name");
    newUser.put("password", "my_PWD_123");
    newUser.put("email", "already_taken@mail.dom");

    when(userService.register(any())).thenReturn(true);

    api.perform(post(REGISTER_URL)
        .content(newUser.toString())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.success").isNotEmpty());
  }


  @Test
  public void register_with_invalid_credentials() throws Exception {
    JSONObject credential = new JSONObject();
    credential.put("username","invalid_user_name");
    credential.put("password", "invalid_Pwd_123");

    when(userService.login(any())).thenThrow(InvalidCredentialException.class);

    api.perform(post(LOGIN_URL)
        .content(credential.toString())
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict())
    ;
  }

  @Test
  public void register_with_credentials() throws Exception {
    JSONObject credential = new JSONObject();
    credential.put("username","correct_user_name");
    credential.put("password", "correct_Pwd_123");

    when(userService.login(any())).thenReturn("JWT_TEST_TOKEN");

    api.perform(post(LOGIN_URL)
        .content(credential.toString())
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.auth_token").exists())
        .andExpect(jsonPath("$.auth_token").value("JWT_TEST_TOKEN"))
    ;
  }


  @Test
  public void login_with_null_credentials() throws Exception {
    JSONObject credential = new JSONObject();
    api.perform(MockMvcRequestBuilders.request(HttpMethod.POST, LOGIN_URL, credential)
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void login_with_empty_credentials() throws Exception {
    JSONObject credential = new JSONObject();
    credential.put("username","");
    credential.put("password", "");

    api.perform(MockMvcRequestBuilders.post(LOGIN_URL)
        .content(credential.toString())
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
//        .andExpect(content().json("Yello Greetings from Spring Boot!"))
    ;

    JSONObject credential2 = new JSONObject();
    credential.put("username","correct_username");
    credential.put("password", "");

    api.perform(MockMvcRequestBuilders.post(LOGIN_URL)
        .content(credential2.toString())
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
//        .andExpect(jsonPath("$.validation.password", Is.is("The username cannot be empty or blank")))
    ;


    JSONObject credential3 = new JSONObject();
    credential.put("username","");
    credential.put("password", "correct_password");
    api.perform(MockMvcRequestBuilders.post(LOGIN_URL)
        .content(credential3.toString())
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
//        .andExpect(jsonPath("$.validation.username", Is.is("The password cannot be empty or blank")))
    ;
  }


  @Test
  public void login_with_unknown_credentials() throws Exception {
    JSONObject credential = new JSONObject();
    credential.put("username","no_user");
    credential.put("password", "no_password");

    api.perform(MockMvcRequestBuilders.post(LOGIN_URL)
        .content(credential.toString())
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
//        .andExpect(content().json("Yello Greetings from Spring Boot!"))
    ;
  }


  @Test
  public void login_with_invalid_credentials() throws Exception {
    JSONObject credential = new JSONObject();
    credential.put("username","invalid_user_name");
    credential.put("password", "invalid_Pwd_123");

    when(userService.login(any())).thenThrow(InvalidCredentialException.class);

    api.perform(MockMvcRequestBuilders.post(LOGIN_URL)
        .content(credential.toString())
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict())
    ;
  }

  @Test
  public void login_with_credentials() throws Exception {
    JSONObject credential = new JSONObject();
    credential.put("username","correct_user_name");
    credential.put("password", "correct_Pwd_123");

    when(userService.login(any())).thenReturn("JWT_TEST_TOKEN");

    api.perform(MockMvcRequestBuilders.post(LOGIN_URL)
        .content(credential.toString())
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.auth_token").exists())
        .andExpect(jsonPath("$.auth_token").value("JWT_TEST_TOKEN"))
    ;
  }



}
