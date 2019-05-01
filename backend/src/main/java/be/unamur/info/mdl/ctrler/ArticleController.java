package be.unamur.info.mdl.ctrler;

import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.ArticleService;
import be.unamur.info.mdl.service.exceptions.ArticleAlreadyExistException;
import be.unamur.info.mdl.service.exceptions.ArticleNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/article")
@Api(value = "Article endpoints", description = "Operations related to an Article such as add, get, delete ...")
public class ArticleController extends APIBaseController {

  @Autowired
  private ArticleService articleService;


  @ApiOperation(value = "Register a new article")
  @ApiResponses(value = {
    @ApiResponse(code = 201, message = "Successfully registered"),
    @ApiResponse(code = 400, message = "Some required fields are invalid or missing"),
    @ApiResponse(code = 409, message = "If the article already exists", response = String.class)
  })
  @RequestMapping(path = {"", "/add"}, method = RequestMethod.POST)
  public ResponseEntity<?> create(@Valid @RequestBody ArticleDTO articleData, Principal authUser) {
    try {
      String username = authUser.getName();
      UserDTO currentUser = new UserDTO();
      currentUser.setUsername(username);
      articleService.create(articleData, currentUser);
    } catch (ArticleAlreadyExistException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
    return new ResponseEntity<>(articleData, HttpStatus.CREATED);
  }


  @ApiOperation(value = "Retrieve a specific article by it reference")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully registered", response = ArticleDTO.class),
    @ApiResponse(code = 400, message = "The article reference is missing "),
    @ApiResponse(code = 404, message = "The provided reference doesn't exist")
  })
  @RequestMapping(path = "/{reference}", method = RequestMethod.GET)
  public ResponseEntity<?> get(@Valid @PathVariable String reference) {
    try {
      ArticleDTO articleData = articleService.getArticleByReference(reference);
      return new ResponseEntity<>(articleData, HttpStatus.OK);
    } catch (ArticleNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  @ApiOperation(value = "Retrieve a list of article by their references")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "List of the matching articles", response = ArticleDTO[].class),
    @ApiResponse(code = 400, message = "The list of provided references is empty"),
    @ApiResponse(code = 404, message = "The provided reference doesn't exist")
  })
  @GetMapping(name = "GET_articles_by_references", path = "", params = "reference")
  public List<ArticleDTO> listByReferences(
    @RequestParam(name = "reference") List<@NotBlank(message = "The reference must be defined") String> references) {
    return articleService.listArticleByReferences(references);
  }


  @ApiOperation(value = "Retrieve a list of article by their categories")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "List of the matching articles"),
  })
  @GetMapping(name = "GET_articles_by_categories",path = "", params = "category")
  public Map<String, List<ArticleDTO>> listByCategories(
    @RequestParam(name = "category") List<String> categories) {
    Map<String, List<ArticleDTO>> articles = articleService
      .listArticleByCategories(categories);
    return articles;
  }

}
