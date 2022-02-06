package com.rohit.luasapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

/**
 * Data class which provides a model for the StopInfo, annotated with @Root annotation to indicate
 * the class should be serialized.
 *
 * @constructor Sets all properties of the StopInfo
 * @property created indicates when the stopInfo was created
 * @property stopName the name identifier of the stop
 * @property stopAbbreviation the name abbreviation of the stop
 * @property message the message status of the stop
 * @property lines possible stop directions lines of the stop
 */
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