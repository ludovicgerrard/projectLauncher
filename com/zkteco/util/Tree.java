package com.zkteco.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tree<E> {
    private Tree<E>.Node lastParent;
    private Tree<E>.Node root;

    private class Node {
        public List<Tree<E>.Node> children;
        public E element;
        public Tree<E>.Node parent;

        public Node(E e, Tree<E>.Node node) {
            this.element = e;
            this.parent = node;
        }

        public void add(E e, Tree<E>.Node node) {
            if (this.children == null) {
                this.children = new ArrayList();
            }
            this.children.add(new Node(e, node));
        }
    }

    public boolean add(E e, E e2) {
        if (e == null) {
            this.root = new Node(e2, (Tree<E>.Node) null);
            return true;
        }
        Tree<E>.Node node = this.lastParent;
        if (node == null || !node.element.equals(e)) {
            Tree<E>.Node node2 = getNode(this.root, e);
            if (node2 != null) {
                node2.add(e2, node2);
                this.lastParent = node2;
            }
            if (node2 != null) {
                return true;
            }
            return false;
        }
        Tree<E>.Node node3 = this.lastParent;
        node3.add(e2, node3);
        return true;
    }

    public Tree<E> getChildren(E e) {
        Tree<E>.Node node = getNode(this.root, e);
        if (node == null) {
            return null;
        }
        Tree<E> tree = new Tree<>();
        tree.root = node;
        return tree;
    }

    public List<E> getDirectChildren(E e) {
        Tree<E>.Node node = getNode(this.root, e);
        if (node == null || node.children == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(node.children.size());
        for (Tree<E>.Node node2 : node.children) {
            arrayList.add(node2.element);
        }
        return arrayList;
    }

    private Tree<E>.Node getNode(Tree<E>.Node node, E e) {
        if (node.element.equals(e)) {
            return node;
        }
        Tree<E>.Node node2 = null;
        if (node.children != null) {
            Iterator<Tree<E>.Node> it = node.children.iterator();
            while (node2 == null && it.hasNext()) {
                node2 = getNode(it.next(), e);
            }
        }
        return node2;
    }

    public E getParent(E e) {
        Tree<E>.Node node = getNode(this.root, e);
        if (node != null) {
            return node.parent.element;
        }
        return null;
    }

    public void print() {
        System.out.println("Juaslol");
    }
}
