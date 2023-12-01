package com.example.myfoodproject

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize

class Reviewlist(val picture: Int, val user: Int, val name: String, val rate: Double, val date: String)