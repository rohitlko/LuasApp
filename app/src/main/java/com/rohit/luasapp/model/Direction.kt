package com.rohit.luasapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root


/**
 * Data class which provides a model for the Direction, annotated with @Root annotation to indicate
 * the class should be serialized.
 *
 * @constructor Sets all properties of the Direction
 * @property name indicates the direction (Inbound/Outbound)
 * @property trams list of trams available for the direction
 */
@Root(name = "direction")
@Parcelize
data class Direction(
    @field: Attribute(name = "name")
    var name: String = "",

    @field : ElementList(name = "tram", inline = true)
    var trams: List<Tram> = ArrayList()
) : Parcelable