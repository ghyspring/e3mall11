package cn.e3mall.controller;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品内容
 */
@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * 内容列表
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/query/list")
    @ResponseBody
    public EasyUIDataGridResult showList(long categoryId,int page, int rows){
        EasyUIDataGridResult result = contentService.getContentList(categoryId,page,rows);
        return result;
    }

    /**
     * 添加内容
     * @param content
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public E3Result addContent(TbContent content){
        contentService.addContent(content);
        return E3Result.ok();
    }

    /**
     * 修改内容
     * @param content
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public E3Result editContent(TbContent content){
        contentService.editContent(content);
        return E3Result.ok();
    }

    /**
     * 删除内容
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public E3Result deleteContentById(String ids){
        contentService.deleteContentById(ids);
        return E3Result.ok();
    }
}
