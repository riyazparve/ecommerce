package com.riyaz.ecom.productcatalog.model;

import lombok.Data;

import java.util.Date;

@Data
public abstract class BaseModel {
    private Long id;
    private Date creationDate;
    private Date modificationDate;
    private Status status;
}
