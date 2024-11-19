package mate.academy.winetaster.dto.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import mate.academy.winetaster.validation.FieldMatch;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@FieldMatch(first = "password", second = "repeatPassword", message = "Passwords do not match")
public class UserRegistrationRequestDto {
    @Column(nullable = false)
    @Length(min = 8, max = 40)
    @Email
    private String email;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    @Length(min = 8, max = 20)
    private String password;

    @Column(nullable = false)
    @Length(min = 8, max = 20)
    private String repeatPassword;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;
}
