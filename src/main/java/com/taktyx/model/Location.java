/**
 * The Location class
 *
 * Represents a physical geological location in the database
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "Locations", indexes =
{
  @Index(columnList = "longitude, latitude", name = "IDX_LONG_LAT")
})
public class Location implements Serializable
{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  protected Long id;

  @Column(nullable = false)
  protected Double longitude;

  @Column(nullable = false)
  protected Double latitude;

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

  public Double getLongitude()
  {
    return longitude;
  }

  public void setLongitude(Double longitude)
  {
    this.longitude = longitude;
  }

  public Double getLatitude()
  {
    return latitude;
  }

  public void setLatitude(Double latitude)
  {
    this.latitude = latitude;
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
