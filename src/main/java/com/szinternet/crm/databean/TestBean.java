package com.szinternet.crm.databean;

import java.util.List;

/**
 * @version $Rev$
 * @company zqb
 * @time 2017/12/30 17:30
 * @des ${TODO}
 * @updateAuthor zqb
 * @updateDate 2017/12/30 17:30
 * @updateDes ${TODO}
 */
public class TestBean {

    /**
     * result : [{"reportId":0,"portalId":2,"name":"主题一","themeId":1,"sort":0,"id":4,"type":0,"isChecked":false,"childs":[{"reportId":1,"portalId":2,"name":"2556","themeId":1,"sort":0,"id":10,"type":2,"isChecked":false,"childs":null,"parentId":4},{"reportId":2,"portalId":2,"name":"454","themeId":1,"sort":0,"id":11,"type":2,"isChecked":false,"childs":null,"parentId":4}],"parentId":0}]
     * success : true
     * __abp : true
     * error : null
     * targetUrl : null
     * unAuthorizedRequest : false
     */
    public List<ResultEntity> result;
    public boolean success;
    public boolean __abp;
    public String error;
    public String targetUrl;
    public boolean unAuthorizedRequest;

    public static class ResultEntity {
        /**
         * reportId : 0
         * portalId : 2
         * name : 主题一
         * themeId : 1
         * sort : 0
         * id : 4
         * type : 0
         * isChecked : false
         * childs : [{"reportId":1,"portalId":2,"name":"2556","themeId":1,"sort":0,"id":10,"type":2,"isChecked":false,"childs":null,"parentId":4},{"reportId":2,"portalId":2,"name":"454","themeId":1,"sort":0,"id":11,"type":2,"isChecked":false,"childs":null,"parentId":4}]
         * parentId : 0
         */
        public int reportId;
        public int portalId;
        public String name;
        public int themeId;
        public int sort;
        public int id;
        public int type;
        public boolean isChecked;
        public List<ChildsEntity> childs;
        public int parentId;

        public static class ChildsEntity {
            /**
             * reportId : 1
             * portalId : 2
             * name : 2556
             * themeId : 1
             * sort : 0
             * id : 10
             * type : 2
             * isChecked : false
             * childs : null
             * parentId : 4
             */
            public int reportId;
            public int portalId;
            public String name;
            public int themeId;
            public int sort;
            public int id;
            public int type;
            public boolean isChecked;
            public String childs;
            public int parentId;

/*
            this.themes = result.items;
            this.showPaging(result, pageNumber);
            console.log(this.themes);
            if(position)
            for(let i=0;i< this.themes.length;i++){
                if(this.themes[i].id===result[i].id){
                    this.imagePath=API_PATH+this.themes[i].fileAddress;
                    console.log(this.imagePath);
                    return;
                }


            }*/

        }
    }
}
