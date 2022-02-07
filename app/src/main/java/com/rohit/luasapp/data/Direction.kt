package com.rohit.luasapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "direction")
@Parcelize
data class Direction(
    @field: Attribute(name = "name")
    var name: String = "",

    @field : ElementList(name = "tram", inline = true)
    var trams: List<Tram> = ArrayList()
) : Parcelable