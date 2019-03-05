package sqlproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sqlproject.services.ContactService;
import java.util.concurrent.atomic.AtomicLong;

// DispatcherServlet получает HTTP-запрос, обращается к интерфейсу HandlerMapping,
// который определяет Контроллер для вызова

@RestController
@RequestMapping("/api/1.0/contacts")
public class WebController {

    @Autowired
    private ContactService contactService;

    private final AtomicLong counter = new AtomicLong();

    //  POST request, добавляет контакт в БД
//    @PostMapping(value = "/add")
    @GetMapping(value = "/add")
    public Long addContact(@RequestParam(value="name", defaultValue="defaultValue") String name,
                           @RequestParam(value="age", defaultValue="0") Integer age,
                           @RequestParam(value="content", defaultValue="defaultValue") String content) {

/*  информация контакта (name, surname, age) передается в теле POST-запроса,
    в ответ приходит уникальный идентификатор контакта ID
    КАК ПОЛУЧИТЬ ТЕЛО ЗАПРОСА ???
 */

        //  записать контакт (name, age, content) в БД и вернуть ID контакта
        Contact contact = new Contact(name, age, content);
        return contactService.add(contact);
    }

    //  GET request, выдает список из первых count контактов
    @GetMapping(value = "/get")
    public String getArrayContacts(@RequestParam(value="count", defaultValue="0") int count) {
        return "You requested " + count + " contacts";
    }

    //  GET request, выдает информацию о контакте по id
    @GetMapping(value = "/{webid:\\d{1,5}}/{name}")
    public String handleContact(@PathVariable("webid") int id, @PathVariable("name") String name) {
        return contactService.getInfo(id, name);
    }

    // DELETE request, удаляет контакт из БД по id
//    @DeleteMapping(value = "/{id:\\d{1,5}}/delete")
    @GetMapping(value = "/{id:\\d{1,5}}/delete")
    public String deleteContact(@PathVariable("id") int id) {
        return contactService.delete(id);
    }

    //  default GET request, для Error 404, ???

}
