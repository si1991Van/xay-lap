package com.viettel.construction.appbase;

import java.util.List;

/***
 * KePham
 * @param <Parrent>
 * @param <Child>
 */
public class ExpandableListModel<Parrent,Child> {
    private Parrent parrent;
    private List<Child> children;

    public ExpandableListModel(){}
    public ExpandableListModel(Parrent parrent, List<Child> children) {
        this.parrent = parrent;
        this.children = children;
    }

    public Parrent getParrent() {
        return parrent;
    }

    public void setParrent(Parrent parrent) {
        this.parrent = parrent;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }
}
