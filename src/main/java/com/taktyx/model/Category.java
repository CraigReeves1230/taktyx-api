/**
 * The Category class
 *
 * Represents a category of a service
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "Categories")
public class Category implements Serializable
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  protected Long id;

  @Column(name = "name", nullable = false, unique = true)
  protected String name;

  @ManyToOne(targetEntity = com.taktyx.model.Category.class)
  @JoinColumn(name = "parent_id", referencedColumnName = "id")
  protected Category parentCategory;

  @Column(name = "inactive", nullable = false)
  protected boolean inactive;

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

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Category getParentCategory()
  {
    return parentCategory;
  }

  public void setParentCategory(Category parentCategory)
  {
    this.parentCategory = parentCategory;
  }

  public boolean isInactive()
  {
    return inactive;
  }

  public void setInactive(boolean inactive)
  {
    this.inactive = inactive;
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
