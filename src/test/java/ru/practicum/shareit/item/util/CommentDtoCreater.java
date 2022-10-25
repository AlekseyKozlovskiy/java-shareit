package ru.practicum.shareit.item.util;

import ru.practicum.shareit.comments.Comment;
import ru.practicum.shareit.comments.CommentDtoNew;
import ru.practicum.shareit.comments.CommentMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

public class CommentDtoCreater {
    public static CommentDtoNew getCommentDto() {
        User user = new User(1L, "user 1", "user1@email");
        Item item = new Item(1L, "item name", "description", true, user, null);

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setText("text");
        comment.setUser(user);
        comment.setItem(item);
        comment.setCreated(null);

        return CommentMapper.toCommentDtoNew(comment);
    }
}
