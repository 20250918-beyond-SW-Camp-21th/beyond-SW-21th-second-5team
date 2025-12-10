package com.ohgiraffers.bookservice.secondbackend.bookreport.repository;

import com.ohgiraffers.bookservice.secondbackend.bookreport.entity.BookReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReportRepository extends JpaRepository<BookReport, Long> {

}
