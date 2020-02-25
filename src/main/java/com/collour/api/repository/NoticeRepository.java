package com.collour.api.repository;

import com.collour.api.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Notice findNoticeByNoAndValid(long no, int valid);

    List<Notice> findByValidOrderByNo(int valid, Sort sort);

    Page<Notice> getByValid(int valid, Pageable page);
}
