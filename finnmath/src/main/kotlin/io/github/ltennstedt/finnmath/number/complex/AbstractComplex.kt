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

package io.github.ltennstedt.finnmath.number.complex

import com.google.common.annotations.Beta
import com.google.common.base.MoreObjects
import org.apiguardian.api.API
import java.io.Serializable
import java.util.Objects

/**
 * Base class for complex numbers
 *
 * @param N type of [real] and [imaginary]
 * @param T type of this
 * @param Q type of the quotient
 * @param A type of the absolute value
 * @param P type of the polar form
 * @property real real part
 * @property imaginary imaginary part
 * @constructor Constructs a AbstractComplex
 * @author Lars Tennstedt
 * @since 0.0.1
 */
@API(status = API.Status.EXPERIMENTAL, since = "0.0.1")
@Beta
public abstract class AbstractComplex<N : Number, T : AbstractComplex<N, T, Q, A, P>, Q, A, P>(
    public val real: N,
    public val imaginary: N
) : Number(), Serializable {
    /**
     * Indicates if this is a unit
     *
     * @since 0.0.1
     */
    public abstract val isUnit: Boolean

    /**
     * Returns the sum of this and the [summand]
     *
     * @since 0.0.1
     */
    public abstract fun add(summand: T): T

    /**
     * Returns the difference of this and the [subtrahend]
     *
     * @since 0.0.1
     */

    public abstract fun subtract(subtrahend: T): T

    /**
     * Returns the product of this and the [factor]
     *
     * @since 0.0.1
     */
    public abstract fun multiply(factor: T): T

    /**
     * Returns the quotient of this and the [divisor]
     *
     * @throws IllegalArgumentException if [divisor] is not a unit
     * @since 0.0.1
     */
    public abstract fun divide(divisor: T): Q

    /**
     * Returns the power of this raised by the [exponent]
     *
     * @since 0.0.1
     */
    public abstract fun pow(exponent: Int): Q

    /**
     * Returns the negated [T]
     *
     * @since 0.0.1
     */
    public abstract fun negate(): T

    /**
     * Returns the inverted [T]
     *
     *@throws IllegalStateException if this is not a unit
     * @since 0.0.1
     */
    public abstract fun invert(): Q

    /**
     * Returns the square of the absolute value
     *
     * @since 0.0.1
     */
    public abstract fun absPow2(): N

    /**
     * Returns the absolute value
     *
     * @since 0.0.1
     */
    public abstract fun abs(): A

    /**
     * Returns the conjugated [T]
     *
     * @since 0.0.1
     */
    public abstract fun conjugate(): T

    /**
     * Returns the argument
     *
     * @throws IllegalStateException if this is equal to 0 by comparing
     * @since 0.0.1
     */
    public abstract fun argument(): A

    override fun toByte(): Byte = real.toByte()

    override fun toShort(): Short = real.toShort()

    override fun toInt(): Int = real.toInt()

    override fun toLong(): Long = real.toLong()

    override fun toFloat(): Float = real.toFloat()

    override fun toDouble(): Double = real.toDouble()

    override fun toChar(): Char = real.toChar()

    /**
     * Returns this as polar form
     *
     * @throws IllegalStateException if this is equal to 0 by comparing
     * @since 0.0.1
     */
    public abstract fun toPolarForm(): P

    /**
     * Returns if this is equal to [other] by comparing
     *
     * @since 0.0.1
     */
    public abstract fun equalsByComparing(other: T): Boolean

    /**
     * Returns if this is not equal to [other] by comparing
     *
     * @since 0.0.1
     */
    public fun doesNotEqualByComparing(other: T): Boolean = !equalsByComparing(other)

    public operator fun plus(summand: T): T = add(summand)

    public operator fun minus(subtrahend: T): T = subtract(subtrahend)

    public operator fun times(factor: T): T = multiply(factor)

    public operator fun div(divisor: T): Q = divide(divisor)

    @Suppress("UNCHECKED_CAST")
    public operator fun unaryPlus(): T = this as T

    public operator fun unaryMinus(): T = negate()

    public fun operator1(): N = real

    public fun operator2(): N = imaginary

    override fun hashCode(): Int = Objects.hash(real, imaginary)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AbstractComplex<*, *, *, *, *>) return false
        return real == other.real && imaginary == other.imaginary
    }

    override fun toString(): String =
        MoreObjects.toStringHelper(this).add("real", real).add("imaginary", imaginary).toString()

    public companion object {
        private const val serialVersionUID = 1L
    }
}
