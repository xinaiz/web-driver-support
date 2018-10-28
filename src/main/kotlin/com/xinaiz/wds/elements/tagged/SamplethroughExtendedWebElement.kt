package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.ExtendedWebElement
import org.openqa.selenium.WebElement

class SamplethroughExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    companion object {
        const val TAG = "samp"
    }
}