package com.idir.codebarscanner.infrastructure.codebar

import com.idir.codebarscanner.data.Barcode
import com.idir.codebarscanner.data.BarcodeGroup
import com.idir.codebarscanner.data.JsonMap

interface IBarcodeBroadcaster {
    fun notifyBarcode(barcode : Barcode)
    fun notifyBarcodes(barcodes : List<Barcode>)
    fun subscribeToBarcodeStream(subscriber : IBarcodeSubscriber)
    fun unsubscribeFromBarcodeStream(subscriber : IBarcodeSubscriber)
    fun getSubscribersAsMap() : JsonMap
}

interface IBarcodeSubscriber{
    fun getId() : String
    fun notify(barcode : Barcode)
    fun notify(barcode : List<Barcode>)
    fun getValue() : BarcodeGroup
}