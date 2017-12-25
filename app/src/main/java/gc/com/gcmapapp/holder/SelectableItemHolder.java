package gc.com.gcmapapp.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.unnamed.b.atv.model.TreeNode;

import gc.com.gcmapapp.R;
import gc.com.gcmapapp.utils.TreeUtils;

/**
 * Created by Bogdan Melnychuk on 2/15/15.
 */
public class SelectableItemHolder extends TreeNode.BaseNodeViewHolder<IconTreeItemHolder.IconTreeItem> {
    private TextView tvValue;
    private CheckBox nodeSelector;

    public SelectableItemHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(final TreeNode node, IconTreeItemHolder.IconTreeItem item) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_selectable_item, null, false);

        tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(item.text);


        nodeSelector = (CheckBox) view.findViewById(R.id.node_selector);
        nodeSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = nodeSelector.isChecked();
                nodeSelector.setChecked(isChecked);
                node.setSelected(isChecked);
                TreeUtils.setChildrenNode(isChecked, node, getTreeView());
                TreeUtils.setParentNode(isChecked, node, getTreeView());
            }
        });
        nodeSelector.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        nodeSelector.setChecked(node.isSelected());

        if (node.isLastChild()) {
            view.findViewById(R.id.bot_line).setVisibility(View.INVISIBLE);
        }

        return view;
    }


    @Override
    public void toggleSelectionMode(boolean editModeEnabled) {
        nodeSelector.setVisibility(editModeEnabled ? View.VISIBLE : View.GONE);
        nodeSelector.setChecked(mNode.isSelected());
    }
}
