package cn.e3mall.service;


import cn.e3mall.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * @Author:Pineapple
 * @Date: 2018/4/10 21:11
 */
public interface ItemCatService {

    List<EasyUITreeNode> getItemCatById(long parentId);

}
