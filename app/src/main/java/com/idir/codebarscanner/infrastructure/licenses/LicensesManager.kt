package com.idir.codebarscanner.infrastructure.licenses

import com.idir.codebarscanner.R
import com.idir.codebarscanner.data.License
import com.idir.codebarscanner.infrastructure.ResourcesLoader

class LicensesManager(private val resourcesLoader: ResourcesLoader) {

    companion object{
        val licensesNames : List<Int> = listOf()
        val licensesContents : List<Int> = listOf()
    }

    val _licenses : MutableList<License> = mutableListOf()
    val licenses : List<License> = _licenses


    init {
        /*for (i:Int in 0 ..licensesNames.size){
            registerLicense(
                licensesNames[i],
                licensesContents[i]
            )
        }*/
    }


    private fun registerLicense(nameId:Int ,contentId:Int){
        val name = resourcesLoader.loadStringResource(nameId)
        val content = resourcesLoader.loadStringResource(contentId)
        _licenses.add(License(name,content))
    }

    fun getTitle(): String{
        return resourcesLoader.loadStringResource(R.string.settings_licenses)
    }

}