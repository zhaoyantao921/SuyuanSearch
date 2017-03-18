package com.shou.zhao.suyuansearch.dao;


import java.util.List;

public class Area {


    /**
     * status : 0
     * msg : ok
     * result : [{"id":"1","name":"北京","parentid":"0","parentname":"","areacode":"010","zipcode":"100000","depth":"1"},{"id":"2","name":"安徽","parentid":"0","parentname":"","areacode":"","zipcode":"","depth":"1"},{"id":"3","name":"福建","parentid":"0","parentname":"","areacode":"","zipcode":"","depth":"1"},{"id":"4","name":"甘肃","parentid":"0","parentname":"","areacode":"","zipcode":"","depth":"1"},{"id":"5","name":"广东","parentid":"0","parentname":"","areacode":"","zipcode":"","depth":"1"},{"id":"6","name":"广西","parentid":"0","parentname":"","areacode":"","zipcode":"","depth":"1"},{"id":"7","name":"贵州","parentid":"0","parentname":"","areacode":"","zipcode":"","depth":"1"},{"id":"8","name":"海南","parentid":"0","parentname":"","areacode":"","zipcode":"","depth":"1"},{"id":"9","name":"河北","parentid":"0","parentname":"","areacode":"","zipcode":"","depth":"1"},{"id":"10","name":"河南","parentid":"0","parentname":"","areacode":"","zipcode":"","depth":"1"},{"id":"11","name":"黑龙江","parentid":"0","parentname":"","areacode":"","zipcode":"","depth":"1"},{"id":"12","name":"湖北","parentid":"0","parentname":"","areacode":"","zipcode":"","depth":"1"},{"id":"13","name":"湖南","parentid":"0","parentname":"","areacode":"","zipcode":"","depth":"1"},{"id":"14","name":"吉林","parentid":"0","parentname":"","areacode":"","zipcode":"","depth":"1"},{"id":"15","name":"江苏","parentid":"0","parentname":"","areacode":"","zipcode":"","depth":"1"},{"id":"16","name":"江西","parentid":"0","parentname":"","areacode":"","zipcode":"","depth":"1"},{"id":"17","name":"辽宁","parentid":"0","parentname":"","areacode":"","zipcode":"","depth":"1"},{"id":"18","name":"内蒙古","parentid":"0","parentname":"","areacode":"","zipcode":"","depth":"1"},{"id":"19","name":"宁夏","parentid":"0","parentname":"","areacode":"","zipcode":"","depth":"1"},{"id":"20","name":"青海","parentid":"0","parentname":"","areacode":"","zipcode":"","depth":"1"},{"id":"21","name":"山东","parentid":"0","parentname":"","areacode":"","zipcode":"","depth":"1"},{"id":"22","name":"山西","parentid":"0","parentname":"","areacode":"","zipcode":"","depth":"1"},{"id":"23","name":"陕西","parentid":"0","parentname":"","areacode":"","zipcode":"","depth":"1"},{"id":"24","name":"上海","parentid":"0","parentname":"","areacode":"021","zipcode":"200000","depth":"1"},{"id":"25","name":"四川","parentid":"0","parentname":"","areacode":"","zipcode":"","depth":"1"},{"id":"26","name":"天津","parentid":"0","parentname":"","areacode":"022","zipcode":"300000","depth":"1"},{"id":"27","name":"西藏","parentid":"0","parentname":"","areacode":"","zipcode":"","depth":"1"},{"id":"28","name":"新疆","parentid":"0","parentname":"","areacode":"","zipcode":"","depth":"1"},{"id":"29","name":"云南","parentid":"0","parentname":"","areacode":"","zipcode":"","depth":"1"},{"id":"30","name":"浙江","parentid":"0","parentname":"","areacode":"","zipcode":"","depth":"1"},{"id":"31","name":"重庆","parentid":"0","parentname":"","areacode":"023","zipcode":"404100","depth":"1"},{"id":"32","name":"香港","parentid":"0","parentname":"","areacode":"00852","zipcode":"999077","depth":"1"},{"id":"33","name":"澳门","parentid":"0","parentname":"","areacode":"00853","zipcode":"999078","depth":"1"},{"id":"34","name":"台湾","parentid":"0","parentname":"","areacode":"00886","zipcode":"","depth":"1"},{"id":"51","name":"国外","parentid":"0","parentname":"","areacode":"","zipcode":"","depth":"1"}]
     */

    private String status;
    private String msg;
    private List<ResultBean> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 1
         * name : 北京
         * parentid : 0
         * parentname :
         * areacode : 010
         * zipcode : 100000
         * depth : 1
         */

        private String id;
        private String name;
        private String parentid;
        private String parentname;
        private String areacode;
        private String zipcode;
        private String depth;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        public String getParentname() {
            return parentname;
        }

        public void setParentname(String parentname) {
            this.parentname = parentname;
        }

        public String getAreacode() {
            return areacode;
        }

        public void setAreacode(String areacode) {
            this.areacode = areacode;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getDepth() {
            return depth;
        }

        public void setDepth(String depth) {
            this.depth = depth;
        }
    }
}
