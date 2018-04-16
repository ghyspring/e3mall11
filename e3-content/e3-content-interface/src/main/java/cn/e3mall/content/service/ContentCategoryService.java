package cn.e3mall.content.service;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbContentCategory;

import java.util.List;

public interface ContentCategoryService {

    List<EasyUITreeNode> getContentCategoryByParentId(long parentId);

    TbContentCategory addContentCategory(long parentId, String name);

    void updateContentCategory(long parentId, String name);

    E3Result deleteContentCategory(long id);
}
