package com.mp.cmfz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {

    ALBUM_NOT_FOUND(404, "专辑不存在"),
    ALBUM_SAVE_ERROR(500, "新增专辑失败"),
    ALBUM_DELETE_ERROR(500, "删除专辑失败"),
    IMAGE_UPLOAD_ERROR(500, "图片上传失败"),
    Article_NOT_FOUND(404, "查找文章失败");
    private int code;
    private String msg;
}
