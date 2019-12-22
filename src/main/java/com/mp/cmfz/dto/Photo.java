package com.mp.cmfz.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Photo implements Serializable {

   /* "is_dir": false,
            "has_file": false,
            "filesize": 208736,
            "dir_path": "",
            "is_photo": true,
            "filetype": "jpg",
            "filename": "1241601537255682809.jpg",
            "datetime": "2018-06-06 00:36:39"*/

    private Boolean is_dir;
    private Boolean has_file;
    private Long filesize;
    private String dir_path;
    private Boolean is_photo;
    private String filetype;
    private String filename;
    private String datetime;
}
