package com.xinaiz.wds.core.driver.modules


import com.xinaiz.wds.core.OCRMode
import com.xinaiz.wds.core.element.ExtendedWebElement
import net.sourceforge.tess4j.Tesseract
import java.awt.image.BufferedImage

interface OCRDriverModulefsdfsdf: DriverModule {

    val ocr: Tesseract

    fun ExtendedWebElement.doOCR(ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): String
    fun ExtendedWebElement.doBinaryOCR(treshold: Int = 128, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): String
    fun ExtendedWebElement.doBinaryOCR(tresholdMin: Int = 86, tresholdMax: Int = 170, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): String
}