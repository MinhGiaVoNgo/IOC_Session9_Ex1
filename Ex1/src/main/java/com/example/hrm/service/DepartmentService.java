package com.example.hrm.service;

import com.example.hrm.dto.DepartmentDTO;
import com.example.hrm.entity.Department;

public interface DepartmentService {
    Department createDepartment(DepartmentDTO dto);
}
