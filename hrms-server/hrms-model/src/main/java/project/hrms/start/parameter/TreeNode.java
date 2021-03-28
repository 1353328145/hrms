package project.hrms.start.parameter;

import project.hrms.start.entity.Department;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private String label;
    private Department department;
    private List<TreeNode> children;

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public void add(TreeNode treeNode){
        if (this.children == null){
            this.children = new ArrayList<>();
        }
        children.add(treeNode);
    }
    @Override
    public String toString() {
        return "TreeNode{" +
                "department=" + department +
                ", children=" + children +
                '}';
    }
}
