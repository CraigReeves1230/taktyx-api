/**
 * The User class
 *
 * Represents a user in the database
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "Users", indexes = {
@Index(columnList = "email, password", name="IDX_EMAIL_PASS"),
@Index(columnList = "phone_number", name="IDX_USR_PHONE")
})
public class User implements Serializable
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  protected Long id;
  
  @Column(nullable = false, unique = true)
  @Email
  protected String email;
  
  @Column(nullable = false, name = "first_name")
  protected String firstName;
  
  @Column(nullable = true, name = "phone_number")
  protected String phoneNumber;
  
  @Column(nullable = false, name = "is_inactive")
  protected boolean inactive;
  
  @Column(nullable = false, name = "last_name")
  protected String lastName;
  
  @Column(nullable = false, name = "user_role")
  protected String role;
  
  @Column(nullable = false)
  protected String password;
  
  @Column(nullable = true)
  protected String sessionId;

  @Column(name = "date_created")
  protected Timestamp dateCreated;

  @Column(name = "date_modified")
  protected Timestamp dateModified;
  
  @ManyToOne(targetEntity = com.taktyx.model.Location.class)
  @JoinColumn(name = "last_known_location_id", referencedColumnName = "id")
  protected Location lastKnownLocation;

  public long getId()
  {
    return id;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber)
  {
    this.phoneNumber = phoneNumber;
  }

  public Location getLastKnownLocation()
  {
    return lastKnownLocation;
  }

  public void setLastKnownLocation(Location lastKnownLocation)
  {
    this.lastKnownLocation = lastKnownLocation;
  }

  protected void setId(Long id)
  {
    this.id = id;
  }

  public boolean isInactive()
  {
    return inactive;
  }

  public void setInactive(boolean inactive)
  {
    this.inactive = inactive;
  }
  
  public String getEmail()
  {
    return email;
  }

  public String getSessionId()
  {
    return sessionId;
  }

  public void setSessionId(String sessionId)
  {
    this.sessionId = sessionId;
  }

  public String getRole()
  {
    return role;
  }

  public void setRole(String role)
  {
    this.role = role;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public void setDateCreated(Timestamp dateCreated)
  {
    if (dateCreated != null)
    {
      this.dateCreated = dateCreated;
    }
    else
    {
      Date date = new Date();
      this.dateCreated = new Timestamp(date.getTime());
    }
  }

  public void setDateModified(Timestamp dateModified)
  {
    if (dateModified != null)
    {
      this.dateModified = dateModified;
    }
    else
    {
      Date date = new Date();
      this.dateModified = new Timestamp(date.getTime());
    }
  }
  
  @Override
  public String toString()
  {
    return "User{" + "id=" + id + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", role=" + role + ", password=" + password + '}';
  }
}
