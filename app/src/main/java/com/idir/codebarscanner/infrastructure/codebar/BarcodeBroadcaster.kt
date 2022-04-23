package com.idir.codebarscanner.infrastructure.codebar

import com.idir.codebarscanner.data.Barcode
import com.idir.codebarscanner.data.BarcodeGroup
import com.idir.codebarscanner.data.JsonMap
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

class BarcodeBroadcaster : IBarcodeBroadcaster {
    private val subscribers : MutableList<IBarcodeSubscriber> = mutableListOf()

    override fun notifyBarcode(barcode: Barcode) {
        subscribers.forEach {
                subscriber -> subscriber.notify(barcode)
        }
    }

    override fun notifyBarcodes(barcodes: List<Barcode>) {
        subscribers.forEach {
                subscriber -> subscriber.notify(barcodes)
        }
    }

    override fun subscribeToBarcodeStream(subscriber : IBarcodeSubscriber) {
        subscribers.add(subscriber)
    }

    override fun unsubscribeFromBarcodeStream(subscriber: IBarcodeSubscriber) {
        subscribers.remove(subscriber)
    }

    override fun getSubscribersAsMap(): JsonMap {
        val temp = mutableListOf<JsonMap>()
        subscribers.forEach {
                group -> temp.add(BarcodeGroup.toMap(group.getValue()))
        }
        return  mapOf("groups" to Json.encodeToJsonElement(temp))
    }


}