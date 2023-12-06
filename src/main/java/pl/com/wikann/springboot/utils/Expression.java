package pl.com.wikann.springboot.utils;

// Klasa reprezentująca wyrażenie matematyczne
class Expression {
    TreeNode root;

    public Expression(TreeNode root) {
        this.root = root;
    }

    public float evaluate() {
        return ExpressionTreeBuilder.calculateTree(root);
    }
}