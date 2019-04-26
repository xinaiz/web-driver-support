package com.xinaiz.wds.core.v2.core.wait

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.FluentWait
import org.openqa.selenium.support.ui.Sleeper
import java.time.Duration


open class SearchContextWait(
    driver: WebDriver,
    searchParent: Any,
    clock: java.time.Clock,
    sleeper: Sleeper,
    timeOutInSeconds: Long,
    sleepTimeOut: Long) : FluentWait<Pair<WebDriver, Any>>(driver to searchParent, clock, sleeper) {

    /**
     * Wait will ignore instances of NotFoundException that are encountered (thrown) by default in
     * the 'until' condition, and immediately propagate all others.  You can add more to the ignore
     * list by calling ignoring(exceptions to add).
     *
     * @param driver The WebDriver instance to pass to the expected conditions
     * @param timeOutInSeconds The timeout in seconds when an expectation is called
     * @see WebDriverWait.ignoring
     */
    constructor(driver: WebDriver, searchParent: Any, timeOutInSeconds: Long) : this(
        driver,
        searchParent,
        java.time.Clock.systemDefaultZone(),
        Sleeper.SYSTEM_SLEEPER,
        timeOutInSeconds,
        FluentWait.DEFAULT_SLEEP_TIMEOUT) {
    }

    /**
     * Wait will ignore instances of NotFoundException that are encountered (thrown) by default in
     * the 'until' condition, and immediately propagate all others.  You can add more to the ignore
     * list by calling ignoring(exceptions to add).
     *
     * @param driver The WebDriver instance to pass to the expected conditions
     * @param timeOutInSeconds The timeout in seconds when an expectation is called
     * @param sleepInMillis The duration in milliseconds to sleep between polls.
     * @see WebDriverWait.ignoring
     */
    constructor(driver: WebDriver, searchParent: Any, timeOutInSeconds: Long, sleepInMillis: Long) : this(
        driver,
        searchParent,
        java.time.Clock.systemDefaultZone(),
        Sleeper.SYSTEM_SLEEPER,
        timeOutInSeconds,
        sleepInMillis) {
    }

    init {
        withTimeout(Duration.ofSeconds(timeOutInSeconds))
        pollingEvery(Duration.ofMillis(sleepTimeOut))
        ignoring(NotFoundException::class.java)
    }

}