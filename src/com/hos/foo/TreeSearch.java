package com.hos.foo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TreeSearch {

    public static int[] solution(int h, int[] q) {

        if (h < 1 || h > 30)
            throw new IllegalArgumentException("h must be between 1 and 30");

        if (q == null || q.length == 0)
            throw new IllegalArgumentException("q is empty");

        BinaryTree myTree = new BinaryTree(h, q);
        return myTree.buildTree();
    }

    public static void main(String... args) {
        TreeSearch.solution(5, new int[]{19, 14, 28, 31, -1, 45});
    }

    static class BinaryTree {
        TreeNode root;
        int treeDepth;
        int currentNodeLevel;
        List<Integer> targetValueList;
        int[] targets;

        BinaryTree(int h, int[] targets) {
            this.treeDepth = h;
            this.targets = targets;
            this.root = new TreeNode();
            root.level = h;
            root.parentNode = null;
            root.value = -1;
            targetValueList = new ArrayList<>(Collections.nCopies(
                    this.targets.length, -1));
        }

        int[] buildTree() {

            this.root = insertNode(root, (int) Math.pow(2, root.level) - 1,
                    treeDepth, root);
            // traversePostorder(this.root);
            System.out.println(targetValueList);
            return targetValueList.stream().mapToInt(i -> i).toArray();
        }

        TreeNode insertNode(TreeNode currentNode, int value, int level,
                            TreeNode parent) {

            if (level > 0) {
                TreeNode node = new TreeNode(value, level, parent);
                searchValue(value, parent);

                currentNode = node;
                --level;
                currentNode.leftNode = insertNode(currentNode.leftNode,
                        node.value - (int) Math.pow(2, node.level - 1), level,
                        currentNode);
                currentNode.rightNode = insertNode(currentNode.rightNode,
                        node.value - 1, level, currentNode);
            }
            return currentNode;
        }

        private void searchValue(int value, TreeNode parent) {
            int index = -1;
            for (int i = 0; i < targets.length; i++)
                if (targets[i] == value) {
                    index = i;
                    break;
                }
            if (index != -1)
                targetValueList.set(index, parent.value);

        }

        void traversePostorder(TreeNode node) {
            if (node == null)
                return;
            System.out.println("Node Level ==> " + node.level
                    + " : Node Val ==> " + node.value
                    + " : Parent Node Value ==> " + node.parentNode.value);
            traversePostorder(node.leftNode);
            traversePostorder(node.rightNode);
        }
    }

    static class TreeNode {
        int value;
        int level;
        TreeNode parentNode;
        TreeNode leftNode;
        TreeNode rightNode;

        public TreeNode() {

        }

        public TreeNode(int value, int level, TreeNode parent) {
            super();
            this.value = value;
            this.level = level;
            this.parentNode = parent;
        }

        @Override
        public String toString() {
            return "TreeNode [value=" + value + ", level=" + level
                    + ", parentNode=" + parentNode + ", leftNode=" + leftNode
                    + ", rightNode=" + rightNode + "]";
        }

    }

}

// /currentNode.rightNode = buildTree(currentNode.rightNode,
// / --currentNodeLevel, --currentNodeLevel);

// (currentNode.leftNode.value - (long) Math.pow(2,
// currentNodeLevel
// - 1)
// node//.rightNode = buildTree(node, (node.value - 1),
// /--currentNodeLevel);
/*
 * void assignValuePostorder(TreeNode node, long value) { if (node == null)
 * return;
 *
 * node.value = value; assignValuePostorder(node.leftNode, node.value - (long)
 * Math.pow(2, node.level - 1)); assignValuePostorder(node.rightNode, node.value
 * - 1); }
 *
 * int[] findNodeValue(int[] targets) { targetValueList = new
 * ArrayList<>(Collections.nCopies( targets.length, -1));
 *
 * for (int i = 0; i < targets.length; i++) findNodeValue(root, targets[i], i);
 *
 * System.out.println(targetValueList); return
 * targetValueList.stream().mapToInt(i -> i).toArray(); }
 *
 * void findNodeValue(TreeNode node, int target, int index) { if (node == null)
 * return;
 *
 * else if (node.value == target) { System.out.println("Found:" +
 * node.parentNode.value); targetValueList.set(index, node.parentNode.value);
 * return; }
 *
 * findNodeValue(node.leftNode, target, index); findNodeValue(node.rightNode,
 * target, index); }
 */