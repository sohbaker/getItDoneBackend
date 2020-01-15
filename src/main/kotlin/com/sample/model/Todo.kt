package com.sample.model

import javax.persistence.*

@Entity
@Table(name="TODO_LIST")
data class Todo (
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int = 0,

        @Column(name = "task_name", nullable = false)
        var name: String,

        @Column(name = "completed", nullable = false)
        var completed: Boolean = false
) { }
