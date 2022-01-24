package com.overplay.test.common.ui.ext

import android.animation.TimeInterpolator
import androidx.annotation.FloatRange

fun ClosedFloatingPointRange<Float>.atFraction(
    @FloatRange(
        from = 0.0,
        to = 1.0
    ) fraction: Float
): Float {
    return start + (endInclusive - start) * fraction.coerceIn(0.0F, 1.0F)
}

fun ClosedFloatingPointRange<Float>.fractionAt(value: Float): Float {
    return (value - start) / (endInclusive - start)
}

@FloatRange(from = 0.0, to = 1.0)
fun ClosedFloatingPointRange<Float>.clampedFractionAt(value: Float): Float {
    return fractionAt(value).coerceIn(0.0F, 1.0F)
}

fun valueAtFraction(
    start: Float,
    endInclusive: Float,
    @FloatRange(from = 0.0, to = 1.0) fraction: Float
): Float {
    return start + (endInclusive - start) * fraction.coerceIn(0.0F, 1.0F)
}

fun valueAtFraction(
    start: Double,
    endInclusive: Double,
    @FloatRange(from = 0.0, to = 1.0) fraction: Double
): Double {
    return start + (endInclusive - start) * fraction.coerceIn(0.0, 1.0)
}

fun fractionAtValue(start: Float, endInclusive: Float, value: Float): Float {
    return (value - start) / (endInclusive - start)
}

fun fractionAtValue(start: Double, endInclusive: Double, value: Double): Double {
    return (value - start) / (endInclusive - start)
}

fun valueAtFraction(
    start: Int,
    endInclusive: Int,
    @FloatRange(from = 0.0, to = 1.0) fraction: Float
): Float {
    return start + (endInclusive - start) * fraction.coerceIn(0.0F, 1.0F)
}

fun fractionAtValue(start: Int, endInclusive: Int, value: Float): Float {
    return (value - start) / (endInclusive - start)
}

fun Float.subFraction(
    range: ClosedFloatingPointRange<Float>,
    interpolator: TimeInterpolator? = null
): Float {
    return when {
        this in range -> {
            val innerFraction = (this - range.start) / (range.endInclusive - range.start)
            interpolator?.getInterpolation(innerFraction)?.coerceIn(0.0F..1.0F) ?: innerFraction
        }
        this < range.start -> 0.0F
        else -> 1.0F
    }
}