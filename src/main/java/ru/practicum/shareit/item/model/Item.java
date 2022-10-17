package ru.practicum.shareit.item.model;

import lombok.*;
import ru.practicum.shareit.requests.ItemRequest;
import ru.practicum.shareit.user.User;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Valid
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "items", schema = "shareit")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(name = "name")
    private String name;
    @NotBlank
    @Column(name = "description")
    private String description;

    //    @NonNull
    @Column(name = "available")
    private Boolean available;


    @OneToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "request_id", nullable = true)
    private ItemRequest request;

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", available=" + available +
                ", owner=" + owner +
                ", request=" + request +
                '}';
    }
}