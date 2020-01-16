package com.myapp.mysqlObject;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="simple_comment")
public class SimpleComment implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="comment_no")
	private long comment_no;

	@Column(nullable = false)
	private String text;

	@Column(updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date datetime;

	public long getComment_no()
	{
		return comment_no;
	}
	public String getText()
	{
		return text;
	}
	public void setText(String text)
	{
		this.text = text;
	}
	public Date getDatetime()
	{
		return datetime;
	}
	public void setDatetime(Date datetime)
	{
		this.datetime = datetime;
	}
}