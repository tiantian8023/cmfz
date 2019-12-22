package com.mp.cmfz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "user")
public class User {
    @Id
    private String id;
    private String password;
    private String phone;
    private String name;
    private Date createDate;
}
