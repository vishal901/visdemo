package com.vishalandroid.visdemo.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vishal on 26/9/16.
 */
public class abc {


    /**
     * table_name : users
     * timestamp : 1474865571582
     * labels : {"id":"ID","name":"Name","address":"Address","gender":"Gender","photo_id":"Photo ID","latitude_longitude":"Latitude Longitude","activity":"Activity"}
     * data : [{"id":"1","name":"Hit","address":"bapunager","gender":"Male","photo_id":"https://static.pexels.com/photos/215/road-sky-clouds-cloudy-large.jpg","latitude_longitude":"23.0386956,72.63075329999992","activity":"Cricket, Basketball, Swimming, Shooting, Music"},{"id":"2","name":"miral","address":"khokhara","gender":"Male","photo_id":"https://static.pexels.com/photos/2324/skyline-buildings-new-york-skyscrapers-large.jpg","latitude_longitude":"23.1134605,70.02601320000008","activity":"Cricket,  Shooting, Music"},{"id":"3","name":"bhagyesh","address":"paladi","gender":"Male","photo_id":"https://static.pexels.com/photos/28452/pexels-photo-28452-large.jpg","latitude_longitude":"23.0134135,72.56240950000006","activity":"Cricket, Music"},{"id":"4","name":"dinesh","address":"jivaraj park","gender":"Male","photo_id":"https://static.pexels.com/photos/38203/pexels-photo-38203-large.jpeg","latitude_longitude":"23.0053051,72.53675040000007","activity":"Shooting, Music"},{"id":"5","name":"neel","address":"vastrapur","gender":"Male","photo_id":"http://i.telegraph.co.uk/multimedia/archive/03543/Narutos_selfie_344_3543114b.jpg","latitude_longitude":"23.0350073,72.52932439999995","activity":"Cricket, Basketball, Shooting, Music"}]
     */

    @SerializedName("table_name")
    private String tableName;
    @SerializedName("timestamp")
    private long timestamp;
    /**
     * id : ID
     * name : Name
     * address : Address
     * gender : Gender
     * photo_id : Photo ID
     * latitude_longitude : Latitude Longitude
     * activity : Activity
     */

    @SerializedName("labels")
    private LabelsEntity labels;
    @SerializedName("data")
    private List<LabelsEntity> data;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public LabelsEntity getLabels() {
        return labels;
    }

    public void setLabels(LabelsEntity labels) {
        this.labels = labels;
    }

    public List<LabelsEntity> getData() {
        return data;
    }

    public void setData(List<LabelsEntity> data) {
        this.data = data;
    }

    public static class LabelsEntity {
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("address")
        private String address;
        @SerializedName("gender")
        private String gender;
        @SerializedName("photo_id")
        private String photoId;
        @SerializedName("latitude_longitude")
        private String latitudeLongitude;
        @SerializedName("activity")
        private String activity;

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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getPhotoId() {
            return photoId;
        }

        public void setPhotoId(String photoId) {
            this.photoId = photoId;
        }

        public String getLatitudeLongitude() {
            return latitudeLongitude;
        }

        public void setLatitudeLongitude(String latitudeLongitude) {
            this.latitudeLongitude = latitudeLongitude;
        }

        public String getActivity() {
            return activity;
        }

        public void setActivity(String activity) {
            this.activity = activity;
        }
    }
}
