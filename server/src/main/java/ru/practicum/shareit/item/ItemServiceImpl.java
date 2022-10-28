package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.comments.Comment;
import ru.practicum.shareit.comments.CommentDtoNew;
import ru.practicum.shareit.comments.CommentMapper;
import ru.practicum.shareit.comments.CommentRepository;
import ru.practicum.shareit.exceptions.IncorrectRequest;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.requests.ItemRequest;
import ru.practicum.shareit.requests.ItemRequestRepository;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.util.BookingValidation;
import ru.practicum.shareit.util.ItemValidation;
import ru.practicum.shareit.util.UserValidation;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemValidation itemValidation;
    private final UserValidation userValidation;
    private final UserRepository userRepository;
    private final BookingValidation bookingValidation;
    private final CommentRepository commentRepository;
    private final BookingRepository bookingRepository;
    private final ItemRequestRepository itemRequestRepository;

    @Transactional
    @Override
    public ItemDto addNewItem(Long userId, ItemDto itemDto) {
        itemDto.setOwner(UserMapper.toUserDto(userRepository.getById(userId)));
        Item item = ItemMapper.toNewItem(itemDto);
        itemValidation.chek(userId, itemDto);

        List<ItemRequest> all = itemRequestRepository.findAll();
        for (ItemRequest itemRequest : all) {
            String stroka = itemDto.getName().replaceAll(" ", "").toLowerCase().substring(0, 3);
            String zapros = itemRequest.getDescription().replaceAll(" ", "").toLowerCase();
            if (zapros.contains(stroka)) {
                itemDto.setRequestId(itemRequest.getId());
            }
        }
        itemRepository.save(item);
        itemDto.setId(item.getId());

        return itemDto;
    }

    @Override
    @Transactional
    public ItemDto upgradeItem(Long userId, ItemDto itemDto, Long itemId) {
        userValidation.isUserRegister(userId);
        itemDto.setOwner(UserMapper.toUserDto(userRepository.getById(userId)));
        Item item = ItemMapper.toNewItem(itemDto);
        item.setId(itemId);
        List<Item> collect = itemRepository.findAll().stream().filter(item1 -> item1.getOwner().getId()
                .equals(userId)).collect(Collectors.toList());
        if (collect.stream().anyMatch(item1 -> item1.getId().equals(itemId))) {
            upgrade(itemDto, item, itemId);
        } else throw new IncorrectRequest("У пользователя нет такой вещи");

        itemRepository.save(item);
        return ItemMapper.toItemDto(item);
    }

    @Override
    @Transactional
    public ItemDto get(Long userId, Long itemId) {
        userValidation.isUserRegister(userId);
        itemValidation.isItemExist(itemId);
        ItemDto itemDto = ItemMapper.toItemDto(itemRepository.getById(itemId));

        if (bookingValidation.test(itemId, userId)) {
            itemDto.setLastBooking(bookingValidation.lastBooking(itemId));
            itemDto.setNextBooking(bookingValidation.nextBooking(itemId));
        }
        itemDto.setComments(CommentMapper.toCommentDtoNewList(commentRepository.findAll()));

        return itemDto;
    }

    @Override
    @Transactional
    public List<ItemDto> getAll(Long userId) {
        System.out.println("2222222222");
        if (userValidation.isUserRegister(userId)) {
            List<Item> collect = itemRepository.findByOwnerId(userId);
            List<ItemDto> itemDtos = ItemMapper.toItemDtoList(collect);
            for (ItemDto item : itemDtos) {
                List<Booking> bookings = bookingRepository.findByItemId(item.getId());
                bookings.sort(Comparator.comparing(Booking::getEnd));
                for (Booking booking : bookings) {
                    if (booking.getEnd().isBefore(LocalDateTime.now())) {
                        item.setLastBooking(bookingValidation.lastBooking(item.getId()));
                    }
                    if (booking.getStart().isAfter(LocalDateTime.now())) {
                        item.setNextBooking(bookingValidation.nextBooking(item.getId()));
                    }
                }
            }
            return itemDtos;
        } else throw new IncorrectRequest("У пользователя нет такой вещи");
    }

    @Override
    @Transactional
    public List<ItemDto> search(String item) {
        List<Item> collect = new ArrayList<>();
        if (item.isBlank()) {
            return ItemMapper.toItemDtoList(collect);
        }
        collect = itemRepository.findAll().stream()
                .filter(i -> i.getAvailable().equals(true))
                .filter(i -> i.getName().toLowerCase().contains(item.toLowerCase())
                        || i.getDescription().toLowerCase().contains(item.toLowerCase()))
                .collect(Collectors.toList());
        return ItemMapper.toItemDtoList(collect);
    }

    void upgrade(ItemDto itemDto, Item item, Long itemId) {
        if (itemDto.getName() == null) {
            item.setName(itemRepository.getById(itemId).getName());
        }
        if (itemDto.getDescription() == null) {
            item.setDescription(itemRepository.getById(itemId).getDescription());
        }
        if (itemDto.getAvailable() == null) {
            item.setAvailable(itemRepository.getById(itemId).getAvailable());
        }

    }

    @Override
    public CommentDtoNew addComment(Long userId, CommentDtoNew commentDtoNew, Long itemId) {
        bookingValidation.isCommentAvailable(itemId, userId, commentDtoNew);
        bookingValidation.test(itemId, userId);
        commentDtoNew.setItem(ItemMapper.toItemDto(itemRepository.getById(itemId)));
        commentDtoNew.setUser(UserMapper.toUserDto(userRepository.getById(userId)));
        commentDtoNew.setCreated(LocalDateTime.now());
        commentDtoNew.setAuthorName(userRepository.getById(userId).getName());

        Comment comment = CommentMapper.toNewComment(commentDtoNew);
        Comment save = commentRepository.save(comment);
        commentDtoNew.setId(save.getId());
        return commentDtoNew;
    }
}
