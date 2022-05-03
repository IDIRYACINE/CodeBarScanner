package com.idir.codebarscanner.infrastructure.barcode

class BarcodeBroadcaster : IBarcodeBroadcaster {
    private val subscribers : MutableList<IBarcodeSubscriber> = mutableListOf()

    override fun notifyBarcode(rawBarcode: String) {
        subscribers.forEach {
                subscriber -> subscriber.notify(rawBarcode)
        }
    }

    override fun notifyBarcodes(rawBarcodes: List<String>) {
        subscribers.forEach {
                subscriber -> subscriber.notify(rawBarcodes)
        }
    }

    override fun subscribeToBarcodeStream(subscriber : IBarcodeSubscriber) {
        subscribers.add(subscriber)
    }

    override fun unsubscribeFromBarcodeStream(subscriber: IBarcodeSubscriber) {
        subscribers.remove(subscriber)
    }


}