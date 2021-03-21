package com.sinmem.peony.web.controller;

import com.sinmem.peony.dao.bean.ReadingBean;
import com.sinmem.peony.dao.bean.User;
import com.sinmem.peony.service.ReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.web.controller
 * @Author sinmem
 * @CreateTime 2021-03-18 22:02
 * @Description
 */
@RestController
@CrossOrigin
@RequestMapping("/web/v1/law")
public class ReadingController {
    @Autowired
    private ReadingService readingService;

    @GetMapping("/getReading")
    public String getReading(Long lawId){
        return readingService.getReading(lawId).toString();
    }

    @PostMapping("/addReading")
    public String addReading(ReadingBean reading, @AuthenticationPrincipal UserDetails userDetails){
        if(userDetails instanceof User)
            reading.setAuthor((User) userDetails);
        return readingService.addReading(reading).toString();
    }

    @PostMapping("/updReading")
    public String updReading(ReadingBean reading, @AuthenticationPrincipal UserDetails userDetails){
        if(userDetails instanceof User)
            reading.setAuthor((User) userDetails);
        return readingService.updReading(reading).toString();
    }

    @GetMapping("/delReading")
    public String delReading(Long lawId){
        return readingService.delReading(lawId).toString();
    }
}
