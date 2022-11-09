package com.andrew.application.mode.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ContentModel {


    public ContentDetailInfoBean contentDetailInfo;
    public List<FileListBean> fileList;
    public CreateUserInfoBean createUserInfo;
    public Sport sport;

    public String type;// picture view  sport
    public int likeCount;// picture view  sport
    public int collectionCount;// picture view  sport
    @SerializedName("dpCount")
    public int commentCount;// picture view  sport
    public boolean likeStatu;
    public boolean collectStatu;
    public int followStatu;
    public static class ContentDetailInfoBean {
        public int id;
        public String title;
        public String key_words;
        public String creator_uid;
        public String creator_name;
        public String create_time;
        public String update_time;
        public int audit_status;
        public int if_del;
        public String location;
        public int lon;
        public int lat;
        public String city_name;
        public int likeNum;
        public int collectNum;
    }

   
    public static class CreateUserInfoBean {
        public int id;
        public String userId;
        public String name;
        public String displayName;
        public int gender;
        public String portrait;
        public String mobile;
        public String email;
        public String address;
        public String company;
        public String social;
        public String salt;
        public String extra;
        public String type;
        public Long dt;
        public String createTime;
        public int deleted;
        public String uid;
        public int age;
        public String token;
        public int joinstatus;
    }


    public static class FileListBean {
        public int id;
        public int content_id;
        public String file_type;
        public String file_url;
        public String file_name;
        public int if_del;
        public String create_time;
        public String update_time;
        public String video_pic ;
    }
}
