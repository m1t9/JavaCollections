package com.javarush.task.task20.task2028;

import java.io.Serializable;
import java.util.*;

/* 
Построй дерево(1)
*/
public class CustomTree extends AbstractList<String> implements Cloneable, Serializable {

    Entry<String> root;
    int currentParent = 0;

    ArrayList<Entry> nodeList;

    public CustomTree() {
        this.root = new Entry<>("root");
        root.parent = root;
        root.parent.elementName = "root";
        nodeList = new ArrayList<>(Arrays.asList(root));
    }

    @Override
    public boolean add(String elementName) {
        if (nodeList.size() == 0) nodeList = new ArrayList<>(Arrays.asList(root));

        if (nodeList.get(currentParent).availableToAddLeftChildren) {
            nodeList.get(currentParent).availableToAddLeftChildren = false;
            nodeList.add(new Entry(elementName));
            nodeList.get(size()).parent = nodeList.get(currentParent);
            nodeList.get(currentParent).leftChild = nodeList.get(size());
            return true;
        } else {
            nodeList.get(currentParent).availableToAddRightChildren = false;
            nodeList.add(new Entry(elementName));
            nodeList.get(size()).parent = nodeList.get(currentParent);
            nodeList.get(currentParent).rightChild = nodeList.get(size());
            currentParent++;
            return true;
        }
    }

    @Override
    public boolean remove(Object o) {
        if (!o.getClass().getSimpleName().equals("String"))
            throw new UnsupportedOperationException();
        removeChilds(o);
        removeNodes(o);
        return true;
    }

    private void removeNodes(Object o) {
        List<String> toDeleteNames = new ArrayList<>();
        List<Integer> toDeleteCounts = new ArrayList<>();
        toDeleteNames.add(o.toString());
        int count = 0;
        for (Entry entry : nodeList) {
            if (toDeleteNames.contains(entry.elementName)) {
                if (!entry.availableToAddLeftChildren) {
                    toDeleteNames.add(entry.leftChild.elementName);
                    entry.availableToAddLeftChildren = true;
                }
                if (!entry.availableToAddRightChildren) {
                    toDeleteNames.add(entry.rightChild.elementName);
                    entry.availableToAddRightChildren = true;
                }
                toDeleteCounts.add(count);
                if (count == currentParent) currentParent++;
            }
            count++;
        }
        int deleter = 0;
        for (Integer i : toDeleteCounts) {
            nodeList.remove(i - deleter);
            deleter++;
            if (i - deleter < currentParent) {
                currentParent--;
            }
        }
        if (nodeList.size() <= currentParent) currentParent = nodeList.size() - 2;
        if (currentParent < 0) currentParent = 0;
    }

    private void removeChilds(Object o) {
        for (Entry entry : this.nodeList) {
            if (!entry.availableToAddLeftChildren) {
                if (entry.leftChild.elementName.equals(o)) {
                    entry.availableToAddLeftChildren = true;
                    entry.leftChild = null;
                }
            }
            if (!entry.availableToAddRightChildren) {
                if (entry.rightChild.elementName.equals(o)) {
                    entry.availableToAddRightChildren = true;
                    entry.rightChild = null;
                    return;
                }
            }
        }

    }

    public String getParent(String name) {
        for (Entry entry : nodeList) {
            if (entry.elementName.equals(name)) {
                return entry.parent.elementName;
            }
        }
        return null;
    }

    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return nodeList.size() - 1;
    }

    static class Entry<T> implements Serializable {

        String elementName;
        boolean availableToAddLeftChildren, availableToAddRightChildren;
        Entry parent, leftChild, rightChild;

        public Entry(String elementName) {
            this.elementName = elementName;
            availableToAddLeftChildren = true;
            availableToAddRightChildren = true;
        }

        public boolean isAvailableToAddChildren() {
            return availableToAddLeftChildren || availableToAddRightChildren;
        }
    }
}
