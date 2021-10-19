package azmithabet.com.news.data.db

import androidx.room.TypeConverter
import azmithabet.com.news.data.model.artical.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source):String{
        return source.name
    }
    @TypeConverter
    fun toSource(name:String):Source{
        return Source(name,name)
    }
}