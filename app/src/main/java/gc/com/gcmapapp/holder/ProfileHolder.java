package gc.com.gcmapapp.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;
import com.unnamed.b.atv.model.TreeNode;

import gc.com.gcmapapp.R;
import gc.com.gcmapapp.utils.TreeUtils;

/**
 * Created by Bogdan Melnychuk on 2/13/15.
 */
public class ProfileHolder extends TreeNode.BaseNodeViewHolder<IconTreeItemHolder.IconTreeItem> {
    private CheckBox nodeSelector;

    public ProfileHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(final TreeNode node, IconTreeItemHolder.IconTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_profile_node, null, false);
        TextView tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(value.text);

        final PrintView iconView = (PrintView) view.findViewById(R.id.icon);
        iconView.setIconText(context.getResources().getString(value.icon));
        nodeSelector = (CheckBox) view.findViewById(R.id.node_selector);
        nodeSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = nodeSelector.isChecked();
                node.setSelected(isChecked);
                nodeSelector.setChecked(isChecked);
//                node.setSelected(isChecked);
//                for (TreeNode n : node.getChildren()) {
//                    getTreeView().selectNode(n, isChecked);
//                }
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

        return view;
    }


    @Override
    public void toggleSelectionMode(boolean editModeEnabled) {
        nodeSelector.setVisibility(editModeEnabled ? View.VISIBLE : View.GONE);
        nodeSelector.setChecked(mNode.isSelected());
    }

    @Override
    public int getContainerStyle() {
        return R.style.TreeNodeStyleCustom;
    }
}
