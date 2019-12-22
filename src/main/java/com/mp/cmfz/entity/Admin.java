package com.mp.cmfz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "admin")
public class Admin implements Serializable {
    @Id
    private String id;
    private String name;
    private String password;
    private String salt;
    private String nickname;
    private int state;
}
