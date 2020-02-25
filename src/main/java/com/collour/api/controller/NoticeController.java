package com.collour.api.controller;

import com.collour.api.service.NoticeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/notice")
@AllArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
}
