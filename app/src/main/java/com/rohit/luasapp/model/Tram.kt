package com.rohit.luasapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

/**
 * Data class which provides a model for the Tram, annotated with @Root annotation to indicate
 * the class should be serialized.
 *
 * @constructor Sets all properties of the Tram
 * @property dueMins indicates the estimated time of arrival of the Tram
 * @property destination indicates the final destination of the Tram
 */
@Root(name = "tram")
@Parcelize
data class Tram(
    /**
     * The Attribute annotation represents a serializable XML attribute within a XML element.
     */
    @field : Attribute(name = "dueMins")
    var dueMins: String = "",

    @field : Attribute(name = "destination")
    var destination: String = ""
) : Parcelable