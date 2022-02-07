package com.rohit.luasapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "stopInfo")
@Parcelize
data class StopInfo(
    @field: Attribute(name = "created")
    var created : String = "",

    @field: Attribute(name = "stop")
    var stopName : String = "",

    @field: Attribute(name = "stopAbv")
    var stopAbbreviation : String = "",

    @field: Element(name = "message")
    var message : String = "",

    @field : ElementList(name="line", inline = true)
    var lines : List<Direction> = ArrayList()
) : Parcelable