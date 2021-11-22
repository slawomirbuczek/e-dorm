package com.edorm.controllers.ping;

import com.edorm.controllers.RestEndpoint;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RestEndpoint.PING)
public class PingController {

    @GetMapping
    public void ping() {
    }

}
