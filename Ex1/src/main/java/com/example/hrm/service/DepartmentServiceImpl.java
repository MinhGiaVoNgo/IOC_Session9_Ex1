package com.example.hrm.service;

import com.example.hrm.dto.DepartmentDTO;
import com.example.hrm.entity.Department;
import com.example.hrm.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public Department createDepartment(DepartmentDTO dto) {
        Department department = Department.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
        return departmentRepository.save(department);
    }
}
