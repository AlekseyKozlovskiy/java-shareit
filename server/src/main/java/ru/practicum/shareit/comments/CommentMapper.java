package ru.practicum.shareit.comments;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.user.UserMapper;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentMapper {

    public static Comment toNewComment(CommentDtoNew commentDtoNew) {
        Comment comment = new Comment();
        comment.setText(commentDtoNew.getText());
        comment.setCreated(commentDtoNew.getCreated());
        comment.setItem(ItemMapper.toNewItem(commentDtoNew.getItem()));
        comment.setUser(UserMapper.toNewUser(commentDtoNew.getUser()));
        return comment;
    }

    public static List<CommentDtoNew> toCommentDtoNewList(List<Comment> comments) {
        List<CommentDtoNew> commentDtoNewList = new ArrayList<>();
        for (Comment comment : comments) {
            commentDtoNewList.add(toCommentDtoNew(comment));
        }
        return commentDtoNewList;
    }

    public static CommentDtoNew toCommentDtoNew(Comment comment) {
        return CommentDtoNew.builder()
                .id(comment.getId())
                .text(comment.getText())
                .created(comment.getCreated())
                .authorName(comment.getUser().getName())
                .build();
    }
}
