/**
 * The ServiceForm class
 *
 * This class represents the form for creating services
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.resource.bean;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

public class ServiceForm implements FormInterface
{
  @NotBlank(message = "Please provide this service with a name, typically it would be your company name.")
  @Pattern(regexp = "[A-Za-z0-9\\-\\.\\s\\'\\&]+", message = "Please make sure your company name only contains numbers and letters.")
  public String name;

  public long category_id;

  @NotBlank(message = "Please provide your address.")
  @Pattern(regexp = "[A-Za-z0-9\\-\\.\\s\\'\\&]+", message = "Please make sure your company address only contains numbers and letters.")
  public String address_line_1;

  @Pattern(regexp = "[A-Za-z0-9\\-\\.\\s\\'\\&]+", message = "Please only use letters and numbers for your address.")
  public String address_line_2;

  @NotBlank(message = "Please provide the city this service is based in.")
  @Pattern(regexp = "[A-Za-z0-9\\-\\.\\s\\'\\&]+", message = "Please only use letters and numbers for your city.")
  public String city;

  @NotBlank(message = "Please provide the state this service is based in.")
  public String state;

  @NotBlank(message = "Please provide the zip code this service is based in.")
  @Pattern(regexp = "[0-9\\-]+", message = "Please make sure your zip code is a valid United States zip code.")
  public String zipcode;

  @NotBlank(message = "Please provide a valid phone number for this service.")
  @Pattern(regexp = "[0-9\\-\\(\\)]+", message = "Please make sure your phone number format is in: (xxx) xxx-xxxx")
  public String phoneNumber;

  @NotBlank(message = "Please provide your email address for your service.")
  @Email(message = "Please make sure you include a valid email address.")
  public String email;

  public Long user_id;

  public boolean inactive;

  public String description;
}
