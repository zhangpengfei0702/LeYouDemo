package com.leyou.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("all")
public enum ExceptionEnum {

    PRICE_CANNOT_NO_NULL(400, "价格不能为空"),
    BRAND_NOT_FOND(404, "品牌没有找到"),
    CATEGORY_NOT_FOND(404, "商品分类没查到"),
    BRAND_SAVE_ERROR(500, "新增品牌失败"),
    INVALID_FILE_TYPE(400, "无效的文件类型"),
    UPLOAD_FILE_ERROR(500,"上传文件失败"),
    ;
    private int code;
    private String msg;
}
