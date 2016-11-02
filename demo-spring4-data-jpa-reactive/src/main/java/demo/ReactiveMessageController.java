package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("message/reactive")
public class ReactiveMessageController {

    @Autowired
    private ReactiveMessageService reactiveMessageService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Flux<Message> findAll() {
        return reactiveMessageService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Mono<Message> findById(@PathVariable("id") long id) {
        Mono<Message> messageOpt = reactiveMessageService.findById(id);
        return messageOpt.mapError(e -> new MessageNotFoundException(id));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Message> create(@Valid @RequestBody Message message) {
        return reactiveMessageService.save(Mono.just(message));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleMessageNotFoundException(MessageNotFoundException e) {
    }
}
