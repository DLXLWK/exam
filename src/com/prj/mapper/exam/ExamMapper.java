package com.prj.mapper.exam;

import com.prj.entity.Exam;

import java.util.List;

public interface ExamMapper {

    public List<Exam> query(int mid);

}
