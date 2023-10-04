package org.examples;

import org.examples.service.VideoStreamService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequestMapping("/video")
public class VideoController {

    private final VideoStreamService videoStreamService;

    public VideoController(VideoStreamService videoStreamService) {
        this.videoStreamService = videoStreamService;
    }

    @GetMapping("/{fileName}")
    public Mono<ResponseEntity<?>> streamVideo(@RequestHeader(value = "Range", required = false) String httpRangeList,
                                                                   @PathVariable("fileName") String fileName) throws IOException {
        return Mono.just(videoStreamService.prepareContent(fileName, "mp4", httpRangeList));
    }
}
