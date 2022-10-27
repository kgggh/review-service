package com.shinhan.assignment.model.entity;

import com.shinhan.assignment.model.entity.common.BaseTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Lecture extends BaseTime {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long id;

    private String title;
    private String instructorName;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    private CoursedDeadline coursedDeadline;

    private Lecture(String title, String instructorName, Difficulty difficulty,
                    CoursedDeadline coursedDeadline) {
        this.title = title;
        this.instructorName = instructorName;
        this.difficulty = difficulty;
        this.coursedDeadline = coursedDeadline;
    }

    public static Lecture createLecture(String title, String instructorName, Difficulty difficulty,
                                        CoursedDeadline coursedDeadline) {
        return new Lecture(title, instructorName, difficulty, coursedDeadline);
    }
}
