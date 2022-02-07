package com.rohit.luasapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(name = "tram")
@Parcelize
data class Tram(

    @field : Attribute(name = "dueMins")
    var dueMins: String = "",

    @field : Attribute(name = "destination")
    var destination: String = ""
) : Parcelable