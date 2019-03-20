package com.szinternet.crm.databean;

import java.util.List;

public class MembersBean {

    /**
     * data : [{"img_url":null,"ids":"15","fenrun":0,"username":"15871977755"},{"img_url":null,"ids":"16","fenrun":0,"username":"15871977754"},{"img_url":null,"ids":"17","fenrun":0,"username":"13872898423"},{"img_url":"/upload/Api/img/default.jpg","ids":"28","fenrun":"65.00","username":"13571977750"}]
     */
    public List<DataEntity> data;

    public static class DataEntity {
        /**
         * img_url : null
         * ids : 15
         * fenrun : 0
         * username : 15871977755
         */
        public String img_url;
        public String ids;
        public int fenrun;
        public String username;

        public DataEntity(String img_url, String ids, int fenrun, String username) {
            this.img_url = img_url;
            this.ids = ids;
            this.fenrun = fenrun;
            this.username = username;
        }
    }
}
