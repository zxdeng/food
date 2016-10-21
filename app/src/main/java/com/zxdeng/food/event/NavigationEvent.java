package com.zxdeng.food.event;

/**
 * Desc：侧滑菜单事件
 * Created by Poison on 2016/10/21.
 */

public class NavigationEvent extends BaseEvent {

    private int id;

    public NavigationEvent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
