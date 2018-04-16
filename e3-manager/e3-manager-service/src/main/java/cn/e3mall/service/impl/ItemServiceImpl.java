package cn.e3mall.service.impl;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.dao.TbItemDescMapper;
import cn.e3mall.dao.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author:Pineapple
 * @Date: 2018/4/10 21:12
 */
@Service
public class ItemServiceImpl implements ItemService{

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;

    /**
     * 根据商品id查询
     * @param id
     * @return
     */
    @Override
    public TbItem getItemById(long id) {
        return itemMapper.selectByPrimaryKey(id);
    }

    @Override
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        //设置分页条件
        PageHelper.startPage(page,rows);

        //执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> list = itemMapper.selectByExample(example);

        //取出分页信息
        Page<TbItem> pageInfo = (Page<TbItem>) list;

        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal((int) pageInfo.getTotal());
        result.setRows(list);
        return result;
    }

    @Override
    public void addItem(TbItem item, String desc) {
        //补全item属性
        long itemId = IDUtils.genItemId();
        item.setId(itemId);
        //商品状态，1-正常，2-下架，3-删除
        item.setStatus((byte) 1);
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);
        //插入商品
        itemMapper.insert(item);

        //补全ItemDesc属性
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        //向商品描述表插入数据
        itemDescMapper.insert(itemDesc);

    }

    @Override
    public void deleteItemById(String ids) {
        //将id字符串分割成数组
        String[] idStr = ids.split(",");
        //遍历通过id删除商品表和商品描述表
        for (String id : idStr) {
            long l = Long.parseLong(id);
            itemMapper.deleteByPrimaryKey(l);
            itemDescMapper.deleteByPrimaryKey(l);
        }
    }

    @Override
    public void downItem(String ids) {
        //将id字符串分割成数组
        String[] idStr = ids.split(",");
        //遍历通过id修改商品表状态为下架
        for (String id : idStr) {
            long l = Long.parseLong(id);
            itemMapper.updateItemStatusById(l);
        }
    }

    @Override
    public void upItem(String ids) {
        //将id字符串分割成数组
        String[] idStr = ids.split(",");
        //遍历通过id修改商品表状态为上架
        for (String id : idStr) {
            long l = Long.parseLong(id);
            itemMapper.upItemById(l);
        }
    }

    @Override
    public TbItemDesc selectItemDescById(long id) {
        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(id);
        return itemDesc;
    }

    @Override
    public void updateItem(TbItem item, String desc) {
        Date date = new Date();
        item.setCreated(date);
        itemMapper.updateByPrimaryKeySelective(item);

        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDesc.setUpdated(date);
        itemDescMapper.updateByPrimaryKeySelective(itemDesc);

    }
}
