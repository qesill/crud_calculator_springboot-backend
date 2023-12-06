package pl.com.wikann.springboot.utils;

// Klasa reprezentująca węzeł drzewa wyrażenia
class TreeNode {
    Object value;
    TreeNode left;
    TreeNode right;

    public TreeNode(Object value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
}