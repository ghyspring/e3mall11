package cn.e3mall.content.service;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.pojo.TbContent;

public interface ContentService {

    EasyUIDataGridResult getContentList(long categoryId, int page, int rows);

    void addContent(TbContent content);

    void editContent(TbContent content);

    void deleteContentById(String ids);
}
