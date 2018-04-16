package cn.e3mall.controller;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.pojo.TbContentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 内容分类管理
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * 查询内容分类列表
     * @param parentId
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCategoryByParentId(@RequestParam(value = "id",defaultValue = "0") long parentId){
        List<EasyUITreeNode> result = contentCategoryService.getContentCategoryByParentId(parentId);
        return result;
    }

    /**
     * 添加内容分类
     * @param parentId
     * @param name
     * @return
     */
    @RequestMapping("/create")
    @ResponseBody
    public E3Result addContentCategory(long parentId,String name){
        TbContentCategory contentCategory = contentCategoryService.addContentCategory(parentId,name);
        return E3Result.ok(contentCategory);
    }

    /**
     * 重命名节点
     * @param id
     * @param name
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public E3Result updateContentCategory(long id,String name){
        contentCategoryService.updateContentCategory(id,name);
        return E3Result.ok();
    }

    /**
     * 删除内容分类节点
     * @param id
     */
    @RequestMapping("/delete")
    @ResponseBody
    public E3Result deleteContentCategory(long id){
        E3Result e3Result = contentCategoryService.deleteContentCategory(id);
        return e3Result;
    }
}
