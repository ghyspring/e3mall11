package cn.e3mall.content.service.impl;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.dao.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Override
    public EasyUIDataGridResult getContentList(long categoryId, int page, int rows) {
        //根据分类id查询内容 并分页查询
        //设置分页条件
        PageHelper.startPage(page,rows);
        List<TbContent> list = null;
        //创建查询条件对象
        TbContentExample example = new TbContentExample();
        if(categoryId == 0){
            //执行查询,全查
            list = contentMapper.selectByExample(example);
        }else {
            //设置条件
            TbContentExample.Criteria criteria = example.createCriteria();
            criteria.andCategoryIdEqualTo(categoryId);
            //执行查询
            list = contentMapper.selectByExample(example);
        }
        //获取分页信息
        Page<TbContent> pageInfo = (Page<TbContent>) list;

        //补全页面所需参数
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        //查询当前页面总数
        result.setTotal((int) pageInfo.getTotal());
        //当前页类容列表
        result.setRows(list);
        return result;
    }

    @Override
    public void addContent(TbContent content) {
        //补全参数
        Date date = new Date();
        content.setCreated(date);
        content.setUpdated(date);
        //执行添加
        contentMapper.insert(content);
    }

    @Override
    public void editContent(TbContent content) {
        contentMapper.updateByPrimaryKeySelective(content);
    }

    @Override
    public void deleteContentById(String ids) {
        String[] idstr = ids.split(",");
        for (String str : idstr) {
            long id = Long.parseLong(str);
            contentMapper.deleteByPrimaryKey(id);
        }
    }
}
