package com.collour.api.service;

import com.collour.api.domain.Notice;
import com.collour.api.domain.User;
import com.collour.api.repository.NoticeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@AllArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    @Override
    public Page<Notice> getList(int valid, int pageNo, int size, String properties) {
        return noticeRepository.getByValid(valid, PageRequest.of(pageNo - 1, size, Sort.Direction.DESC, properties));
    }

    @Override
    @Transactional
    public Notice getNotice(int valid, long no) {
        Notice notice = noticeRepository.findNoticeByNoAndValid(no, valid);
        notice.setHits(notice.getHits() + 1);
        noticeRepository.save(notice);

        return notice;
    }

    @Override
    public Boolean save(String title, String content, User author) {
        if (title.isEmpty() || content.isEmpty() || author == null) {
            return false;
        }

        Date datetime = new Date();

        try {
            Notice notice = new Notice();

            notice.setTitle(title);
            notice.setContent(content);
            notice.setAuthor(author);
            notice.setDate(datetime);
            notice.setTime(datetime);

            noticeRepository.save(notice);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public Boolean remove(long no) {
        try {
            Notice notice = noticeRepository.getOne(no);

            notice.setValid(0);
            noticeRepository.save(notice);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
