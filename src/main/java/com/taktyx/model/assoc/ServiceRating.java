/**
 * The ServiceRating class
 *
 * This class represents the relationship between services and ratings
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.model.assoc;

import com.taktyx.model.Rating;
import com.taktyx.model.Service;
import com.taktyx.model.User;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "AssocServicesRatings")
public class ServiceRating implements Serializable
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  protected Long id;

  @ManyToOne(targetEntity = Service.class)
  @JoinColumn(name = "service_id", referencedColumnName = "id")
  protected Service service;

  @ManyToOne(targetEntity = Rating.class)
  @JoinColumn(name = "rating_id", referencedColumnName = "id")
  protected Rating rating;

  @Column(name = "date_created")
  protected Timestamp dateCreated;

  @Column(name = "date_modified")
  protected Timestamp dateModified;

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public Service getService()
  {
    return service;
  }

  public void setService(Service service)
  {
    this.service = service;
  }

  public Rating getRating()
  {
    return rating;
  }

  public void setRating(Rating rating)
  {
    this.rating = rating;
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

