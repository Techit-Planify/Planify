package com.ll.planify.domain.todo.todo.entity;

import com.ll.planify.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hashtag extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "hashtag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Todo todo;
    @ManyToOne(fetch = FetchType.LAZY)
    private Keyword keyword;
}