package com.xinaiz.wds.core.v2.core.bycontext

import com.xinaiz.evilkotlin.errorhandling.tryOrNull
import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.core.v2.core.wait.NoThrowSearchContextWait
import com.xinaiz.wds.core.v2.core.wait.SearchContextConditions
import com.xinaiz.wds.core.v2.core.wait.SearchContextWait
import com.xinaiz.wds.util.extensions.extend
import com.xinaiz.wds.util.extensions.extendAll
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

open class ByContextV2 {

    private val searchType: SearchType
    private val searchDriver: WebDriver
    private val searchParent: Any
    private val targetBy: By

    enum class SearchType {
        DRIVER,
        PARENT_ELEMENT_LOCATOR,
        PARENT_ELEMENT
    }

    constructor(driver: WebDriver, by: By) {
        searchType = SearchType.DRIVER
        searchParent = driver
        searchDriver = driver
        targetBy = by
    }

    constructor(driver: WebDriver, parentLocator: By, by: By) {
        searchType = SearchType.PARENT_ELEMENT_LOCATOR
        searchParent = parentLocator
        searchDriver = driver
        targetBy = by
    }

    constructor(driver: WebDriver, parentElement: WebElement, by: By) {
        searchType = SearchType.PARENT_ELEMENT
        searchParent = parentElement
        searchDriver = driver
        targetBy = by
    }

    private fun getSearchContext() = when (searchType) {
        ByContextV2.SearchType.DRIVER -> searchParent as WebDriver
        ByContextV2.SearchType.PARENT_ELEMENT_LOCATOR -> searchDriver.findElement(searchParent as By)
        ByContextV2.SearchType.PARENT_ELEMENT -> searchParent as WebElement
    }

    fun unwrap() = targetBy

    fun find(): ExtendedWebElement = getSearchContext().findElement(targetBy).extend()
    fun findOrNull(): ExtendedWebElement? = tryOrNull { tryOrNull { getSearchContext() }?.findElement(targetBy)?.extend() }
    fun findAll(): List<ExtendedWebElement> = getSearchContext().findElements(targetBy).extendAll()

    open fun wait(seconds: Long = 10, refreshMs: Long = 500) = WaitingThrowingByContextV2(searchDriver, searchParent, targetBy, seconds, refreshMs)

    fun click() = find().click()
    fun type(vararg keysToSend: CharSequence) = find().type(*keysToSend)
    val text get() = find().text
}

class CacheByContextV2(driver: WebDriver, parentElement: WebElement, by: By): ByContextV2(driver, parentElement, by) {

    @Deprecated(level = DeprecationLevel.ERROR, message = "Wait operations on cached screen don't make sense.")
    override fun wait(seconds: Long, refreshMs: Long): WaitingThrowingByContextV2
        = throw RuntimeException("Wait operations on cached screen don't make sense.")

}

class WaitingThrowingByContextV2(private val driver: WebDriver, private val searchParent: Any, private val by: By, private val seconds: Long, private val refreshMs: Long) {

    private val wait = SearchContextWait(driver, searchParent, seconds, refreshMs)

    fun unwrap() = by

    fun orNull() = WaitingNullableByContextV2(driver, searchParent, by, seconds, refreshMs)

    fun untilPresent() = wait.until(SearchContextConditions.presenceOfElementLocated(by))!!.extend()
    fun untilVisible() = wait.until(SearchContextConditions.visibilityOfElementLocated(by))!!.extend()
    fun untilClickable() = wait.until(SearchContextConditions.elementToBeClickable(by))!!.extend()
    fun untilTextPresent(text: String) = wait.until(SearchContextConditions.textToBePresentInElementLocated(by, text))!!.extend()
    fun untilFrameAvailableAndSwithToIt() = wait.until(SearchContextConditions.frameToBeAvailableAndSwitchToIt(by))

    fun find(): ExtendedWebElement = untilPresent()

    fun click() = find().click()

}

class WaitingNullableByContextV2(private val driver: WebDriver, private val searchParent: Any, private val by: By, private val seconds: Long, private val refreshMs: Long) {

    private val wait = NoThrowSearchContextWait(driver, searchParent, seconds, refreshMs)

    fun unwrap() = by

    fun untilPresent() = wait.until(SearchContextConditions.presenceOfElementLocated(by))?.extend()
    fun untilVisible() = wait.until(SearchContextConditions.visibilityOfElementLocated(by))?.extend()
    fun untilClickable() = wait.until(SearchContextConditions.elementToBeClickable(by))?.extend()
    fun untilTextPresent(text: String) = wait.until(SearchContextConditions.textToBePresentInElementLocated(by, text))?.extend()

    fun find(): ExtendedWebElement? = untilPresent()

    fun click() = find()?.click()

}