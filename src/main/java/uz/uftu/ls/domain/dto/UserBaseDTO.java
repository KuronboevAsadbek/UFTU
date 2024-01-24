package uz.uftu.ls.domain.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import uz.uftu.ls.domain.entity.FileStorage;
import uz.uftu.ls.domain.enumeration.Role;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserBaseDTO {
    @NotNull
    Long id;
    @NotNull
    String username;
    @NotNull
    String firstName;
    @NotNull
    String lastName;
    @NotNull
    @Enumerated(EnumType.STRING)
    Role role;
    FileStorage fileStorage;
}
