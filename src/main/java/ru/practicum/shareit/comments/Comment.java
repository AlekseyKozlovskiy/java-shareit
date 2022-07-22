package ru.practicum.shareit.comments;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.LocalDateTime;

@Setter
@Getter
@Valid
@Entity
@Table(name = "comments", schema = "shareit")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String text;

    @ManyToOne
    @JoinColumn(name = "item_id")
    Item item;

    @ManyToOne
    @JoinColumn(name = "author_id")
    User user;

    @Column(name = "created")
    LocalDateTime created = LocalDateTime.now();
}
