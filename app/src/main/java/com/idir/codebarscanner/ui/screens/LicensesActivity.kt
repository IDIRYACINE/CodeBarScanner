package com.idir.codebarscanner.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.idir.codebarscanner.R
import com.idir.codebarscanner.infrastructure.Provider
import com.idir.codebarscanner.infrastructure.licenses.LicensesManager
import com.idir.codebarscanner.ui.components.LicenseFragment
import com.idir.codebarscanner.ui.components.SecondaryAppBar

class LicensesActivity : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LicensesScreen(Provider.licenseManager)
        }
    }
}

@Composable
fun LicensesScreen (licensesManager: LicensesManager){

    Scaffold(
        topBar = { SecondaryAppBar(title = licensesManager.getTitle())}
    ) {
        LazyColumn(){

            licensesManager.licenses.forEach{
                    license -> item{ LicenseFragment(title = license.name , license = license.content) }
            }

        }
    }
}