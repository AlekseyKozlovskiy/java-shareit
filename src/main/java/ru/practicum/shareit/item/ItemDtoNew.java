package ru.practicum.shareit.item;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemDtoNew {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private LastBooking lastBooking;


}
