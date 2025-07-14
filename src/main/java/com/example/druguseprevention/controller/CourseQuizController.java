package com.example.druguseprevention.controller;

import com.example.druguseprevention.dto.CourseQuizDto;
import com.example.druguseprevention.service.CourseQuizService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "api")
@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
public class CourseQuizController {

    private final CourseQuizService quizService;

    @GetMapping("/course/{courseId}")
    public List<CourseQuizDto> getQuizzesByCourse(@PathVariable Long courseId) {
        return quizService.getQuizByCourseId(courseId);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CourseQuizDto createQuiz(@RequestBody CourseQuizDto dto) {
        return quizService.createQuiz(dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CourseQuizDto updateQuiz(@PathVariable Long id, @RequestBody CourseQuizDto dto) {
        return quizService.updateQuiz(id, dto);
    }

    // API: Lấy danh sách courseId đã hoàn thành (điểm >= 60%)
    @GetMapping("/completed/{userId}")
    public List<Long> getCompletedCourses(@PathVariable Long userId) {
        return quizService.getCompletedCourseIdsByUserId(userId);
    }
}