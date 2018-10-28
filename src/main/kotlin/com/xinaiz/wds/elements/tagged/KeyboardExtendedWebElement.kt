package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.ExtendedWebElement
import org.openqa.selenium.WebElement

class KeyboardExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {

    companion object {
        const val TYPE = "kbd"
    }

}