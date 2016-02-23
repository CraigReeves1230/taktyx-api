/**
 * The LoginForm class
 *
 * This class represents the login form
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.resource.bean;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class LoginForm implements FormInterface
{
  @NotEmpty(message = "Please provide the email associated with your account.")
  @Email(message = "Please provide a valid email address.")
  public String email;
  
  @NotEmpty(message = "Please provide the password associated with your account.")
  public String password;
}
