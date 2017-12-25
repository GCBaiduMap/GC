package gc.com.gcmapapp.utils;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

/**
 * Created by jiguijun on 2017/12/25.
 */

public class TreeUtils {

    public static void setParentNode(boolean isChecked, TreeNode n, AndroidTreeView treeView) {
        if (isChecked){
            if(n.getParent() != null){
                for(TreeNode node : n.getParent().getChildren()){
                    if(!node.isSelected())
                        return;
                }
                treeView.selectNode(n.getParent(), isChecked);
                setParentNode(isChecked, n.getParent(), treeView);
            }
        }else{
            if(n.getParent() != null){
                treeView.selectNode(n.getParent(), isChecked);
                setParentNode(isChecked, n.getParent(), treeView);
            }
        }
    }

    public static void setChildrenNode(boolean isChecked, TreeNode n, AndroidTreeView treeView) {
        for(TreeNode treeNode : n.getChildren()){
            treeView.selectNode(treeNode, isChecked);
        }
    }

    public static boolean isSelected(TreeNode node){
        if(node.isSelected()){
            return true;
        }else {
            for(TreeNode child : node.getChildren()){
                return isSelected(child);
            }
        }
        return false;
    }
}
