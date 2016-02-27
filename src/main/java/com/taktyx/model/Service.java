/**
 * The Service class
 *
 * Represents a service in the database
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "Services")
public class Service implements Serializable
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  protected Long id;

  @Column(name = "name", nullable = false)
  protected String name;

  @ManyToOne(targetEntity = com.taktyx.model.Category.class)
  @JoinColumn(name = "category_id", referencedColumnName = "id")
  protected Category category;

  @ManyToOne(targetEntity = User.class)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  protected User user;

  @Column(name = "inactive", nullable = false)
  protected boolean inactive;

  @Column(name = "rating_score", nullable = true)
  protected Float ratingScore;

  @Column(name = "num_of_ratings", nullable = true)
  protected Long numberOfRatings;

  @Column(name = "description", columnDefinition = "LONGTEXT", nullable = true)
  protected String description;

  @Column(name = "phone_number", nullable = true)
  protected String phoneNumber;

  @Column(name = "email", nullable = false)
  protected String email;

  @ManyToOne(targetEntity = Address.class)
  @JoinColumn(name = "address_id", referencedColumnName = "id")
  protected Address address;

  public Long getNumberOfRatings()
  {
    return numberOfRatings;
  }

  public void setNumberOfRatings(Long numberOfRatings)
  {
    this.numberOfRatings = numberOfRatings;
  }

  public Float getRatingScore()
  {
    return ratingScore;
  }

  public void setRatingScore(Float ratingScore)
  {
    this.ratingScore = ratingScore;
  }

  @Column(name = "date_created")
  protected Timestamp dateCreated;

  @Column(name = "date_modified")
  protected Timestamp dateModified;

  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  public Long getId()
  {
    return id;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public void setPhoneNumber(String phoneNumber)
  {
    this.phoneNumber = phoneNumber;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public Address getAddress()
  {
    return address;
  }

  public void setAddress(Address address)
  {
    this.address = address;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Category getCategory()
  {
    return category;
  }

  public void setCategory(Category category)
  {
    this.category = category;
  }

  public boolean isInactive()
  {
    return inactive;
  }

  public void setInactive(boolean inactive)
  {
    this.inactive = inactive;
  }

  public Timestamp getDateCreated()
  {
    return dateCreated;
  }

  public Timestamp getDateModified()
  {
    return dateModified;
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
}
