package com.szinternet.crm.databean;

import java.util.List;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class RankBean {

    /**
     * data : [{"img_url":"/upload/Api/img/11515480384.jpg","grade":"VIP","people":1,"username":"158****750"},{"people":null,"username":null},{"people":null,"username":null},{"people":null,"username":null},{"people":null,"username":null},{"people":null,"username":null},{"people":null,"username":null},{"people":null,"username":null},{"people":null,"username":null},{"people":null,"username":null}]
     */
    public List<DataEntity> data;


    public static class DataEntity {
        /**
         * img_url : /upload/Api/img/11515480384.jpg
         * grade : VIP
         * people : 1
         * username : 158****750
         */
        public String img_url;
        public String grade;
        public int people;
        public String username;

        public DataEntity(String img_url, String grade, int people, String username) {
            this.img_url = img_url;
            this.grade = grade;
            this.people = people;
            this.username = username;
        }
    }
}
