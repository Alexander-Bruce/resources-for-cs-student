package backend.experiment3.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterInfo implements Serializable {
    private String username;
    private String password;
}
