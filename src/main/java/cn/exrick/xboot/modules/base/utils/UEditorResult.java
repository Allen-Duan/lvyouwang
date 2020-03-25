package cn.exrick.xboot.modules.base.utils;

import lombok.Data;

@Data
public class UEditorResult {
    /**
     * 上传结果
     */
    private String state;
    /**
     * 原图名称
     */
    private String original;
    /**
     * 保存名称
     */
    private String title;
    /**
     * 文件路径
     */
    private String url;
}
