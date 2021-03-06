package com.xinaiz.wds.delegates

import com.xinaiz.wds.core.element.ExtendedWebElement
import kotlin.reflect.KProperty

class JSAttribute(private val name: String? = null){
    operator fun setValue(thisRef: ExtendedWebElement, property: KProperty<*>, value: String)  {
        val name = name?: property.name
        thisRef.setAttribute(name, value)
    }

    operator fun getValue(thisRef: ExtendedWebElement, property: KProperty<*>): String {
        val name = name ?: property.name
        return thisRef.attribute(name)
    }
}