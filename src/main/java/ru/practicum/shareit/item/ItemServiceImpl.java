package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.IncorrectOwnerException;
import ru.practicum.shareit.exceptions.IncorrectUserException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.util.ItemValidation;
import ru.practicum.shareit.util.NumberGenerator;
import ru.practicum.shareit.util.UserValidation;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemValidation itemValidation;
    private final UserValidation userValidation;
    private final UserRepository userRepository;

    @Override
    public ItemDto addNewItem(Long userId, ItemDto itemDto) {
        userValidation.isUserRegister(userId);
        itemValidation.chek(userId, itemDto);
        itemDto.setId(NumberGenerator.getItemId());
        itemDto.setOwner(UserMapper.toUserDto(userRepository.get(userId)));
        Item item = ItemMapper.toNewItem(itemDto);
        return ItemMapper.toItemDto(itemRepository.add(userId, item));
    }

    @Override
    public ItemDto upgradeItem(Long userId, ItemDto itemDto, Long itemId) {
        userValidation.isUserRegister(userId);
        itemDto.setOwner(UserMapper.toUserDto(userRepository.get(userId)));
        Item item = ItemMapper.toNewItem(itemDto);
        return ItemMapper.toItemDto(itemRepository.upgrade(userId, item, itemId));

    }

    @Override
    public ItemDto get(Long userId, Long itemId) {
        if (userValidation.isUserRegister(userId)) {
            return ItemMapper.toItemDto(itemRepository.get(itemId));
        } else throw new IncorrectUserException(userId);
    }

    @Override
    public List<ItemDto> getAll(Long userId) {
        if (userValidation.isUserRegister(userId)) {
            return ItemMapper.toItemDtoList(itemRepository.getAll(userId));
        } else throw new IncorrectOwnerException();
    }

    @Override
    public List<ItemDto> search(String item) {
        return ItemMapper.toItemDtoList(itemRepository.search(item));
    }
}
