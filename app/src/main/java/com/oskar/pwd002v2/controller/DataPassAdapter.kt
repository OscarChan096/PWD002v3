package com.oskar.pwd002v2.controller

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.oskar.pwd002v2.obj.dataPass

class DataPassAdapter : TypeAdapter<dataPass>() {
    override fun write(out: JsonWriter, value: dataPass) {
        out.beginObject()
        out.name("id").value(value.ID.toString())
        out.name("title").value(value.TITLE)
        out.name("username").value(value.USERNAME)
        out.name("userpassword").value(value.USERPASSWORD)
        out.name("fechmodif").value(value.FECHMODIF)
        out.endObject()
    }

    override fun read(`in`: JsonReader): dataPass {
        var id: Int? = null
        var title: String? = null
        var username: String? = null
        var userpassword: String? = null
        var fechmodif: String? = null
        // Lee otros campos según sea necesario

        `in`.beginObject()
        while (`in`.hasNext()) {
            when (`in`.nextName()) {
                "id" -> id = `in`.nextInt()
                "title" -> title = `in`.nextString()
                "username" -> username = `in`.nextString()
                "userpassword" -> userpassword = `in`.nextString()
                "fechmodif" -> fechmodif = `in`.nextString()
            }
        }
        `in`.endObject()
        return dataPass(id, title, username, userpassword, fechmodif)
    }
}
