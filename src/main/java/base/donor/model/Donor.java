package base.donor.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Donor implements Cloneable {

  public Donor() {}

  public Donor(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Integer id;

  @NotBlank
  @Size(min=0, max=30)
  private String firstName;

  @NotBlank
  @Size(min=0, max=30)
  private String lastName;

  public Donor clone() throws CloneNotSupportedException {
    return  (Donor) super.clone();
  }
}
