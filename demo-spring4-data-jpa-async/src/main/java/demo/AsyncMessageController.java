package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("message/async")
public class AsyncMessageController {

    @Autowired
    private AsyncMessageRepository asyncMessageRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody DeferredResult<List<Message>> findAll() {
        return toDeferredResult(asyncMessageRepository.findAllAsFuture());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Message findById(@PathVariable("id") long id) {
        Optional<Message> messageOpt = asyncMessageRepository.findById(id);
        return messageOpt.orElseThrow(() -> new MessageNotFoundException(id));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Message create(@Valid @RequestBody Message message) {
        return asyncMessageRepository.save(message);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleMessageNotFoundException(MessageNotFoundException e) {
    }

    private DeferredResult<List<Message>> toDeferredResult(CompletableFuture<List<Message>> future) {
        DeferredResult<List<Message>> deferred = new DeferredResult<>();
        future.whenComplete((result, error) -> {
            if (error != null) {
                deferred.setErrorResult(error);
            } else {
                deferred.setResult(result);
            }
        });
        return deferred;
    }
}
