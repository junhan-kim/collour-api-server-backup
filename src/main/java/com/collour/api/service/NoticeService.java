package com.collour.api.service;

import com.collour.api.domain.Notice;
import com.collour.api.domain.User;
import org.springframework.data.domain.Page;

public interface NoticeService {

    Page<Notice> getList(int valid, int pageNo, int size, String properties);

    Notice getNotice(int valid, long no);

    Boolean save(String title, String content, User author);

    Boolean remove(long no);
}
