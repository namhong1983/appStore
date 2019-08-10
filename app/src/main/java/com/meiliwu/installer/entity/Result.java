package com.meiliwu.installer.entity;

import java.util.List;

/**
 * Author:  ZhangTao
 * Date: 2018/3/5.
 */

public class Result<T>{

    /**
     * data : {"statistic":{"count":11},"data":[{"id":"12","application_name":"南京麦风","application_describe":null,"icon_url":"http://cdn.mse.mlwplus.com/meiliwu/applications/2017/11/30/09/50/Spotlight.png","bundle_id":"com.eallcn.chowRentAgent","testing_bundle_id":"com.eallcn.chowRentAgentDev","if_deleted":"0","create_time":"1512006618","update_time":"1512006618"},{"id":"11","application_name":"美丽帮","application_describe":"供应商App","icon_url":"http://cdn.mse.mlwplus.com/meiliwu/applications/2017/09/19/13/43/Spotlight.png","bundle_id":"com.meiliwu.dragon","testing_bundle_id":"com.meiliwu.dragon","if_deleted":"0","create_time":"1505376822","update_time":"1505799829"},{"id":"10","application_name":"典盛","application_describe":null,"icon_url":"http://cdn.mse.mlwplus.com/meiliwu/applications/2017/04/24/18/35/Spotlight.png","bundle_id":"com.eallcn.chowRentAgent","testing_bundle_id":"com.eallcn.chowRentAgentDev","if_deleted":"0","create_time":"1493030110","update_time":"1493030110"},{"id":"9","application_name":"云龙腾","application_describe":null,"icon_url":"http://cdn.mse.mlwplus.com/meiliwu/applications/2017/02/21/11/10/Spotlight.png","bundle_id":"com.meiliwu.mse","testing_bundle_id":null,"if_deleted":"0","create_time":"1487646641","update_time":"1487646641"},{"id":"8","application_name":"广发","application_describe":null,"icon_url":"http://cdn.mse.mlwplus.com/meiliwu/applications/2017/02/21/11/09/Spotlight.png","bundle_id":"com.meiliwu.mse","testing_bundle_id":null,"if_deleted":"0","create_time":"1487646588","update_time":"1487646588"},{"id":"7","application_name":"华远嘉禾","application_describe":null,"icon_url":"http://cdn.mse.mlwplus.com/meiliwu/applications/2017/02/21/11/08/Spotlight.png","bundle_id":"com.meiliwu.mse","testing_bundle_id":null,"if_deleted":"0","create_time":"1487646526","update_time":"1487646526"},{"id":"6","application_name":"小家联行","application_describe":null,"icon_url":"http://cdn.mse.mlwplus.com/meiliwu/applications/2017/02/21/11/07/Spotlight.png","bundle_id":"com.meiliwu.xiaojia","testing_bundle_id":null,"if_deleted":"0","create_time":"1487646410","update_time":"1487646436"},{"id":"4","application_name":"管家宝（简约版）","application_describe":null,"icon_url":"http://cdn.mse.mlwplus.com/meiliwu/applications/2017/01/09/15/12/Spotlight.png","bundle_id":null,"testing_bundle_id":null,"if_deleted":"0","create_time":"1483521023","update_time":"1483945969"},{"id":"3","application_name":"管家宝通用版","application_describe":null,"icon_url":"http://cdn.mse.mlwplus.com/meiliwu/applications/2017/01/09/15/08/Spotlight.png","bundle_id":"com.meiliwu.mse","testing_bundle_id":"com.meiliwu.mseDev","if_deleted":"0","create_time":"1483521006","update_time":"1490843921"},{"id":"2","application_name":"美丽屋C端","application_describe":"用户端，房源查询，在线联系等","icon_url":"http://cdn.mse.mlwplus.com/meiliwu/applications/2017/01/09/15/10/Spotlight.png","bundle_id":"com.eallcn.chowRent","testing_bundle_id":"com.eallcn.chowRentDev","if_deleted":"0","create_time":"1483520996","update_time":"1490533263"},{"id":"1","application_name":"美丽屋经纪人","application_describe":"经纪人使用的app，业绩神器","icon_url":"http://cdn.mse.mlwplus.com/meiliwu/applications/2017/01/09/15/07/Spotlight.png","bundle_id":"com.eallcn.chowRentAgent","testing_bundle_id":"com.eallcn.chowRentAgentDev","if_deleted":"0","create_time":"1483520965","update_time":"1508918173"}]}
     * code : 0
     */

    private ResultBean<T> data;
    private int code;
    private String message;

    public ResultBean getData() {
        return data;
    }

    public void setData(ResultBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class ResultBean<T> {
        /**
         * statistic : {"count":11}
         * data : [{"id":"12","application_name":"南京麦风","application_describe":null,"icon_url":"http://cdn.mse.mlwplus.com/meiliwu/applications/2017/11/30/09/50/Spotlight.png","bundle_id":"com.eallcn.chowRentAgent","testing_bundle_id":"com.eallcn.chowRentAgentDev","if_deleted":"0","create_time":"1512006618","update_time":"1512006618"},{"id":"11","application_name":"美丽帮","application_describe":"供应商App","icon_url":"http://cdn.mse.mlwplus.com/meiliwu/applications/2017/09/19/13/43/Spotlight.png","bundle_id":"com.meiliwu.dragon","testing_bundle_id":"com.meiliwu.dragon","if_deleted":"0","create_time":"1505376822","update_time":"1505799829"},{"id":"10","application_name":"典盛","application_describe":null,"icon_url":"http://cdn.mse.mlwplus.com/meiliwu/applications/2017/04/24/18/35/Spotlight.png","bundle_id":"com.eallcn.chowRentAgent","testing_bundle_id":"com.eallcn.chowRentAgentDev","if_deleted":"0","create_time":"1493030110","update_time":"1493030110"},{"id":"9","application_name":"云龙腾","application_describe":null,"icon_url":"http://cdn.mse.mlwplus.com/meiliwu/applications/2017/02/21/11/10/Spotlight.png","bundle_id":"com.meiliwu.mse","testing_bundle_id":null,"if_deleted":"0","create_time":"1487646641","update_time":"1487646641"},{"id":"8","application_name":"广发","application_describe":null,"icon_url":"http://cdn.mse.mlwplus.com/meiliwu/applications/2017/02/21/11/09/Spotlight.png","bundle_id":"com.meiliwu.mse","testing_bundle_id":null,"if_deleted":"0","create_time":"1487646588","update_time":"1487646588"},{"id":"7","application_name":"华远嘉禾","application_describe":null,"icon_url":"http://cdn.mse.mlwplus.com/meiliwu/applications/2017/02/21/11/08/Spotlight.png","bundle_id":"com.meiliwu.mse","testing_bundle_id":null,"if_deleted":"0","create_time":"1487646526","update_time":"1487646526"},{"id":"6","application_name":"小家联行","application_describe":null,"icon_url":"http://cdn.mse.mlwplus.com/meiliwu/applications/2017/02/21/11/07/Spotlight.png","bundle_id":"com.meiliwu.xiaojia","testing_bundle_id":null,"if_deleted":"0","create_time":"1487646410","update_time":"1487646436"},{"id":"4","application_name":"管家宝（简约版）","application_describe":null,"icon_url":"http://cdn.mse.mlwplus.com/meiliwu/applications/2017/01/09/15/12/Spotlight.png","bundle_id":null,"testing_bundle_id":null,"if_deleted":"0","create_time":"1483521023","update_time":"1483945969"},{"id":"3","application_name":"管家宝通用版","application_describe":null,"icon_url":"http://cdn.mse.mlwplus.com/meiliwu/applications/2017/01/09/15/08/Spotlight.png","bundle_id":"com.meiliwu.mse","testing_bundle_id":"com.meiliwu.mseDev","if_deleted":"0","create_time":"1483521006","update_time":"1490843921"},{"id":"2","application_name":"美丽屋C端","application_describe":"用户端，房源查询，在线联系等","icon_url":"http://cdn.mse.mlwplus.com/meiliwu/applications/2017/01/09/15/10/Spotlight.png","bundle_id":"com.eallcn.chowRent","testing_bundle_id":"com.eallcn.chowRentDev","if_deleted":"0","create_time":"1483520996","update_time":"1490533263"},{"id":"1","application_name":"美丽屋经纪人","application_describe":"经纪人使用的app，业绩神器","icon_url":"http://cdn.mse.mlwplus.com/meiliwu/applications/2017/01/09/15/07/Spotlight.png","bundle_id":"com.eallcn.chowRentAgent","testing_bundle_id":"com.eallcn.chowRentAgentDev","if_deleted":"0","create_time":"1483520965","update_time":"1508918173"}]
         */
//
        private StatisticBean statistic;
        private T data;

        public StatisticBean getStatistic() {
            return statistic;
        }

        public void setStatistic(StatisticBean statistic) {
            this.statistic = statistic;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public static class StatisticBean {
            /**
             * count : 11
             */

            private int count;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }
        }


    }
}
