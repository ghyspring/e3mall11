package cn.e3mall.controller;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品管理
 * @Author:Pineapple
 * @Date: 2018/4/10 21:15
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 通过id查询商品
     * @param itemId
     * @return
     */
    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId){
        TbItem item = itemService.getItemById(itemId);
        return item;
    }

    /**
     * 添加商品
     * @param item
     * @param desc
     * @return
     */
    @RequestMapping("/item/save")
    @ResponseBody
    public E3Result addItem(TbItem item,String desc){

        itemService.addItem(item,desc);

        return E3Result.ok();
    }

    /**
     * 修改商品
     * @return
     */
    @ResponseBody
    @RequestMapping("/rest/item/update")
    public E3Result updateItem(TbItem item,String desc){
        itemService.updateItem(item,desc);
       return E3Result.ok();
    }

    /**
     * 删除商品
     * @param ids
     * @return
     */
    @RequestMapping("/rest/item/delete")
    @ResponseBody
    public E3Result deleteItemById(String ids){
        itemService.deleteItemById(ids);
        return E3Result.ok();
    }

    /**
     * 商品下架
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/rest/item/instock")
    public E3Result downItem(String ids){
        itemService.downItem(ids);
        return E3Result.ok();
    }

    /**
     * 商品上架
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/rest/item/reshelf")
    public E3Result upItem(String ids){
        itemService.upItem(ids);
        return E3Result.ok();
    }

    /**
     * 查询商品描述
     * @param id
     * @return
     */
    @RequestMapping("/rest/item/query/item/desc/{id}")
    @ResponseBody
    public E3Result selectItemDescById(@PathVariable long id){
        //System.out.println(id);
        TbItemDesc itemDesc = itemService.selectItemDescById(id);
        return E3Result.ok(itemDesc);
    }

}
