/**
 * The UserForm class
 *
 * This class represents the user registration and account form.
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.resource.bean;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class UserForm implements FormInterface
{
  @NotBlank(message = "Please provide your first name.")
  @Pattern(regexp = "[A-Za-z\\s]+", message = "Your first name must only contain alpha-numeric characters.")
  public String firstName;
  
  @NotBlank(message = "Please provide your last name.")
  @Pattern(regexp = "[A-Za-z\\s]+", message = "Your last name must only contain alpha-numeric characters.")
  public String lastName;
  
  @NotBlank(message = "Please provide your phone number.")
  @Pattern(regexp = "[0-9\\(\\)\\-]+", message = "Please provide your phone number in the following format (xxx) xxx-xxxx")
  public String phoneNumber;
  
  @NotBlank(message = "Please provide your email address.")
  @Email(message = "Please provide a valid email address.")
  public String email;
  
  @NotBlank(message = "Please provide a password to use for your account.")
  @Size(min = 6, message = "Your password must be at least 6 characters in length.")
  public String password;
  
  @NotBlank(message = "Please repeat the password you want to use here.")
  public String confirmPassword;
}
