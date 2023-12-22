package ru.mirea.lab31.task1;

import java.io.*;
import java.util.Queue;
import java.util.LinkedList;

public class ProcessorTree {
    private TreeNode root;

    public ProcessorTree() {
        this.root = null;
    }

    public void insert(Processor processor) {
        root = insertRec(root, processor);
    }

    private TreeNode insertRec(TreeNode root, Processor processor) {
        if (root == null) {
            return new TreeNode(processor);
        }

        if (processor.key < root.processor.key) {
            root.left = insertRec(root.left, processor);
        } else if (processor.key == root.processor.key) {
            System.out.println("Record with key " + processor.key + " already exists.");
        } else if (root.middle == null) {
            root.middle = new TreeNode(processor);
        } else {
            root.right = insertRec(root.right, processor);
        }

        return root;
    }

    public void delete(int key) {
        root = deleteRec(root, key);
    }

    private TreeNode deleteRec(TreeNode root, int key) {
        if (root == null) {
            System.out.println("Record with key " + key + " not found.");
            return null;
        }

        if (key < root.processor.key) {
            root.left = deleteRec(root.left, key);
        } else if (key > root.processor.key) {
            root.right = deleteRec(root.right, key);
        } else {
            if (root.middle != null) {
                System.out.println("Record with key " + key + " has multiple values. Specify a different key for deletion.");
            } else {
                if (root.right != null) {
                    TreeNode min = findMin(root.right);
                    root.processor = min.processor;
                    root.right = deleteRec(root.right, min.processor.key);
                } else if (root.left != null) {
                    TreeNode max = findMax(root.left);
                    root.processor = max.processor;
                    root.left = deleteRec(root.left, max.processor.key);
                } else {
                    root = null;
                }
            }
        }

        return root;
    }

    private TreeNode findMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private TreeNode findMax(TreeNode node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    public void printLevels() {
        if (root == null) {
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();

            System.out.print(node.processor.key + " ");

            if (node.left != null) {
                queue.add(node.left);
            }

            if (node.middle != null) {
                queue.add(node.middle);
            }

            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }

    private int height(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            int leftHeight = height(root.left);
            int middleHeight = height(root.middle);
            int rightHeight = height(root.right);

            return Math.max(leftHeight, Math.max(middleHeight, rightHeight)) + 1;
        }
    }

    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            saveToFile(root, writer);
            System.out.println("Data saved to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile(TreeNode root, PrintWriter writer) {
        if (root == null) {
            return;
        }

        saveToFile(root.left, writer);

        writer.println(root.processor.key + ", " + root.processor.name + ", " + root.processor.clockFrequency
                + ", " + root.processor.cacheSize + ", " + root.processor.busFrequency
                + ", " + root.processor.specInt + ", " + root.processor.specFp);

        saveToFile(root.middle, writer);

        saveToFile(root.right, writer);
    }
}
