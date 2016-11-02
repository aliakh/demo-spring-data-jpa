package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("message/sync")
public class SyncMessageController {

    @Autowired
    private SyncMessageRepository syncMessageRepository;

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody long count() {
        return syncMessageRepository.count();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Message> findAll() {
        return syncMessageRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Message findById(@PathVariable("id") long id) {
        Optional<Message> messageOpt = syncMessageRepository.findById(id);
        return messageOpt.orElseThrow(() -> new MessageNotFoundException(id));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Message create(@Valid @RequestBody Message message) {
        return syncMessageRepository.save(message);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Message update(@PathVariable("id") long id, @RequestBody String text) {
        Optional<Message> messageOpt = syncMessageRepository.findById(id);
        return messageOpt.map(message -> {
                    message.setText(text);
                    return syncMessageRepository.save(message);
                }
        ).orElseThrow(() -> new MessageNotFoundException(id));
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteAll() {
        syncMessageRepository.deleteAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void deleteById(@PathVariable("id") long id) {
        syncMessageRepository.delete(id);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleMessageNotFoundException(MessageNotFoundException e) {
    }
}
