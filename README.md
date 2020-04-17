# sel

package com.tree;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Tree {
    Node root;

    public void add(int value){
        root = addRecursive(root, value);
    }

    private Node addRecursive(Node currentNode, int value) {
        if (currentNode == null){
            return new Node(value);
        }
        if (value < currentNode.value){
            currentNode.left = addRecursive(currentNode.left, value);
        }else if (value > currentNode.value){
            currentNode.right = addRecursive(currentNode.right, value);
        }else {
            return currentNode;
        }
        return currentNode;
    }

    public boolean contains(int value) {
        return containsRecursive(root, value);
    }

    private boolean containsRecursive(Node currentNode, int value) {
        if (currentNode == null){
            return false;
        }
        if (currentNode.value == value){
            return true;
        }
        if (value < currentNode.value){
            return containsRecursive(currentNode.left, value);
        }else {
            return containsRecursive(currentNode.right, value);
        }
    }

    public void delete(int value){
        root = deleteRecursive(root, value);
    }

    private Node deleteRecursive(Node currentNode, int value) {
        if (currentNode != null && currentNode.value == value){
            if (currentNode.left == null && currentNode.right == null) {
                return null;
            }
            if (currentNode.right == null) {
                return currentNode.left;
            }
            if (currentNode.left == null) {
                return currentNode.right;
            }
            int smallestValue = findSmallestValue(currentNode.right);
            currentNode.value = smallestValue;
            currentNode.right = deleteRecursive(currentNode.right, smallestValue);
            return currentNode;
        }
        if (value < currentNode.value){
            currentNode.left = deleteRecursive(currentNode.left, value);
            return currentNode;
        }
        currentNode.right = deleteRecursive(currentNode.right, value);
        return currentNode;
    }
    private int findSmallestValue(Node root) {
        return root.left == null ? root.value : findSmallestValue(root.left);
    }

    public void traverseInOrder() {
        traverseInOrder(root);
        System.out.println();
    }
    public void traversePreOrder() {
        traversePreOrder(root);
        System.out.println();
    }
    public void traversePostOrder() {
        traversePostOrder(root);
        System.out.println();
    }

    private void traverseInOrder(Node currentNode) {
        if (currentNode != null){
            traverseInOrder(currentNode.left);
            System.out.print(" " + currentNode.value);
            traverseInOrder(currentNode.right);

        }
    }
    private void traversePreOrder(Node currentNode) {
        if (currentNode != null){
            System.out.print(" " + currentNode.value);
            traversePreOrder(currentNode.left);
            traversePreOrder(currentNode.right);

        }
    }
    private void traversePostOrder(Node currentNode) {
        if (currentNode != null){
            traversePostOrder(currentNode.left);
            traversePostOrder(currentNode.right);
            System.out.print(" " + currentNode.value);

        }
    }
    public void traversePostOrderWithoutRecursion() {
        Stack<Node> stack = new Stack<Node>();
        Node prev = root;
        Node current = root;
        stack.push(root);

        while (!stack.isEmpty()) {
            current = stack.peek();
            boolean hasChild = (current.left != null || current.right != null);
            boolean isPrevLastChild = (prev == current.right || (prev == current.left && current.right == null));

            if (!hasChild || isPrevLastChild) {
                current = stack.pop();
                System.out.print(" " + current.value);
                prev = current;
            } else {
                if (current.right != null) {
                    stack.push(current.right);
                }
                if (current.left != null) {
                    stack.push(current.left);
                }
            }
        }
    }

    public void traverseLevelOrder() {
        if (root == null)
            return;
        Queue<Node> nodes = new LinkedList<>();
        nodes.add(root);

        while (!nodes.isEmpty()){
            Node node = nodes.remove();
            System.out.print(" " + node.value);
            if (node.left != null){
                nodes.add(node.left);
            }
            if (node.right != null){
                nodes.add(node.right);
            }
        }
        System.out.println();

    }

    public void printTraversePreOrder() {
        System.out.println(printTraversePreOrder(root));
    }
    private String printTraversePreOrder(Node root) {

        if (root == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(root.value);

        String pointerRight = "└──";
        String pointerLeft = (root.right != null) ? "├──" : "└──";

        traverseNodes(sb, "", pointerLeft, root.left, root.right != null);
        traverseNodes(sb, "", pointerRight, root.right, false);

        return sb.toString();
    }

    private void traverseNodes(StringBuilder sb, String padding, String pointer, Node currentNode,
                               boolean hasRightSibling) {

        if (currentNode != null) {

            sb.append("\n");
            sb.append(padding);
            sb.append(pointer);
            sb.append(currentNode.value);

            StringBuilder paddingBuilder = new StringBuilder(padding);
            if (hasRightSibling) {
                paddingBuilder.append("│  ");
            } else {
                paddingBuilder.append("   ");
            }

            String paddingForBoth = paddingBuilder.toString();
            String pointerRight = "└──";
            String pointerLeft = (currentNode.right != null) ? "├──" : "└──";

            traverseNodes(sb, paddingForBoth, pointerLeft, currentNode.left, currentNode.right != null);
            traverseNodes(sb, paddingForBoth, pointerRight, currentNode.right, false);

        }

    }


    private class Node {
        private int value;
        Node left;
        Node right;

        Node(int value){
            this.value = value;
            left=null;
            right=null;
        }


    }
}


package com.tree;

public class Application {
    public static void main(String[] args) {
        Tree tree = new Tree();
        tree.add(4);
        tree.add(2);
        tree.add(6);
        tree.add(5);
        tree.add(3);
        tree.add(7);
        tree.add(1);
        System.out.println(tree.contains(4));
        System.out.println(tree.contains(2));
        System.out.println(tree.contains(3));
        System.out.println(tree.contains(5));
        System.out.println(tree.contains(6));
        //tree.delete(3);
        System.out.println(tree.contains(3));

        //DFS
        //1. InOrder
        tree.traverseInOrder();
        //2. PreOrder
        tree.traversePreOrder();
        //3. PostOrder
        tree.traversePostOrder();
        tree.traversePostOrderWithoutRecursion();
        //BFS - level order
        tree.traverseLevelOrder();
        tree.printTraversePreOrder();
    }
}
