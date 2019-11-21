package com.prj.mapper.exam;

import com.prj.entity.Exam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamMapper {

    public List<Exam> query( @Param("mid") int mid);

}
