package com.night.nightcourse.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(tableName = Animal.TABLE_NAME)
data class Animal(
    @ColumnInfo(name = "ALBUM_FILE") var album_file: String = "",
    @Ignore var album_update: String = "",
    @ColumnInfo var animal_age: String = "",
    @Ignore var animal_area_pkid: Int = 0,
    @Ignore var animal_bacterin: String = "",
    @Ignore var animal_bodytype: String = "",
    @Ignore var animal_caption: String = "",
    @Ignore var animal_closeddate: String = "",
    @Ignore var animal_colour: String = "",
    @Ignore var animal_createtime: String = "",
    @Ignore var animal_foundplace: String = "",
    @PrimaryKey(autoGenerate = true) var animal_id: Int = 0,
    @ColumnInfo(name = "ANIMAL_KIND") var animal_kind: String= "",
    @Ignore var animal_opendate: String= "",
    @Ignore var animal_place: String = "",
    @Ignore var animal_remark: String = "",
    @ColumnInfo(name = "ANIMAL_SEX") var animal_sex: String = "",
    @Ignore var animal_shelter_pkid: Int = 0,
    @Ignore var animal_status: String = "",
    @Ignore var animal_sterilization: String = "",
    @Ignore var animal_subid: String = "",
    @ColumnInfo(name = "ANIMAL_TITTLE") var animal_title: String = "",
    @Ignore var animal_update: String = "",
    @Ignore var cDate: String? = "",
    @Ignore var shelter_address: String = "",
    @ColumnInfo(name = "SHELTER_NAME") var shelter_name: String = "",
    @ColumnInfo(name = "SHELTER_TEL") var shelter_tel: String = ""
){
    companion object{
        const val TABLE_NAME = "animals"
    }
}