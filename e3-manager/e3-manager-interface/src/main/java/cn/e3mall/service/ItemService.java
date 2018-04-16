package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;

/**
 * @Author:Pineapple
 * @Date: 2018/4/10 21:11
 */
public interface ItemService {

    TbItem getItemById(long id);

    EasyUIDataGridResult getItemList(Integer page, Integer rows);

    void addItem(TbItem item, String desc);

    void deleteItemById(String ids);

    void downItem(String ids);

    void upItem(String ids);

    TbItemDesc selectItemDescById(long id);

    void updateItem(TbItem item, String desc);
}
