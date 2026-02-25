package org.example
import kotlin.math.*
fun main() {
    GearCalculator().run(
        powerKw = 5.5,          // мощность, кВт
        inputSpeedRpm = 1450.0, // частота вращения, об/мин
        totalRatio = 8.0        // общее передаточное число
    )
}
class GearCalculator {
    companion object { // --- Инженерные коэффициенты ---
        const val TORQUE_COEFFICIENT = 9550.0          // для расчета крутящего момента
        const val CENTER_DISTANCE_COEFF = 450.0        // коэффициент для межосевого расстояния (косозубые)
        const val CONTACT_STRESS_COEFF = 480.0         // коэффициент контактных напряжений
        const val STAGE_EFFICIENCY = 0.97              // КПД одной ступени
        const val FACE_WIDTH_RATIO = 0.3               // ширина венца от межосевого расстояния
        const val SHAFT_ALLOWABLE_STRESS = 25.0        // допустимое напряжение среза, МПа
        const val BEARING_LOAD_FACTOR = 1.2            // коэффициент радиальной нагрузки
    }
    fun run(powerKw: Double, inputSpeedRpm: Double, totalRatio: Double) {
        val inputTorqueNm = TORQUE_COEFFICIENT * powerKw / inputSpeedRpm // --- 1. КИНЕМАТИКА ---
        val firstStageRatio = sqrt(totalRatio)
        val secondStageRatio = totalRatio / firstStageRatio
        val intermediateSpeedRpm = inputSpeedRpm / firstStageRatio
        val intermediateTorqueNm = inputTorqueNm * firstStageRatio * STAGE_EFFICIENCY

        val centerDistanceMm = // --- 2. ГЕОМЕТРИЯ ---
            CENTER_DISTANCE_COEFF *
                    (firstStageRatio + 1) *
                    cbrt(inputTorqueNm * 1.1 / (firstStageRatio.pow(2) * FACE_WIDTH_RATIO))

        val pinionDiameterMm = 2 * centerDistanceMm / (firstStageRatio + 1)
        val faceWidthMm = centerDistanceMm * FACE_WIDTH_RATIO

        val tangentialForceN = 2000 * inputTorqueNm / pinionDiameterMm // --- 3. ПРОЧНОСТЬ ЗУБЬЕВ (контактные напряжения) ---
        val contactStressMpa =
            CONTACT_STRESS_COEFF *
                    sqrt((tangentialForceN * (firstStageRatio + 1)) /
                            (pinionDiameterMm * faceWidthMm * firstStageRatio))

        val shaftDiameterMm = // --- 4. РАСЧЕТ ВАЛА ---
            cbrt(inputTorqueNm / (0.2 * SHAFT_ALLOWABLE_STRESS)) * 10

        val radialLoadN = tangentialForceN * BEARING_LOAD_FACTOR // --- 5. ПОДШИПНИКИ ---
        val requiredDynamicLoadCapacityKn =
            radialLoadN *
                    ((10000 * inputSpeedRpm / 60).pow(1.0 / 3.0)) / 100
        println("--- РЕЗУЛЬТАТЫ РАСЧЕТА ---")
        println("Межосевое расстояние: $centerDistanceMm мм")
        println("Контактные напряжения: $contactStressMpa МПа")
        println("Диаметр ведущего вала: $shaftDiameterMm мм")
        println("Требуемая динамическая грузоподъемность: $requiredDynamicLoadCapacityKn кН")
    }
}