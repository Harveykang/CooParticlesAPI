package cn.coostack.cooparticlesapi.utils.helper

abstract class StatusHelper : ParticleHelper {
    /**
     * 1 为开启
     * 2 为关闭
     */
    var status = 1

    /**
     * 当状态设置为2时 保留的时间
     */
    var closedInternal = 20



    fun setStatus(){

    }

}