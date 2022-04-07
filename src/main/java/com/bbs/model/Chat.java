package com.bbs.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Chat")
@Getter
@Setter
@NoArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "board")
    private long board;

    @Column(name = "title")
    private String title;

    @Column(name = "comment")
    private String comment;

    public Chat(long board, String title, String comment) {
        this.board = board;
        this.title = title;
        this.comment = comment;
    }
}
