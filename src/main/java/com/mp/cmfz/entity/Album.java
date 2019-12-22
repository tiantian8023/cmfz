package com.mp.cmfz.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
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
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "album")
@ExcelTarget("album")
public class Album implements Serializable {

    @Id
    @Excel(name = "ID")
    private String id;

    @Excel(name = "标题")
    private String title;

    @Excel(name = "图片")
    private String cover;

    @Excel(name = "数量")
    @Column(name = "amount")
    private int amount;

    @Excel(name = "评分")
    private int score;

    @Excel(name = "作者")
    private String author;

    @Excel(name = "播音")
    private String broadcaster;// 播音

    @Excel(name = "内容")
    private String content;

    @Excel(name = "状态")
    private int status;

    @Excel(name = "介绍")
    private String introduce;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发布日期", format = "yyyy年MM月dd日 HH时mm分ss秒")
    private Date publishDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建日期", format = "yyyy年MM月dd日 HH时mm分ss秒")
    private Date createDate;

    @ExcelCollection(name = "文章集合")
    private List<Chapter> chapters;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后修改日期", format = "yyyy年MM月dd日 HH时mm分ss秒")
    private Date lastUpdateDate;
}
