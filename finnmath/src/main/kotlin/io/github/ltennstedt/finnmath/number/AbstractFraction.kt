/*
 * Copyright 2021 Lars Tennstedt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.ltennstedt.finnmath.number

import com.google.common.base.MoreObjects
import java.math.BigDecimal
import java.math.BigInteger
import java.util.Objects

/**
 * Immutable implementation of a fraction which uses [BigInteger] as type for its [numerator] and [denominator]
 *
 * @property numerator numerator
 * @property denominator denominator; default argument is [BigInteger.ONE]
 * @author Lars Tennstedt
 * @since 0.0.1
 */
@Suppress("TooManyFunctions")
public abstract class AbstractFraction<N : Number, T : AbstractFraction<N, T, R>, R : ClosedRange<T>>(
    public val numerator: N,
    public val denominator: N
) : Number(), Comparable<T> {
    /**
     * Indicates if [this][AbstractFraction] is invertible
     *
     * @since 0.0.1
     */
    public abstract val isInvertible: Boolean

    /**
     * Indicates if [this][AbstractFraction] is not invertible
     *
     * @since 0.0.1
     */
    public val isNotInvertible: Boolean get() = !isInvertible

    /**
     * Indicates if [this][AbstractFraction] is a unit
     *
     * @since 0.0.1
     */
    public abstract val isUnit: Boolean

    /**
     * Indicates if [this][AbstractFraction] is not a unit
     *
     * @since 0.0.1
     */
    public val isNotUnit: Boolean get() = !isUnit

    /**
     * Indicates if [this][AbstractFraction] is dyadic
     *
     * @since 0.0.1
     */
    public abstract val isDyadic: Boolean

    /**
     * Indicates if [this][AbstractFraction] is not dyadic
     *
     * @since 0.0.1
     */
    public val isNotDyadic: Boolean get() = !isDyadic

    /**
     * Indicates if [this][AbstractFraction] is irreducible
     *
     * @since 0.0.1
     */
    public abstract val isIrreducible: Boolean

    /**
     * Indicates if [this][AbstractFraction] is reducible
     *
     * @since 0.0.1
     */
    public val isReducible: Boolean = !isIrreducible

    /**
     * Indicates if [this][AbstractFraction] is proper
     *
     * @since 0.0.1
     */
    public abstract val isProper: Boolean

    /**
     * Indicates if [this][AbstractFraction] is improper
     *
     * @since 0.0.1
     */
    public val isImproper: Boolean = !isProper

    /**
     * Signum
     *
     * @since 0.0.1
     */
    public abstract val signum: Int

    /**
     * Returns the sum of [this][AbstractFraction] and the [summand]
     *
     * @since 0.0.1
     */
    public abstract fun add(summand: T): T

    /**
     * Returns the difference of [this][AbstractFraction] and the [subtrahend]
     *
     * @since 0.0.1
     */
    public abstract fun subtract(subtrahend: T): T

    /**
     * Returns the product of [this][AbstractFraction] and the [factor]
     *
     * @since 0.0.1
     */
    public abstract fun multiply(factor: T): T

    /**
     * Return the quotient of [this][AbstractFraction] and the [divisor]
     *
     * @throws IllegalArgumentException if `!divisor.isInvertible`
     * @since 0.0.1
     * @see .isInvertible
     */
    public abstract fun divide(divisor: T): T

    /**
     * Returns the power of [this][AbstractFraction] raised by the [exponent]
     *
     * @since 0.0.1
     */
    public abstract fun pow(exponent: Int): T

    /**
     * Returns the negated [AbstractFraction]
     *
     * @since 0.0.1
     */
    public abstract fun negate(): T

    /**
     * Returns the inverted [AbstractFraction]
     *
     * @throws IllegalStateException if `!isInvertible`
     *
     * @since 0.0.1
     */
    public abstract fun invert(): T

    /**
     * Returns the absolute value
     *
     * @since 0.0.1
     */
    public abstract fun abs(): T

    /**
     * Returns if [this][AbstractFraction] is less than or equal to [other]
     *
     * @since 0.0.1
     */
    public abstract fun lessThanOrEqualTo(other: T): Boolean

    /**
     * Returns if [this][AbstractFraction] is greater than or equal to [other]
     *
     * @since 0.0.1
     */
    public fun greaterThanOrEqualTo(other: T): Boolean = !lessThanOrEqualTo(other) || equivalent(other)

    /**
     * Returns if [this][AbstractFraction] is strictly less than [other]
     *
     * @since 0.0.1
     */
    public fun lessThan(other: T): Boolean = !greaterThanOrEqualTo(other)

    /**
     * Returns if [this][AbstractFraction] is strictly greater than [other]
     *
     * @since 0.0.1
     */
    public fun greaterThan(other: T): Boolean = !lessThanOrEqualTo(other)

    /**
     * Returns the minimum of [this][AbstractFraction] and [other]
     *
     * @since 0.0.1
     */
    @Suppress("UNCHECKED_CAST")
    public fun min(other: T): T = if (greaterThan(other)) other else this as T

    /**
     * Returns the maximum of [this][AbstractFraction] and [other]
     *
     * @since 0.0.1
     */
    @Suppress("UNCHECKED_CAST")
    public fun max(other: T): T = if (lessThan(other)) other else this as T

    /**
     * Returns the [AbstractFraction] incremented by 1
     *
     * @since 0.0.1
     */
    public abstract fun inc(): T

    /**
     * Returns the [AbstractFraction] decremented by 1
     *
     * @since 0.0.1
     */
    public abstract fun dec(): T

    /**
     * Returns the normalized [AbstractFraction]
     *
     * @since 0.0.1
     */
    public abstract fun normalize(): T

    /**
     * Returns the reduced [AbstractFraction]
     *
     * @since 0.0.1
     */
    public abstract fun reduce(): T

    /**
     * Returns the [AbstractFraction] expanded by [number]
     *
     * @since 0.0.1
     */
    public abstract fun expand(number: N): T

    /**
     * Returns if [this][AbstractFraction] is equivalent to [other]
     *
     * @since 0.0.1
     */
    public fun equivalent(other: T): Boolean = normalize().reduce() == other.normalize().reduce()

    override fun toByte(): Byte = toBigDecimal().toByte()

    override fun toShort(): Short = toBigDecimal().toShort()

    override fun toInt(): Int = toBigDecimal().toInt()

    override fun toLong(): Long = toBigDecimal().toLong()

    override fun toFloat(): Float = toBigDecimal().toFloat()

    override fun toDouble(): Double = toBigDecimal().toDouble()

    override fun toChar(): Char = toBigDecimal().toChar()

    /**
     * Returns [this][AbstractFraction] as [BigInteger]
     *
     * @since 0.0.1
     */
    public fun toBigInteger(): BigInteger = toBigDecimal().toBigInteger()

    /**
     * Returns [this][AbstractFraction] as exact [BigInteger]
     *
     * @throws ArithmeticException if [this][AbstractFraction] is not an exact [BigInteger]
     * @since 0.0.1
     */
    public fun toBigIntegerExact(): BigInteger = toBigDecimal().toBigIntegerExact()

    /**
     * Returns [this][AbstractFraction] as [BigDecimal]
     *
     * @since 0.0.1
     */
    public abstract fun toBigDecimal(): BigDecimal

    /**
     * Returns [numerator]
     *
     * @since 0.0.1
     */
    public fun operator1(): N = numerator

    /**
     * Returns [denominator]
     *
     * @since 0.0.1
     */
    public fun operator2(): N = denominator

    override fun hashCode(): Int = Objects.hash(numerator, denominator)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AbstractFraction<*, *, *>) return false
        return numerator == other.numerator && denominator == other.denominator
    }

    override fun toString(): String = MoreObjects.toStringHelper(this)
        .add("numerator", numerator)
        .add("denominator", denominator)
        .toString()

    public companion object
}