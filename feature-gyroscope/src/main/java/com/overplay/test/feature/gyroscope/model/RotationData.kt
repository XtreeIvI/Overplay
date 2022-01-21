package com.overplay.test.feature.gyroscope.model

data class RotationData(
    val x: Float = 0.0F,
    val y: Float = 0.0F,
    val z: Float = 0.0F
) {
    val xDegree: Float by lazy {
        convertToReadableDegree(x)
    }

    val yDegree: Float by lazy {
        convertToReadableDegree(y)
    }

    val zDegree: Float by lazy {
        convertToReadableDegree(z)
    }

    companion object {
        private fun convertToReadableDegree(rad: Float): Float {
            return (Math.toDegrees(rad.toDouble()) + 360).rem(360).toFloat()
        }
    }
}