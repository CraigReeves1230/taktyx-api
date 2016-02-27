/**
 * The Address class
 *
 * Represents an address in the database
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.model;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;

@Entity
@Table(name = "Addresses")
public class Address implements Serializable
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  protected Long id;

  @ManyToOne(targetEntity = Location.class)
  @JoinColumn(name = "location_id", referencedColumnName = "id")
  protected Location location;

  @Column(name = "company", nullable = true)
  protected String company;
  
  @Column(name = "address_1", nullable = false)
  protected String address1;
  
  @Column(name = "address_2", nullable = true)
  protected String address2;
  
  @Column(name = "city", nullable = false)
  protected String city;
  
  @Column(name = "state", nullable = true)
  protected String state;
  
  @Column(name = "zip_code", nullable = false)
  protected String zipCode;

  @Column(name = "date_created")
  protected Timestamp dateCreated;

  @Column(name = "date_modified")
  protected Timestamp dateModified;

  public String getCompany()
  {
    return company;
  }

  public void setCompany(String company)
  {
    this.company = company;
  }

  public Location getLocation()
  {
    return location;
  }

  public void setLocation(Location location)
  {
    this.location = location;
  }

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getAddress1()
  {
    return address1;
  }

  public void setAddress1(String address1)
  {
    this.address1 = address1;
  }

  public String getAddress2()
  {
    return address2;
  }

  public void setAddress2(String address2)
  {
    this.address2 = address2;
  }

  public String getCity()
  {
    return city;
  }

  public void setCity(String city)
  {
    this.city = city;
  }

  public String getState()
  {
    return state;
  }

  public void setState(String state)
  {
    this.state = state;
  }

  public String getZipCode()
  {
    return zipCode;
  }

  public void setZipCode(String zipCode)
  {
    this.zipCode = zipCode;
  }
  
  public void setDateCreated(Timestamp dateCreated)
  {
    if (dateCreated != null)
    {
      this.dateCreated = dateCreated;
    }
    else
    {
      java.util.Date date = new java.util.Date();
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
      java.util.Date date = new java.util.Date();
      this.dateModified = new Timestamp(date.getTime());
    }
  }
}
