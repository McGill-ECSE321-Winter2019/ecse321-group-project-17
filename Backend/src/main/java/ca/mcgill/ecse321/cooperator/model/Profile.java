package ca.mcgill.ecse321.cooperator.model;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public abstract class Profile{
   private String email;

public void setEmail(String value) {
    this.email = value;
}
@Id
public String getEmail() {
    return this.email;
}
private String name;

public void setName(String value) {
    this.name = value;
}
public String getName() {
    return this.name;
}
private String password;

public void setPassword(String value) {
    this.password = value;
}
@NotNull
@NotEmpty
public String getPassword() {
    return this.password;
}
private String phone;

public void setPhone(String value) {
    this.phone = value;
}
public String getPhone() {
    return this.phone;
}
}
