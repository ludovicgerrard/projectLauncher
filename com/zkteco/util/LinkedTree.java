package com.zkteco.util;

import java.lang.Comparable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class LinkedTree<E extends Comparable<? super E>> {
    private final Map<E, LinkedTree<E>.TreeNode> quickAccessMap = new HashMap();
    private LinkedTree<E>.TreeNode root;

    private class Counter {
        public int count;

        private Counter() {
        }
    }

    private class TreeNode implements Comparable<LinkedTree<E>.TreeNode> {
        private Map<E, Integer> childIndex = new HashMap();
        /* access modifiers changed from: private */
        public List<E> childrenElements;
        /* access modifiers changed from: private */
        public List<LinkedTree<E>.TreeNode> childrenNodes;
        public int depth = -1;
        public E element;
        public LinkedTree<E>.TreeNode parent;

        protected TreeNode(E e, LinkedTree<E>.TreeNode treeNode, int i) {
            this.element = e;
            this.parent = treeNode;
            this.depth = i;
        }

        public LinkedTree<E>.TreeNode addChildren(E e) {
            if (this.childrenNodes == null) {
                this.childrenNodes = new ArrayList();
                this.childrenElements = new ArrayList();
                this.childIndex = new HashMap();
            }
            int size = this.childIndex.size();
            Integer num = this.childIndex.get(e);
            if ((num == null || num.intValue() == -1) ? false : true) {
                return this.childrenNodes.get(num.intValue());
            }
            this.childrenElements.add(e);
            LinkedTree<E>.TreeNode treeNode = new TreeNode(e, this, this.depth + 1);
            this.childrenNodes.add(treeNode);
            this.childIndex.put(e, Integer.valueOf(size));
            return treeNode;
        }

        public LinkedTree<E>.TreeNode addChildren(LinkedTree<E>.TreeNode treeNode) {
            if (this.childrenNodes == null) {
                this.childrenNodes = new ArrayList();
                this.childrenElements = new ArrayList();
                this.childIndex = new HashMap();
            }
            int size = this.childIndex.size();
            Integer num = this.childIndex.get(this.element);
            if (!((num == null || num.intValue() == -1) ? false : true)) {
                this.childrenElements.add(treeNode.element);
                this.childrenNodes.add(treeNode);
                this.childIndex.put(this.element, Integer.valueOf(size));
            }
            return treeNode;
        }

        public int compareTo(LinkedTree<E>.TreeNode treeNode) {
            return this.element.compareTo(treeNode.element);
        }

        public LinkedTree<E>.TreeNode removeChild(LinkedTree<E>.TreeNode treeNode) {
            List<LinkedTree<E>.TreeNode> list = this.childrenNodes;
            if (list == null) {
                return null;
            }
            list.remove(treeNode);
            this.childrenElements.remove(treeNode.element);
            this.childIndex.remove(treeNode.element);
            return null;
        }

        public String toString() {
            return this.element.toString();
        }
    }

    public class TreeNodeDescription {
        public int childrenCount;
        public int depth = -1;
        public E element;

        public TreeNodeDescription(E e, int i, int i2) {
            this.element = e;
            this.depth = i;
            this.childrenCount = i2;
        }

        public boolean equals(Object obj) {
            return this.element.equals(obj);
        }

        public int hashCode() {
            return this.element.hashCode();
        }
    }

    public void add(E e, E e2) throws NoSuchElementException {
        if (e != null) {
            this.quickAccessMap.put(e2, getNodeFromQuickMap(e).addChildren(e2));
            return;
        }
        throw new NoSuchElementException("Null parent");
    }

    public void clear() {
        this.root = null;
        this.quickAccessMap.clear();
    }

    public boolean exists(E e) {
        return this.quickAccessMap.get(e) != null;
    }

    public E get(E e, int i) throws UnsupportedOperationException, IndexOutOfBoundsException {
        LinkedTree<E>.TreeNode nodeFromQuickMap = getNodeFromQuickMap(e);
        if (nodeFromQuickMap.childrenElements != null) {
            return (Comparable) nodeFromQuickMap.childrenElements.get(i);
        }
        throw new IndexOutOfBoundsException("This parent has no children");
    }

    public List<E> getChildren(E e) throws NoSuchElementException {
        return getNodeFromQuickMap(e).childrenElements;
    }

    public LinkedTree<E> getChildTree(E e) throws NoSuchElementException {
        LinkedTree<E>.TreeNode nodeFromQuickMap = getNodeFromQuickMap(e);
        LinkedTree<E> linkedTree = new LinkedTree<>();
        linkedTree.setRoot(nodeFromQuickMap.element);
        getChildTreeRecursive(nodeFromQuickMap, linkedTree);
        return linkedTree;
    }

    private void getChildTreeRecursive(LinkedTree<E>.TreeNode treeNode, LinkedTree<E> linkedTree) {
        if (treeNode.childrenNodes != null && !treeNode.childrenNodes.isEmpty()) {
            for (TreeNode treeNode2 : treeNode.childrenNodes) {
                linkedTree.add(treeNode.element, treeNode2.element);
                getChildTreeRecursive(treeNode2, linkedTree);
            }
        }
    }

    public E getLinearByDepth(int i) {
        Counter counter = new Counter();
        counter.count = i;
        return getLinearByDepthRecursive(this.root, counter);
    }

    private E getLinearByDepthRecursive(LinkedTree<E>.TreeNode treeNode, LinkedTree<E>.Counter counter) throws IndexOutOfBoundsException {
        if (counter.count == 0) {
            return treeNode.element;
        }
        if (treeNode.childrenNodes == null) {
            return null;
        }
        for (TreeNode linearByDepthRecursive : treeNode.childrenNodes) {
            counter.count--;
            E linearByDepthRecursive2 = getLinearByDepthRecursive(linearByDepthRecursive, counter);
            if (linearByDepthRecursive2 != null) {
                return linearByDepthRecursive2;
            }
        }
        return null;
    }

    private LinkedTree<E>.TreeNode getNodeFromQuickMap(E e) throws NoSuchElementException {
        LinkedTree<E>.TreeNode treeNode = this.quickAccessMap.get(e);
        if (treeNode != null) {
            return treeNode;
        }
        throw new NoSuchElementException("Parent does not exist");
    }

    public List<E> getParents(E e) throws NoSuchElementException {
        LinkedTree<E>.TreeNode nodeFromQuickMap = getNodeFromQuickMap(e);
        ArrayList arrayList = new ArrayList();
        getParentsRecursive(nodeFromQuickMap, arrayList);
        return arrayList;
    }

    private void getParentsRecursive(LinkedTree<E>.TreeNode treeNode, List<E> list) {
        if (treeNode.parent != null) {
            list.add(treeNode.parent.element);
            getParentsRecursive(treeNode.parent, list);
        }
    }

    public E getRoot() {
        return this.root.element;
    }

    public List<LinkedTree<E>.TreeNodeDescription> getTreeDescription() {
        ArrayList arrayList = new ArrayList();
        getTreeDescription(this.root, arrayList);
        return arrayList;
    }

    private void getTreeDescription(LinkedTree<E>.TreeNode treeNode, List<LinkedTree<E>.TreeNodeDescription> list) {
        if (treeNode == null) {
            return;
        }
        if (treeNode.childrenNodes != null) {
            list.add(new TreeNodeDescription(treeNode.element, treeNode.depth, treeNode.childrenNodes.size()));
            for (TreeNode treeDescription : treeNode.childrenNodes) {
                getTreeDescription(treeDescription, list);
            }
            return;
        }
        list.add(new TreeNodeDescription(treeNode.element, treeNode.depth, 0));
    }

    public void move(E e, E e2) throws NoSuchElementException, UnsupportedOperationException {
        LinkedTree<E>.TreeNode nodeFromQuickMap = getNodeFromQuickMap(e);
        if (e != this.root) {
            LinkedTree<E>.TreeNode treeNode = nodeFromQuickMap.parent;
            if (!treeNode.equals(getNodeFromQuickMap(e2))) {
                treeNode.removeChild(nodeFromQuickMap);
                LinkedTree<E>.TreeNode nodeFromQuickMap2 = getNodeFromQuickMap(e2);
                nodeFromQuickMap.parent = nodeFromQuickMap2;
                nodeFromQuickMap2.addChildren(nodeFromQuickMap);
                return;
            }
            return;
        }
        throw new UnsupportedOperationException("Cannot move root");
    }

    public void printDebug() {
        printDebugRecursive(this.root, "");
    }

    private void printDebugRecursive(LinkedTree<E>.TreeNode treeNode, String str) {
        if (treeNode != null) {
            System.out.println(str + "-> " + treeNode.toString());
            if (treeNode.childrenNodes != null) {
                for (TreeNode printDebugRecursive : treeNode.childrenNodes) {
                    printDebugRecursive(printDebugRecursive, str + "----");
                }
            }
        }
    }

    public void setRoot(E e) {
        LinkedTree<E>.TreeNode treeNode = this.root;
        if (treeNode == null) {
            this.root = new TreeNode(e, (LinkedTree<E>.TreeNode) null, 0);
        } else {
            this.quickAccessMap.remove(treeNode.toString());
            this.root.element = e;
        }
        this.quickAccessMap.put(e, this.root);
    }

    public int size() {
        return this.quickAccessMap.keySet().size();
    }

    public void sort() {
        sortRecursive(this.root);
    }

    private void sortRecursive(LinkedTree<E>.TreeNode treeNode) {
        if (treeNode != null && treeNode.childrenNodes != null) {
            Collections.sort(treeNode.childrenElements);
            Collections.sort(treeNode.childrenNodes);
            for (TreeNode sortRecursive : treeNode.childrenNodes) {
                sortRecursive(sortRecursive);
            }
        }
    }
}
