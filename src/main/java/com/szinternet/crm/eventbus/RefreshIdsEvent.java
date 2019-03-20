package com.szinternet.crm.eventbus;

public class RefreshIdsEvent {
    public RefreshIdsEvent(String ids) {
        this.ids = ids;
    }

    public String ids;
}
