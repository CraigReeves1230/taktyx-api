/**
 * The ServiceMessage class
 *
 * This class represents the relationship between
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.model.assoc;

import com.taktyx.model.Message;
import com.taktyx.model.Service;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "AssocServicesMessages")
public class ServiceMessage
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  protected Long id;

  @Column(name = "date_created")
  protected Timestamp dateCreated;

  @Column(name = "date_modified")
  protected Timestamp dateModified;

  @ManyToOne(targetEntity = Service.class)
  @JoinColumn(name = "service_id", referencedColumnName = "id")
  protected Service service;

  @Column(name = "has_read", nullable = false)
  protected boolean read;

  public boolean hasRead()
  {
    return read;
  }

  public void setRead(boolean read)
  {
    this.read = read;
  }

  @ManyToOne(targetEntity = Message.class)
  @JoinColumn(name = "message_id", referencedColumnName = "id")
  protected Message message;

  public Service getService()
  {
    return service;
  }

  public void setService(Service service)
  {
    this.service = service;
  }

  public Message getMessage()
  {
    return message;
  }

  public void setMessage(Message message)
  {
    this.message = message;
  }

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
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
