package com.plcoding.streamchatapp.util

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.Navigator

fun NavController.safeNavigation(
    @IdRes resId : Int,
    args : Bundle? = null,
    navOptions: NavOptions? = null,
    navExtra : Navigator.Extras? = null
){
 val action = currentDestination?.getAction(resId) ?: graph.getAction(resId)
    if(action != null && currentDestination?.id != action.destinationId ){
        navigate(resId,args,navOptions,navExtra)
    }
}