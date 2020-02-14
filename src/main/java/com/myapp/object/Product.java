package com.myapp.object;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name="PRODUCT", uniqueConstraints = {@UniqueConstraint(
		name = "ID_UNIQUE",
		columnNames = "PRODUCT_ID")}
		)
@SequenceGenerator(
		name = "PRODUCT_SEQ_GENERATOR",
		sequenceName = "PRODUCT_SEQ",
		initialValue = 1, allocationSize = 1
		)
public class Product {
	
	@Id
    @Column(name = "PRODUCT_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "PRODUCT_SEQ_GENERATOR")
    private Long id;
    
    @Column(name = "NAME", nullable = false)
    private String name;
    
    @Column(name = "PRICE", nullable = false)
    private int price;
    
    @Lob
    private String description;
    
    @Column(name = "imageName")
    private String imageName;
    
    @Column(name = "division")
    private String division;
    
    @Column(name = "brand")
    private String brand;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    
    
    public Product() {
    	Date nowDate = new Date();
    	
    	createdDate = nowDate;
    	lastModifiedDate = nowDate;
    }
    
}