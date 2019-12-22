package com.mp.cmfz.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "slide")
@ExcelTarget(value = "slide")
public class Slide implements Serializable {
    @Id
    @Excel(name = "编号")
    private String id;
    @Excel(name = "标题")
    private String title;
    @Excel(name = "图像", type = 2, width = 40, height = 20, imageType = 1)
    private String cover;
    @Column(name = "status")
    @Excel(name = "状态")
    private int status;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建日期", format = "yyyy年MM月dd日 HH时mm分ss秒")
    private Date create_date;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后更改日期", format = "yyyy年MM月dd日 HH时mm分ss秒")
    private Date last_update_date;
}
