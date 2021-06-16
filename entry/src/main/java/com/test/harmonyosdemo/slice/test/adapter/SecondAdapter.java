package com.test.harmonyosdemo.slice.test.adapter;

import com.test.harmonyosdemo.ResourceTable;
import com.test.harmonyosdemo.slice.test.SecondAbilitySlice;
import ohos.agp.components.*;
import ohos.agp.window.dialog.ToastDialog;

import java.util.List;

public class SecondAdapter extends BaseItemProvider {
    private List<String> list;
    private final SecondAbilitySlice abilitySlice;

    public SecondAdapter(SecondAbilitySlice abilitySlice) {
        this.abilitySlice = abilitySlice;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    /**
     * 返回填充的表项个数
     * @return 填充的表项个数
     */
    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    /**
     * 根据position返回对应的数据
     * @param position 下标
     * @return 对应的数据
     */
    @Override
    public Object getItem(int position) {
        if (list != null && position >= 0 && position < list.size()) {
            return list.get(position);
        }
        return null;
    }

    /**
     *返回某一项的id
     * @param position 下标
     * @return 某一项的id
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 根据position返回对应的界面组件
     * @param position 下标
     * @param component 组件
     * @param componentContainer componentContainer
     * @return 对应的界面组件
     */
    @Override
    public Component getComponent(int position, Component component, ComponentContainer componentContainer) {
        final Component cpt;
        ViewHolder viewHolder;
        if (component == null) {
            cpt = LayoutScatter.getInstance(abilitySlice).parse(ResourceTable.Layout_item_second, null, false);
            viewHolder = new ViewHolder(cpt);
            // 将获取到的子组件信息绑定到列表项的实例中
            cpt.setTag(viewHolder);
        } else {
            cpt = component;
            // 从缓存中获取到列表项实例后，直接使用绑定的子组件信息进行数据填充。
            viewHolder = (ViewHolder) cpt.getTag();
        }
        viewHolder.itemText.setText(list.get(position));
        return cpt;
    }

    /**
     * 用于保存列表项中的子组件信息(类似ListView中ViewHolder性能优化)
     */
    public static class ViewHolder {
        Text itemText;
        ViewHolder(Component component) {
            itemText = (Text) component.findComponentById(ResourceTable.Id_item_text);

        }
    }

    /**
     * 添加列表数据
     */
    public void addData() {
        list.add("添加一条数据" + list.size());
        notifyDataChanged();
    }

    /**
     * 减少列表数据
     */
    public void loseData() {
        if (list.size() != 0) {
            list.remove(list.get(0));
            notifyDataChanged();
        } else {
            new ToastDialog(abilitySlice).setText("no data").show();
        }

    }
}
