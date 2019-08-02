package ru.n00ner.musicradioapp.datasource.model

import com.google.gson.annotations.SerializedName

data class StreamResponse (
    @SerializedName("id") val id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("is_active") val is_active : Boolean,
    @SerializedName("poster") val poster : String,
    @SerializedName("duration") val duration : Int,
    @SerializedName("mediafiles_count") val mediafiles_count : Int,
    @SerializedName("stream_url") val stream_url : String,
    @SerializedName("is_template") val is_template : Boolean,
    @SerializedName("rightholder") val rightholder : Int,
    @SerializedName("rightholder_name") val rightholder_name : String,
    @SerializedName("user_first_name") val user_first_name : String,
    @SerializedName("genres") val genres : List<Int>,
    @SerializedName("demo_urls") val demo_urls : List<String>,
    @SerializedName("link_name") val link_name : String,
    @SerializedName("ctype") val ctype : String,
    @SerializedName("last_listen_time") val last_listen_time : String,
    @SerializedName("not_listen_degree") val not_listen_degree : String,
    @SerializedName("timezone") val timezone : String,
    @SerializedName("user_id") val user_id : Int,
    @SerializedName("bitrate") val bitrate : Int,
    @SerializedName("amplify_mediafile") val amplify_mediafile : Int,
    @SerializedName("amplify_promo") val amplify_promo : Int,
    @SerializedName("is_suspended") val is_suspended : Boolean,
    @SerializedName("block_pattern") val block_pattern : List<List<String>>,
    @SerializedName("crm") val crm : List<String>,
    @SerializedName("normalize") val normalize : Boolean,
    @SerializedName("weather_mode") val weather_mode : Boolean
)