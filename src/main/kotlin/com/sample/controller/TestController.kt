import com.sample.repository.TodoRepository
import com.sample.controller.TodoController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.beans.factory.annotation.Autowired

@CrossOrigin(origins = ["http://localhost:4523"])
@RestController
@RequestMapping("/reset")
class TestController(
        @Autowired val todoRepository: TodoRepository
) : TodoController() {
    @PostMapping
    fun reset() {
       return todoRepository.__deleteAll()
    }
}
