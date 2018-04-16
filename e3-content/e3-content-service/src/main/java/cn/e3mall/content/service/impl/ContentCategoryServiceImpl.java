package cn.e3mall.content.service.impl;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.dao.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 内容分类
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCategoryByParentId(long parentId) {
        //创建查询添加
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);

        //将查询结果转换成页面所需的List<EasyUITreeNode>

        List<EasyUITreeNode> result = new ArrayList<>();
        for (TbContentCategory contentCategory : list) {
            EasyUITreeNode treeNode = new EasyUITreeNode();
            treeNode.setId(contentCategory.getId());
            treeNode.setText(contentCategory.getName());
            treeNode.setState(contentCategory.getIsParent()?"closed":"open");
            //加入到集合中
            result.add(treeNode);
        }


        return result;
    }

    @Override
    public TbContentCategory addContentCategory(long parentId, String name) {
        //创建内容分类对象
        TbContentCategory contentCategory = new TbContentCategory();
        //设置属性
        //父类的ID
        contentCategory.setParentId(parentId);
        //该类目是否为父类目，1为true，0为false
        contentCategory.setIsParent(false);
        //设置该类目名称
        contentCategory.setName(name);
        //该目录状态。可选值:1(正常),2(删除)
        contentCategory.setStatus(1);
        //排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
        contentCategory.setSortOrder(1);
        //创建时间和修改时间
        Date date = new Date();
        contentCategory.setCreated(date);
        contentCategory.setUpdated(date);
        //执行插入
        contentCategoryMapper.insert(contentCategory);

        //判断父节点isParent是否为true，不是需要改为true
        //以parentId为主键查询出父目录
        TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
        //判断
        if(!parent.getIsParent()){
            parent.setIsParent(true);
            //更新父节点
            contentCategoryMapper.updateByPrimaryKey(parent);
        }

        //主键返回
        return contentCategory;
    }

    @Override
    public void updateContentCategory(long id, String name) {
        //根据id修改内容分类名称
        //创建一个内容分类对象
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setId(id);
        contentCategory.setName(name);
        contentCategoryMapper.updateContentCatNameById(contentCategory);
    }

    @Override
    public E3Result deleteContentCategory(long id) {
        //查询出节点对象
        TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);

        //判断该目录是否是父目录
        if(contentCategory.getIsParent()){
            //如果是，不让删除
            //将状态设置成300 ，信息为“为父节点不能删除”
            return new E3Result(300,"父节点不能直接删除！",null);
        }

        //根据id删除
        contentCategoryMapper.deleteByPrimaryKey(id);

        //判断父节点下是否还有子节点，如果没有需要把父节点的isparent改为false
        //获取父节点id
        long parentId = contentCategory.getParentId();
        //通过父节点查询分类
        //创建查询条件
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        //判断
        if(list.size() == 0){
            TbContentCategory parent = new TbContentCategory();
            parent.setId(parentId);
            parent.setIsParent(false);
            //根据主键修改数据
            contentCategoryMapper.updateByPrimaryKeySelective(parent);
        }

        return E3Result.ok();
    }
}
