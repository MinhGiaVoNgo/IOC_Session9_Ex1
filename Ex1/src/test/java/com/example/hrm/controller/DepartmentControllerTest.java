package com.example.hrm.controller;

import com.example.hrm.dto.DepartmentDTO;
import com.example.hrm.entity.Department;
import com.example.hrm.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DepartmentService departmentService;

    @Test
    @DisplayName("POST /api/v1/departments - Valid JSON returns 201 Created")
    void testCreateDepartment_Success() throws Exception {
        DepartmentDTO dto = DepartmentDTO.builder()
                .name("Phòng Kỹ thuật")
                .description("Phụ trách phát triển phần mềm")
                .build();

        Department saved = Department.builder()
                .id(1L)
                .name("Phòng Kỹ thuật")
                .description("Phụ trách phát triển phần mềm")
                .build();

        when(departmentService.createDepartment(any(DepartmentDTO.class))).thenReturn(saved);

        mockMvc.perform(post("/api/v1/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Phòng Kỹ thuật"))
                .andExpect(jsonPath("$.description").value("Phụ trách phát triển phần mềm"));
    }

    @Test
    @DisplayName("POST /api/v1/departments - Missing / blank name returns 400 Bad Request")
    void testCreateDepartment_MissingName_BadRequest() throws Exception {
        DepartmentDTO dto = DepartmentDTO.builder()
                .name("")
                .description("Mô tả hợp lệ")
                .build();

        mockMvc.perform(post("/api/v1/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/v1/departments - Name too short (< 5 chars) returns 400 Bad Request")
    void testCreateDepartment_ShortName_BadRequest() throws Exception {
        DepartmentDTO dto = DepartmentDTO.builder()
                .name("IT")
                .description("Mô tả hợp lệ")
                .build();

        mockMvc.perform(post("/api/v1/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}
