package uz.uftu.ls.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.uftu.ls.domain.entity.FieldOfStudy;
import uz.uftu.ls.domain.entity.User;
import uz.uftu.ls.domain.enumeration.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 50)
    private String lastName;

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @NotNull
    @Size(min = 4, max = 50)
    private String password;

    @NotNull
    private String roleName;


    private Long fieldOfStudyId;

    private String address;

    private String numberOfPersonalDocument;

    private String contractNumber;


    public static User map2Entity(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.firstName);
        user.setLastName(userDTO.lastName);
        user.setUsername(userDTO.username);
        user.setContactNumber(userDTO.contractNumber);
        user.setNumberOfPersonalDocument(userDTO.numberOfPersonalDocument);
        user.setAddress(userDTO.address);
        user.setRole(Role.valueOf(userDTO.roleName));
        if (userDTO.getFieldOfStudyId() != null) {
            FieldOfStudy fieldOfStudy = new FieldOfStudy();
            fieldOfStudy.setId(userDTO.fieldOfStudyId);
            user.setFieldOfStudy(fieldOfStudy);
        }
        return user;
    }
}
