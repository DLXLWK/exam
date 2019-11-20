package com.prj.server.exam;

import com.prj.entity.Exam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamServer {
    public List<Exam> query(@Param("mid") int mid);
}
