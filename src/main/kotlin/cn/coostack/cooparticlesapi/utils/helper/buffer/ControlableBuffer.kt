package cn.coostack.cooparticlesapi.utils.helper.buffer

/**
 * 注解此类可以快速获得particle buffer
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class ControlableBuffer(
    val name: String
)
